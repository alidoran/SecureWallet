package views

import android.content.Intent
import android.os.Bundle
import android.view.View
import base.BaseActivity
import base.BaseApplication
import com.google.android.material.button.MaterialButton
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import ir.doran_program.SecureWallet.R
import tools.LangSet
import tools.MySettings.englishFont
import tools.MySettings.fontFamily
import tools.MySettings.persianFont
import tools.PrefManager

class ChooseLanguageActivity : BaseActivity() {
    private var btnEnglish: MaterialButton? = null
    private var btnPersian: MaterialButton? = null
    private var btnAccept: MaterialButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_language)
        initView()
        initEvent()
        if (!PrefManager.getInstance().isFirstBoot) {
            setLanguage(PrefManager.getInstance().isPersianLanguage)
            intentToSignInActivity()
        }
    }

    private fun initView() {
        btnEnglish = findViewById(R.id.choose_language_english_btn)
        btnPersian = findViewById(R.id.choose_language_persian_btn)
        btnAccept = findViewById(R.id.choose_lang_ok_btn)
    }

    private fun initEvent() {
        btnEnglish!!.setOnClickListener { v: View? -> setLanguage(false) }
        btnPersian!!.setOnClickListener { v: View? -> setLanguage(true) }
        btnAccept!!.setOnClickListener { v: View? -> intentToSignInActivity() }
    }

    private fun intentToSignInActivity() {
        pumpLang()
        LangSet(activity)
        if (PrefManager.getInstance().isFirstBoot) {
            PrefManager.getInstance().isFirstBoot = false
            BaseApplication().attachBaseContext(BaseApplication.getContext())
        }
        LangSet(BaseApplication.getContext())
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setLanguage(isPersian: Boolean) {
        btnPersian!!.setBackgroundColor(getColor(if (isPersian) R.color.colorPrimary else R.color.material_on_primary_disabled))
        btnEnglish!!.setBackgroundColor(getColor(if (isPersian) R.color.material_on_primary_disabled else R.color.colorPrimary))
        btnEnglish!!.setTextColor(getColor(if (isPersian) R.color.md_black_1000 else R.color.md_white_1000))
        btnPersian!!.setTextColor(getColor(if (isPersian) R.color.md_white_1000 else R.color.md_black_1000))
        btnAccept!!.text = getString(if (isPersian) R.string.accept_fa else R.string.accept_en)
        fontFamily = if (isPersian) persianFont else englishFont
        PrefManager.getInstance().isPersianLanguage = isPersian
        pumpLang()
    }

    private fun pumpLang() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(fontFamily)
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }
}