package views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.SecureWallet.R;

import java.util.concurrent.Executor;

import base.BaseActivity;

public class MainActivity extends BaseActivity {

    private AppCompatEditText receiveText;
    private AppCompatTextView showText;
    private AppCompatButton btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


    }


    private void initView() {
        receiveText = findViewById(R.id.receive_text);
        showText = findViewById(R.id.show_text);
        btn = findViewById(R.id.btn);
    }

}
