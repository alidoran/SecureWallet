package tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.SecureWallet.R;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;

import base.BaseActivity;
import interfaces.OnClickListenerNoObject;
import models.EncryptCipherModel;
import models.ModelViewModel;

import static android.content.ContentValues.TAG;

public class Common {

    boolean fingerResult;

    private static Common common;


    public static Common getInstance() {
        if (common == null)
            common = new Common();
        return common;
    }

    public String stringToSha256(String inputString) {
        String stringHashCode = "";
        try {
            MessageDigest objSHA = MessageDigest.getInstance("SHA-256");
            byte[] bytSHA = objSHA.digest(inputString.getBytes());
            BigInteger intNumber = new BigInteger(1, bytSHA);
            stringHashCode = intNumber.toString(16);
            while (stringHashCode.length() < 64) {
                stringHashCode = "0" + stringHashCode;
            }
        } catch (Exception e) {
            Log.d(TAG, "stringToSha256:" + e.toString());
        }
        return stringHashCode;
    }

    public EncryptCipherModel RSAEncrypt(final String plain) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plain.getBytes());
        EncryptCipherModel encryptCipher = new EncryptCipherModel();
        encryptCipher.setEncryptCipher(publicKey, privateKey, encryptedBytes);
        return encryptCipher;
    }

    public String RSADecrypt(final byte[] encryptedBytes, PrivateKey privateKey) throws Exception {
        Cipher cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher1.doFinal(encryptedBytes);
        String decrypted = new String(decryptedBytes);
        System.out.println("DDecrypted?????" + decrypted);
        return decrypted;
    }

    public boolean fingerPrintAction(Context context) {
        fingerResult = false;
        Executor executor = ContextCompat.getMainExecutor(context);
        BiometricPrompt biometricPrompt = new BiometricPrompt(((BaseActivity) context),
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                fingerResult = true;

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
        return fingerResult;
    }
}
