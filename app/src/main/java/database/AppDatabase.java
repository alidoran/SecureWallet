package database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import models.AccountDetail;

@Database(entities = {AccountDetail.class}, version = 1)

    public abstract class AppDatabase extends RoomDatabase {
        private static AppDatabase appDatabase;
        public abstract accountDetailsDao.AccountDetailsDao accountDetailsDao();

        public static AppDatabase getInstance(Context context){
            if (appDatabase == null)
                appDatabase =Room.databaseBuilder(context,AppDatabase.class, "room_database").allowMainThreadQueries().build();
            return appDatabase;
        }

}
