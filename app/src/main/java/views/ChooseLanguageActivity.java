package views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import ir.doran_program.SecureWallet.R;
import tools.LangSet;
import tools.PrefManager;

public class ChooseLanguageActivity extends AppCompatActivity {

    private MaterialButton btnEnglish;
    private MaterialButton btnPersian;
    private MaterialButton btnAccept;
    private boolean isEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        initView();
        initEvent();
        if (!PrefManager.getInstance().isFirstBoot())
            intentToSignInActivity();
    }



    private void initView() {
        btnEnglish = findViewById(R.id.choose_language_english_btn);
        btnPersian = findViewById(R.id.choose_language_persian_btn);
        btnAccept = findViewById(R.id.choose_lang_ok_btn);
    }

    private void initEvent() {
        btnEnglish.setOnClickListener(v->{
            btnEnglish.setBackgroundColor(getColor(R.color.colorPrimary));
            btnPersian.setBackgroundColor(getColor(R.color.material_on_primary_disabled));
            PrefManager.getInstance().setPersianLanguage(false);
        });

        btnPersian.setOnClickListener(v->{
            btnPersian.setBackgroundColor(getColor(R.color.colorPrimary));
            btnEnglish.setBackgroundColor(getColor(R.color.material_on_primary_disabled));
            PrefManager.getInstance().setPersianLanguage(true);
        });
        btnAccept.setOnClickListener(v-> acceptClicked());
    }

    private void acceptClicked() {
        PrefManager.getInstance().setFirstBoot(false);
    }

    private void intentToSignInActivity(){
        new LangSet(this);

    }


}