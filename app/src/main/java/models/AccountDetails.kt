package models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class AccountDetails : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo
    var code: Long = 0

    @ColumnInfo(name = "Name")
    var name: String? = null

    @ColumnInfo(name = "Number")
    var number: String? = null

    @ColumnInfo(name = "Password")
    var password: String? = null

    @ColumnInfo(name = "Cvv2")
    var cvv2: String? = null

    @ColumnInfo(name = "Date")
    var date: String? = null

    @ColumnInfo(name = "BankName")
    var bankName: String? = null

    @ColumnInfo(name = "CardNumber")
    var cardNumber: String? = null

    @ColumnInfo(name = "Summery")
    var summery: String? = null
}