package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import models.AccountDetails

@Dao
interface AccountDetailsDao {
    @get:Query("SELECT * FROM AccountDetails")
    val all: List<AccountDetails?>?

    @Query("SELECT * FROM AccountDetails WHERE Id = :id")
    fun getSelectedModel(id: Long): AccountDetails?

    @Query("Delete FROM AccountDetails WHERE Id = :id")
    fun deleteSelectedModel(id: Long): Int

    @Insert
    fun insertAll(vararg accountDetails: AccountDetails?)

    @Update
    fun update(accountDetails: AccountDetails?): Int
}