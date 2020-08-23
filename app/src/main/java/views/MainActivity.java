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

/*        model.EncryptCipherModel encryptCipher = new model.EncryptCipherModel();
        String decoded = "";
        try {
            encryptCipher = tools.Common.getInstance().RSAEncrypt("Ali Doran");
        }catch (Exception e){}

        try {
            decoded = tools.Common.getInstance().RSADecrypt(encryptCipher.encryptedBytes, encryptCipher.privateKey);
        }catch (Exception e){}

        Toast.makeText(this, decoded , Toast.LENGTH_SHORT).show();*/


        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this,
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
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        btn.setOnClickListener(v-> {biometricPrompt.authenticate(promptInfo);});
    }

    private void initView() {
        receiveText = findViewById(R.id.receive_text);
        showText = findViewById(R.id.show_text);
        btn = findViewById(R.id.btn);
    }

}
