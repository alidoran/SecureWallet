package views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

import base.BaseActivity;
import tools.Common;
import tools.PrefManager;

import static constants.IntentKeys.USER_NAME;

public class SignInActivity extends BaseActivity {

    private TextInputLayout layoutUserName;
    private TextInputEditText edtUserName;
    private TextInputLayout layoutPass;
    private TextInputEditText edtPass;
    private CheckBox chkRemember;
    private AppCompatTextView viewSignUp;
    private AppCompatTextView viewFingerPrint;
    private MaterialButton btnOk;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton btnGoogleSignIn;
    private static final int RC_SIGN_IN = 9001;
    private static final int REQ_SIGN_UP = 696;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        googleSignIn();
        initView();
        initBiometricSensor();
        initEvent();
        biometricPrompt.authenticate(promptInfo);
        autoFillUserName();
    }

    private void initView() {
        layoutUserName = findViewById(R.id.sign_in_user_name_lay);
        edtUserName = findViewById(R.id.sign_in_user_name_edt);
        layoutPass = findViewById(R.id.sign_in_password_layout);
        edtPass = findViewById(R.id.sign_in_password_edt);
        chkRemember = findViewById(R.id.sign_in_remember_chk);
        viewSignUp = findViewById(R.id.sign_in_sign_up_view);
        viewFingerPrint = findViewById(R.id.sign_in_finger_print_view);
        btnOk = findViewById(R.id.sign_in_ok_btn);
        btnGoogleSignIn = findViewById(R.id.sign_in_google_btn);
    }

    private void initEvent() {
        btnOk.setOnClickListener(v -> {
            acceptPass();
        });
        viewSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
        viewFingerPrint.setOnClickListener(v -> {
            biometricPrompt.authenticate(promptInfo);
        });
        viewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            if (!edtUserName.getText().toString().isEmpty())
                intent.putExtra(USER_NAME, edtUserName.getText().toString());
            startActivityForResult(intent, REQ_SIGN_UP);
        });
    }

    private void setUserNamePref() {
        String userName = edtUserName.getText().toString();
        new PrefManager().setUserName(chkRemember.isChecked() ? userName : "");
    }

    private void autoFillUserName() {
        String prefUserName = new PrefManager().getUserName();
        if (!prefUserName.equals("")) {
            chkRemember.setChecked(true);
            edtUserName.setText(prefUserName);
        }
    }

    private void initBiometricSensor() {
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
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
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for SecureWallet")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();
    }

    private void acceptPass() {
        setUserNamePref();
        Intent intent = new Intent(this, ShowAccountsActivity.class);
        startActivity(intent);
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
        if (signInAccount != null) {
            StaticManager.gMailAddress = Common.getInstance().stringToSha256(signInAccount.getEmail());
            StaticManager.GoogleId = Common.getInstance().stringToSha256(signInAccount.getId());
            Intent intent = new Intent(this, ShowAccountsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    //endregion

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case RC_SIGN_IN:
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);
                    break;
            }
    }
}