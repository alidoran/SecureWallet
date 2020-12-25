package views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import constants.StaticManager;
import ir.doran_program.SecureWallet.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.concurrent.Executor;

import base.BaseActivity;
import models.SignUpModel;
import tools.Common;
import tools.PrefManager;

import static android.view.View.*;

public class SignInActivity extends BaseActivity {

    private AppCompatTextView viewSignUp;
    private MaterialButton btnOk;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private GoogleSignInClient mGoogleSignInClient;
    private TextInputEditText edtPass;
    private LinearLayoutCompat linBio;
    private LinearLayoutCompat linGoogle;
    private MaterialTextView txtBioConnect;

    private static final int REQ_GOOGLE_SIGN_IN = 9001;
    private static final int REQ_SIGN_UP = 696;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initView();
        initBiometricSensor();
        initEvent();
        loadDefault();
    }

    private void loadDefault() {
        SignUpModel signUpModel = new PrefManager().getSignDetails();
        if (signUpModel != null) {
            googleSignIn();
        } else {
            linGoogle.setVisibility(GONE);
            linBio.setVisibility(GONE);
        }
    }

    private void initView() {
        viewSignUp = findViewById(R.id.sign_in_sign_up_view);
        btnOk = findViewById(R.id.sign_in_ok_btn);
        edtPass = findViewById(R.id.sign_in_pass_edt);
        linBio = findViewById(R.id.sign_in_bio_lin);
        linGoogle = findViewById(R.id.sign_in_google_lin);
        txtBioConnect = findViewById(R.id.sign_in_bio_connect_txt);
    }

    private void initEvent() {
        btnOk.setOnClickListener(v -> {
            biometricPrompt.authenticate(promptInfo);
        });
        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PrefManager.getInstance().getSignDetails().getPassword().equals(s.toString()))
                    acceptPass();
            }
        });
        viewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivityForResult(intent, REQ_SIGN_UP);
        });
    }

    //region Google_SignIn
    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_google_btn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(v -> {
            signIn();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount signInAccount) {
        SignUpModel signUpModel = new PrefManager().getSignDetails();
        if (signInAccount != null && signUpModel != null) {
            if (signUpModel.getEmail().equals(signInAccount.getEmail())) {
                StaticManager.gMailAddress = signInAccount.getEmail();
                StaticManager.GoogleId = signInAccount.getId();
                viewSignUp.setVisibility(GONE);
                linGoogle.setVisibility(GONE);
                if (signUpModel.isBiometric()) {
                    txtBioConnect.setVisibility(VISIBLE);
                    biometricPrompt.authenticate(promptInfo);
                } else {
                    edtPass.setVisibility(VISIBLE);
                    txtBioConnect.setVisibility(GONE);
                }
            } else {
                linGoogle.setVisibility(VISIBLE);
                mGoogleSignInClient.signOut();
                Toast.makeText(this, getString(R.string.select_correct_email), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQ_GOOGLE_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {

            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    //endregion

    private void initBiometricSensor() {
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(SignInActivity.this, Common.getInstance().BiometricHandleError(errorCode), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                acceptPass();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(SignInActivity.this, getString(R.string.biometric_authentication_failed), Toast.LENGTH_SHORT).show();
            }


        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.bio_secure_login))
                .setSubtitle(getString(R.string.log_in_biometric))
                .setNegativeButtonText(getString(R.string.cancel))
                .build();
    }

    private void acceptPass() {
        Intent intent = new Intent(this, ShowAccountsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case REQ_GOOGLE_SIGN_IN:
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);
                    break;
                case REQ_SIGN_UP:
                    ((TextInputLayout) edtPass.getParent().getParent()).setVisibility(GONE);
                    break;
            }
    }
}