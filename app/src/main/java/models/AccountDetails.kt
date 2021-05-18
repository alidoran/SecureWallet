package models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class AccountDetails : Serializable {
    @PrimaryKey(autoGenerate = true)
    var Id: Long = 0

    @ColumnInfo
    var Code: Long = 0

    @ColumnInfo(name = "Name")
    var Name: String? = null

    @ColumnInfo(name = "Number")
    var Number: String? = null

    @ColumnInfo(name = "Password")
    var Password: String? = null

    @ColumnInfo(name = "Cvv2")
    var Cvv2: String? = null

    @ColumnInfo(name = "Date")
    var Date: String? = null

    @ColumnInfo(name = "BankName")
    var BankName: String? = null

    @ColumnInfo(name = "CardNumber")
    var CardNumber: String? = null

    @ColumnInfo(name = "Summery")
    var Summery: String? = null
}