package constants;

import ir.doran_program.SecureWallet.BuildConfig;
import tools.EnumManager;

public class SettingManager {
    public static String APP_LANGUAGE = EnumManager.AppLanguage.Persian.name();
    public static String DATABASE_NAME = "secure_wallet_database";
    public static boolean debugMode = BuildConfig.DEBUG;
}
