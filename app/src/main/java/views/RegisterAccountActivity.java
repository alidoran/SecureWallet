package views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.SecureWallet.R;
import com.google.android.material.button.MaterialButton;

import base.BaseActivity;
import constants.IntentKeys;
import models.AccountDetail;
import database.AppDatabase;

public class RegisterAccountActivity extends BaseActivity {

    private long code;
    private AppCompatEditText edtAccountName;
    private AppCompatEditText edtAccountNumber;
    private AppCompatEditText edtAccountPassword;
    private AppCompatEditText edtAccountCvv2;
    private AppCompatEditText edtAccountDate;
    private AppCompatEditText edtAccountBankName;
    private AppCompatEditText edtAccountCardNumber;
    private AppCompatEditText edtAccountSummery;
    private MaterialButton btnAccept;
    private AccountDetail editAccountModel;
    private AppCompatImageView imgBack;
    private AccountDetail selectedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiser_account);

        initView();
        setTag();
        readIntent();
        initEvent();
    }

    private void readIntent() {
        if (getIntent().getExtras() !=null){
            selectedAccount = (AccountDetail) getIntent().getExtras().getSerializable(IntentKeys.SelectedAccount);
            setModelToView(selectedAccount);
        }
    }

    private void initEvent() {
        btnAccept.setOnClickListener(view -> {
            AccountDetail accountDetail = (AccountDetail) setViewToModel(AccountDetail.class);
            AppDatabase.getInstance(this).accountDetailsDao().insertAll(accountDetail);
             });
    }

    private void setTag() {
        setViewModelText(edtAccountName, "Name");
        setViewModelText(edtAccountNumber, "Number");
        setViewModelText(edtAccountPassword, "Password");
        setViewModelText(edtAccountCvv2, "Cvv2");
        setViewModelText(edtAccountDate, "Date");
        setViewModelText(edtAccountBankName, "BankName");
        setViewModelText(edtAccountCardNumber, "CardNumber");
        setViewModelText(edtAccountSummery, "Summery");
    }

    private void initView() {
        edtAccountName = findViewById(R.id.register_account_name_edt);
        edtAccountNumber = findViewById(R.id.register_account_number_edt);
        edtAccountPassword = findViewById(R.id.register_account_password_edt);
        edtAccountCvv2 = findViewById(R.id.register_account_cvv2_edt);
        edtAccountDate = findViewById(R.id.register_account_date_edt);
        edtAccountBankName = findViewById(R.id.register_bank_name_edt);
        edtAccountCardNumber = findViewById(R.id.register_card_number_edt);
        edtAccountSummery = findViewById(R.id.register_summery_edt);
        btnAccept = findViewById(R.id.register_account_btn);
        imgBack = findViewById(R.id.register_back_img);
    }





}
