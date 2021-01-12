package views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import base.BaseActivity;
import base.BaseApplication;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.doran_program.SecureWallet.R;
import tools.LangSet;
import tools.PrefManager;
import static tools.MySettings.englishFont;
import static tools.MySettings.fontFamily;
import static tools.MySettings.persianFont;

public class ChooseLanguageActivity extends BaseActivity {

    private MaterialButton btnEnglish;
    private MaterialButton btnPersian;
    private MaterialButton btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        initView();
        initEvent();
        if (!PrefManager.getInstance().isFirstBoot()) {
            setLanguage(PrefManager.getInstance().isPersianLanguage());
            intentToSignInActivity();
        }
    }



    private void initView() {
        btnEnglish = findViewById(R.id.choose_language_english_btn);
        btnPersian = findViewById(R.id.choose_language_persian_btn);
        btnAccept = findViewById(R.id.choose_lang_ok_btn);
    }

    private void initEvent() {
        btnEnglish.setOnClickListener(v-> setLanguage(false));
        btnPersian.setOnClickListener(v-> setLanguage(true));
        btnAccept.setOnClickListener(v-> intentToSignInActivity());
    }


    private void intentToSignInActivity(){
        pumpLang();
        new LangSet(getActivity());
        if (PrefManager.getInstance().isFirstBoot()){
            PrefManager.getInstance().setFirstBoot(false);
            new BaseApplication().attachBaseContext(BaseApplication.getContext());
        }
        new LangSet(BaseApplication.getContext());
        Intent intent = new Intent(this , SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLanguage(boolean isPersian){
        btnPersian.setBackgroundColor(getColor(isPersian ? R.color.colorPrimary : R.color.material_on_primary_disabled));
        btnEnglish.setBackgroundColor(getColor(isPersian ? R.color.material_on_primary_disabled : R.color.colorPrimary));
        btnEnglish.setTextColor(getColor(isPersian ? R.color.md_black_1000 : R.color.md_white_1000));
        btnPersian.setTextColor(getColor(isPersian ? R.color.md_white_1000 : R.color.md_black_1000));
        btnAccept.setText(getString(isPersian ? R.string.accept_fa : R.string.accept_en));
        fontFamily = isPersian ? persianFont : englishFont;
        PrefManager.getInstance().setPersianLanguage(isPersian);
        pumpLang();
    }

    private void pumpLang() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(fontFamily)
                        .setFontAttrId(R.attr.fontPath)
                        .build()))
                .build());
    }
}