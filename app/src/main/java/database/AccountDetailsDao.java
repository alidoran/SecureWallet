package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import models.AccountDetails;

    @Dao
    public interface AccountDetailsDao {

        @Query("SELECT * FROM AccountDetails")
        List<AccountDetails> getAll();

        @Insert
        void insertAll(AccountDetails... accountDetails);

        @Update
        int update(AccountDetails accountDetails);

        @Delete
        boolean delete(AccountDetails... accountDetails);
    }

