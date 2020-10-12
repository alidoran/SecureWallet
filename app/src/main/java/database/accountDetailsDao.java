package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import models.AccountDetail;

public class accountDetailsDao {
    @Dao
    public interface AccountDetailsDao {

        @Query("SELECT * FROM AccountDetail")
        List<AccountDetail> getAll();

        @Insert
        List<Long> insertAll(AccountDetail... accountDetails);

        @Update
        void update(AccountDetail... accountDetails);

        @Delete
        void delete(AccountDetail accountDetailModels);
    }
}
