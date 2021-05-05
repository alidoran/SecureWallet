package constants

import ir.doran_program.SecureWallet.BuildConfig
import tools.EnumManager

class SettingManager {
    companion object{
        var APP_LANGUAGE = EnumManager.AppLanguage.Persian.name
        var DATABASE_NAME = "secure_wallet_database"
        var debugMode = BuildConfig.DEBUG
    }
}