package database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import models.AccountDetails;

import static constants.SettingManager.DATABASE_NAME;

@Database(entities = {AccountDetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null)
            appDatabase = Room.databaseBuilder(context,AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return appDatabase;
    }

    public abstract AccountDetailsDao accountDetailsDao();

}
