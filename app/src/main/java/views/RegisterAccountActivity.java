package views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import database.AppDatabase;
import ir.doran_program.SecureWallet.R;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.seyagh.persiandatepicker.DatePickerMethod;

import base.BaseActivity;
import constants.IntentKeys;
import models.AccountDetails;

public class RegisterAccountActivity extends BaseActivity {

    private long code;
    private TextInputEditText edtAccountName;
    private TextInputEditText edtAccountNumber;
    private TextInputEditText edtAccountPassword;
    private TextInputEditText edtAccountCvv2;
    private TextInputEditText edtAccountDate;
    private TextInputEditText edtAccountBankName;
    private TextInputEditText edtAccountCardNumber;
    private TextInputEditText edtAccountSummery;
    private MaterialButton btnAccept;
    private AppCompatImageView imgBack;
    private AccountDetails selectedAccount;
    private ScrollView scrollMain;

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
        if (getIntent().getExtras() != null) {
            selectedAccount = (AccountDetails) getIntent().getExtras().getSerializable(IntentKeys.SELECTED_ACCOUNT);
            setModelToView(selectedAccount);
        }
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
        scrollMain = findViewById(R.id.register_main_scroll);
    }

    private void initEvent() {
        btnAccept.setOnClickListener(view -> {
            if (checkField()) {
                AccountDetails accountDetails = (AccountDetails) setViewToModel(AccountDetails.class);
                if (selectedAccount == null) {
                    AppDatabase.getInstance(this).accountDetailsDao().insertAll(accountDetails);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    AppDatabase.getInstance(this).accountDetailsDao().update(accountDetails);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        imgBack.setOnClickListener(v ->
                new MaterialAlertDialogBuilder(this)
                        .setTitle(R.string.exit)
                        .setMessage(R.string.close_without_save)
                        .setNegativeButton(getString(R.string.no), null)
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> finish())
                        .show());
        DatePickerMethod.getInstance().setDatePicker(getFragmentManager(), edtAccountDate, null, false);
    }

    private Boolean checkField() {
        boolean chk = checkField(edtAccountName, scrollMain);
        return chk;
    }


}
