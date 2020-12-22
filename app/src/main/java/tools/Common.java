package tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
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

import base.BaseApplication;
import ir.doran_program.SecureWallet.R;

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
import static tools.EnumManager.BiometricError.*;

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

    public <T> List listModelMapper(List list, Class<T> class1) {
        ArrayList destList = new ArrayList();
        try {
            for (int i = 0; i < list.size(); i++)
                destList.add(modelMapper(list.get(i), class1));
        } catch (Exception e) {
        }
        return destList;
    }

    private <T> T modelMapper(Object source, Class<T> destClass) {
        Field[] srcFields = source.getClass().getDeclaredFields();
        Field[] destFields = destClass.getDeclaredFields();
        T newInstance = null;
        try {
            newInstance = destClass.newInstance();
        } catch (Exception e) {
        }
        for (Field srcField : srcFields) {
            for (Field destField : destFields) {
                try {
                    if (srcField.getName().equals(destField.getName())) {
                        destField.setAccessible(true);
                        srcField.setAccessible(true);
                        destField.set(newInstance, srcField.get(source));
                    }
                } catch (Exception e) {
                    Log.d(TAG, "ModelMapper: " + e);
                }
            }
        }
        return newInstance;
    }

    public static String toNumberLatin(String text) {
        char[][] nums = new char[][]{"۰۱۲۳۴۵۶۷۸۹".toCharArray(), "0123456789".toCharArray()};
        for (int x = 0; x <= 9; x++) {
            text = text.replace(nums[0][x], nums[1][x]);
        }
        return text.replace("٬", ",");
    }

    public static SpannableString getCustomTitle(String title) {
        return getCustomTitle(title, 0);
    }

    public static SpannableString getCustomTitle(String title, int size) {
        return getCustomTitle(title, size, 0);
    }

    public static SpannableString getCustomTitle(String title, int size, int color) {
        CustomSpan span = new CustomSpan(size, color);
        SpannableString spannableString = new SpannableString(title);
        spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static int dpToPx(int dp) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public String BiometricHandleError(int code) {
        Resources resource = BaseApplication.getContext().getResources();
        String result;
        if (code == Disabled.getValue())
            result = resource.getString(R.string.biometric_disable);
        else if (code == Negative.getValue())
            result = resource.getString(R.string.biometric_negative);
        else if (code == NoBiometric.getValue())
            result = resource.getString(R.string.biometric_no_biometric);
        else if (code == NoDeviceCredential.getValue())
            result = resource.getString(R.string.biometric_no_device_credential);
        else if (code == NoSpace.getValue())
            result = resource.getString(R.string.biometric_no_space);
        else if (code == SecurityUpdateRequired.getValue())
            result = resource.getString(R.string.biometric_security_update_required);
        else if (code == TimeOut.getValue())
            result = resource.getString(R.string.biometric_time_out);
        else if (code == UnableToProcess.getValue())
            result = resource.getString(R.string.biometric_unable_process);
        else if (code == UserCanceled.getValue())
            result = resource.getString(R.string.biometric_user_canceled);
        else if (code == ErrorVendor.getValue())
            result = resource.getString(R.string.biometric_error_vendor);
        else
            result = resource.getString(R.string.biometric_not_found);
        return result;
    }
}
