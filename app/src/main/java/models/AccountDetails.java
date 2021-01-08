package models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity
public class AccountDetails implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long Id;
    @ColumnInfo()
    private long Code;
    @ColumnInfo(name = "Name")
    public String Name;
    @ColumnInfo(name = "Number")
    private String Number;
    @ColumnInfo(name = "Password")
    private String Password;
    @ColumnInfo(name = "Cvv2")
    private String Cvv2;
    @ColumnInfo(name = "Date")
    private String Date;
    @ColumnInfo(name = "BankName")
    private String BankName;
    @ColumnInfo(name = "CardNumber")
    private String CardNumber;
    @ColumnInfo(name = "Summery")
    private String Summery;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getCode() {
        return Code;
    }

    public void setCode(long code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCvv2() {
        return Cvv2;
    }

    public void setCvv2(String cvv2) {
        Cvv2 = cvv2;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getSummery() {
        return Summery;
    }

    public void setSummery(String summery) {
        Summery = summery;
    }
}
