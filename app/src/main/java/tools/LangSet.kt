package tools

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LangSet(var context: Context) {
    private fun changeLangRecognize() {
        val lang = if (PrefManager.getInstance().isPersianLanguage) "fa" else "en"
        ChangeLocate(lang)
    }

    private fun ChangeLocate(localLoad: String) {
        val locale = Locale(localLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    init {
        changeLangRecognize()
    }
}