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
import constants.StaticManager;
import ir.doran_program.SecureWallet.R;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import base.BaseActivity;
import interfaces.OnClickListenerNoObject;
import models.EncryptCipherModel;
import models.ModelViewModel;

import static android.content.ContentValues.TAG;
import static tools.EnumManager.BiometricError.*;

public class Common {

    private static Common common;
    private final String cipherMode= "AES/ECB/PKCS5Padding";

    public static Common getInstance() {
        if (common == null)
            common = new Common();
        return common;
    }

    public String stringToSha256(String inputString) {
        StringBuilder stringHashCode = new StringBuilder();
        try {
            MessageDigest objSHA = MessageDigest.getInstance("SHA-256");
            byte[] bytSHA = objSHA.digest(inputString.getBytes());
            BigInteger intNumber = new BigInteger(1, bytSHA);
            stringHashCode = new StringBuilder(intNumber.toString(16));
            while (stringHashCode.length() < 64) {
                stringHashCode.insert(0, "0");
            }
        } catch (Exception e) {
            Log.d(TAG, "stringToSha256:" + e.toString());
        }
        return stringHashCode.toString();
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
        } catch (Exception ignore) {
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

    public String toNumberLatin(String text) {
        char[][] nums = new char[][]{"۰۱۲۳۴۵۶۷۸۹".toCharArray(), "0123456789".toCharArray()};
        for (int x = 0; x <= 9; x++) {
            text = text.replace(nums[0][x], nums[1][x]);
        }
        return text.replace("٬", ",");
    }

    public SpannableString getCustomTitle(String title) {
        return getCustomTitle(title, 0);
    }

    public SpannableString getCustomTitle(String title, int size) {
        return getCustomTitle(title, size, 0);
    }

    public SpannableString getCustomTitle(String title, int size, int color) {
        CustomSpan span = new CustomSpan(size, color);
        SpannableString spannableString = new SpannableString(title);
        spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public int dpToPx(int dp) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public String biometricHandleError(int code) {
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

    public String encryptString(String s) {
        SecretKeySpec sks = new SecretKeySpec(StaticManager.gMailAddress.getBytes(), "AES");
        try {
            Cipher cipher = null;
            cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            byte[] cipherText = cipher.doFinal(s.getBytes(StandardCharsets.UTF_8));
            return byteArrayToString(cipherText);
        } catch (Exception ignore) {
            return null;
        }
    }

    public String decryptString(String s) {
        SecretKeySpec sks = new SecretKeySpec(StaticManager.gMailAddress.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.DECRYPT_MODE, sks);
            return new String(cipher.doFinal(s.getBytes()), StandardCharsets.UTF_8);
        } catch (Exception ignore) {
            return null;
        }
    }


    public String byteArrayToString(byte[] bytes) {
        String text = null;
        text = new String(bytes, StandardCharsets.UTF_8);
        return text;
    }
}
