package constants

import android.text.Spannable
import android.text.SpannableString
import ir.doran_program.SecureWallet.BuildConfig
import tools.CustomSpan
import tools.EnumManager

class SettingManager {
    companion object{
        var DATABASE_NAME = "secure_wallet_database"
        var debugMode = BuildConfig.DEBUG
    }


}