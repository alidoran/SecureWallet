package views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import ir.doran_program.SecureWallet.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import base.BaseActivity;
import models.SignUpModel;
import tools.Common;
import tools.PrefManager;

import static android.view.View.*;

public class SignUpActivity extends BaseActivity {

    private TextInputEditText edtName;
    private TextInputEditText edtLName;
    private TextInputLayout layEmail;
    private TextInputEditText edtEmail;
    private TextInputEditText edtUserName;
    private TextInputEditText edtPassword;
    private TextInputLayout layRePassword;
    private TextInputEditText edtRePassword;
    private ScrollView scrollMain;
    private MaterialButton btnAccept;
    private LinearLayoutCompat linSectionOne;
    private LinearLayoutCompat linSectionTwo;
    private SignInButton googleBtnSign;
    private GoogleSignInClient mGoogleSignInClient;
    private MaterialTextView txtSectionTwoTitle;
    private MaterialCheckBox chkFingerPrint;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private static final int REQ_GOOGLE_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
        initEvent();
        seTag();
        googleSignIn();
        initBiometricSensor();
        linSectionTwo.setVisibility(GONE);
    }

    private void showHelp() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.help)
                .setMessage(R.string.sign_help)
                .show();
    }

    private void initView() {
        edtName = findViewById(R.id.signup_firs_name_edt);
        edtLName = findViewById(R.id.signup_last_name_edt);
        layEmail = findViewById(R.id.signup_email_lay);
        edtEmail = findViewById(R.id.signup_email_edt);
        edtUserName = findViewById(R.id.sign_up_user_name_edt);
        edtPassword = findViewById(R.id.sign_up_password_edt);
        layRePassword = findViewById(R.id.sign_up_two_password_lay);
        edtRePassword = findViewById(R.id.sign_up_two_password_edt);
        scrollMain = findViewById(R.id.sign_up_main_scroll);
        btnAccept = findViewById(R.id.sign_up_accept_btn);
        linSectionOne = findViewById(R.id.sign_up_section_one_lin);
        linSectionTwo = findViewById(R.id.sign_up_section_two_lin);
        googleBtnSign = findViewById(R.id.sign_up_google_btn);
        txtSectionTwoTitle = findViewById(R.id.sign_up_section_two_title_txt);
        chkFingerPrint = findViewById(R.id.sign_up_finger_print_chk);
    }

    private void initEvent() {
        btnAccept.setOnClickListener(v -> {
            if (checkField()) {
                SignUpModel signUpModel = setViewToModel(SignUpModel.class);
                String signStringModel = new Gson().toJson(signUpModel);
                PrefManager.getInstance().setSignDetails(signStringModel);
                setResult(RESULT_OK);
                finish();
            }
        });
        chkFingerPrint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                biometricPrompt.authenticate(promptInfo);
        });
    }

    private void seTag() {
        setViewModelText(edtName, "name");
        setViewModelText(edtLName, "lName");
        setViewModelText(edtEmail, "email");
        setViewModelText(edtUserName, "userName");
        setViewModelText(edtPassword, "password");
        setViewModelText(chkFingerPrint, "biometric");
    }

    private boolean checkField() {
        boolean chk;
        List<EditText> editTextList = new ArrayList<>();
        editTextList.add(edtName);
        editTextList.add(edtLName);
        editTextList.add(edtEmail);
        editTextList.add(edtUserName);
        editTextList.add(edtPassword);
        chk = checkField(editTextList, scrollMain);
        boolean samePassField = edtPassword.getText().toString().equals(edtRePassword.getText().toString());
        if (!samePassField) {
            chk = false;
            layRePassword.setErrorEnabled(true);
            layRePassword.setError(getString(R.string.same_pass_error_message));
        }
        if (!edtEmail.getText().toString().endsWith("@gmail.com")) {
            chk = false;
            layEmail.setErrorEnabled(true);
            layEmail.setError(getString(R.string.need_gmail));
        }
        return chk;
    }

    //region Google_SignIn
    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleBtnSign.setSize(SignInButton.SIZE_STANDARD);
        googleBtnSign.setOnClickListener(v -> signIn());
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount signInAccount) {
        if (signInAccount != null) {
            PrefManager.getInstance().setGoogleId(Common.getInstance().stringToSha256(signInAccount.getId()));
            linSectionOne.setVisibility(GONE);
            linSectionTwo.setVisibility(VISIBLE);
            edtEmail.setText(signInAccount.getEmail());
            ((TextInputLayout) edtEmail.getParent().getParent()).setEnabled(false);
            String title = getString(R.string.hi) + getString(R.string.space) + signInAccount.getDisplayName() + getString(R.string.space) + getString(R.string.fill_field);
            txtSectionTwoTitle.setText(title);
        } else
            showHelp();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQ_GOOGLE_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
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
                chkFingerPrint.setChecked(false);
                Toast.makeText(SignUpActivity.this, Common.getInstance().biometricHandleError(errorCode), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                chkFingerPrint.setChecked(false);
                Toast.makeText(SignUpActivity.this, getString(R.string.biometric_authentication_failed), Toast.LENGTH_SHORT).show();
            }


        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.bio_secure_login))
                .setSubtitle(getString(R.string.log_in_biometric))
                .setNegativeButtonText(getString(R.string.cancel))
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == REQ_GOOGLE_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }


    }
}