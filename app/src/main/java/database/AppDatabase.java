package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import constants.SettingManager;
import models.AccountDetails;


@Database(entities = {AccountDetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null)
            appDatabase = Room.databaseBuilder(context,AppDatabase.class, SettingManager.Companion.getDATABASE_NAME())
                    .allowMainThreadQueries()
                    .build();
        return appDatabase;
    }

    public abstract AccountDetailsDao accountDetailsDao();

}
