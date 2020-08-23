package views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import com.example.SecureWallet.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import base.BaseActivity;
import tools.PrefManager;

public class LoginActivity extends BaseActivity {

    private TextInputLayout layoutUserName;
    private TextInputEditText edtUserName;
    private TextInputLayout layoutPass;
    private TextInputEditText edtPass;
    private CheckBox chkRemember;
    private AppCompatTextView viewSignUp;
    private AppCompatTextView viewFingerPrint;
    private MaterialButton btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();
        autoFillUserName();
    }

    private void initView() {
        layoutUserName = findViewById(R.id.login_user_name_lay);
        edtUserName = findViewById(R.id.login_user_name_edt);
        layoutPass = findViewById(R.id.login_password_layout);
        edtPass = findViewById(R.id.login_password_edt);
        chkRemember = findViewById(R.id.login_remember_chk);
        viewSignUp = findViewById(R.id.login_sign_up_view);
        viewFingerPrint = findViewById(R.id.login_finger_print_view);
        btnOk = findViewById(R.id.login_ok_btn);
    }

    private void initEvent() {
        btnOk.setOnClickListener(v -> {
            setUserNamePref();
        });
        viewSignUp.setOnClickListener(v->{
            startActivity(new Intent(this, SignupActivity.class));
        });
    }

    private void autoFillUserName() {
        String prefUserName = new PrefManager().getUserName();
        if (!prefUserName.equals("")) {
            chkRemember.setChecked(true);
            edtUserName.setText(prefUserName);
        }
    }

    private void setUserNamePref(){
        String userName = edtUserName.getText().toString();
        new PrefManager().setUserName(chkRemember.isChecked() ? userName : "");
    }
}