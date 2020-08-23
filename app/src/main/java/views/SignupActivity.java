package views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.SecureWallet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;

public class SignupActivity extends BaseActivity {

    private TextInputLayout layName;
    private TextInputEditText edtName;
    private TextInputLayout layLName;
    private TextInputEditText edtLName;
    private TextInputLayout layEmail;
    private TextInputEditText edtEmail;
    private TextInputLayout layUserName;
    private TextInputEditText edtUserName;
    private TextInputLayout layPassword;
    private TextInputEditText edtPassword;
    private TextInputLayout layRePassword;
    private TextInputEditText edtRePassword;
    private ScrollView scrollMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
        initEvent();
        seTag();
    }

    private void initView() {
        layName = findViewById(R.id.signup_firs_name_lay);
        edtName = findViewById(R.id.signup_firs_name_edt);
        layLName = findViewById(R.id.signup_last_name_lay);
        edtLName = findViewById(R.id.signup_last_name_edt);
        layEmail = findViewById(R.id.signup_email_lay);
        edtEmail = findViewById(R.id.signup_email_edt);
        layUserName = findViewById(R.id.sign_up_user_name_lay);
        edtUserName = findViewById(R.id.sign_up_user_name_edt);
        layPassword = findViewById(R.id.sign_up_password_lay);
        edtPassword = findViewById(R.id.sign_up_password_edt);
        layRePassword = findViewById(R.id.sign_up_two_password_lay);
        edtRePassword = findViewById(R.id.sign_up_two_password_edt);
        scrollMain = findViewById(R.id.sign_up_main_scroll);
    }

    private void initEvent() {

    }

    private void seTag() {
        setViewModelTag(edtName , "Name");
        setViewModelTag(edtLName , "LName");
        setViewModelTag(edtEmail , "email");
        setViewModelTag(edtUserName , "userName");
        setViewModelTag(edtPassword , "password");
    }

    private boolean checkField(){
        boolean chk;
        List<EditText> editTextList = new ArrayList<>();
        editTextList.add(edtName);
        editTextList.add(edtLName);
        editTextList.add(edtEmail);
        editTextList.add(edtUserName);
        editTextList.add(edtPassword);
        chk = checkField(editTextList , scrollMain);
        boolean samePassField = edtPassword.getText().toString().equals(edtRePassword.getText().toString());
        if (!samePassField) {
            chk = false;
            layRePassword.setErrorEnabled(true);
            layRePassword.setError(getString(R.string.same_pass_error_message));
        }
        return chk;
    }
}