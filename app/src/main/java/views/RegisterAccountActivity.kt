package views

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import base.BaseActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.seyagh.persiandatepicker.DatePickerMethod
import constants.IntentKeys.SELECTED_ACCOUNT
import database.AppDatabase
import ir.doran_program.SecureWallet.R
import models.AccountDetails

class RegisterAccountActivity : BaseActivity() {
    private val code: Long = 0
    private var edtAccountName: TextInputEditText? = null
    private var edtAccountNumber: TextInputEditText? = null
    private var edtAccountPassword: TextInputEditText? = null
    private var edtAccountCvv2: TextInputEditText? = null
    private var edtAccountDate: TextInputEditText? = null
    private var edtAccountBankName: TextInputEditText? = null
    private var edtAccountCardNumber: TextInputEditText? = null
    private var edtAccountSummery: TextInputEditText? = null
    private var btnAccept: MaterialButton? = null
    private var imgBack: AppCompatImageView? = null
    private val selectedAccount: AccountDetails? = null
    private var scrollMain: ScrollView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regiser_account)
        initView()
        setTag()
        readIntent()
        initEvent()
    }

    private fun readIntent() {
        if (intent.extras != null) {
            val selectedId = intent.extras!!.getInt(SELECTED_ACCOUNT)
            val selectedAccount = AppDatabase.getInstance(this).accountDetailsDao()
                .getSelectedModel(selectedId.toLong())
            setModelToView(selectedAccount)
        }
    }

    private fun setTag() {
        setViewModelText(edtAccountName, "Name")
        setViewModelText(edtAccountNumber, "Number")
        setViewModelText(edtAccountPassword, "Password")
        setViewModelText(edtAccountCvv2, "Cvv2")
        setViewModelText(edtAccountDate, "Date")
        setViewModelText(edtAccountBankName, "BankName")
        setViewModelText(edtAccountCardNumber, "CardNumber")
        setViewModelText(edtAccountSummery, "Summery")
    }

    private fun initView() {
        edtAccountName = findViewById(R.id.register_account_name_edt)
        edtAccountNumber = findViewById(R.id.register_account_number_edt)
        edtAccountPassword = findViewById(R.id.register_account_password_edt)
        edtAccountCvv2 = findViewById(R.id.register_account_cvv2_edt)
        edtAccountDate = findViewById(R.id.register_account_date_edt)
        edtAccountBankName = findViewById(R.id.register_bank_name_edt)
        edtAccountCardNumber = findViewById(R.id.register_card_number_edt)
        edtAccountSummery = findViewById(R.id.register_summery_edt)
        btnAccept = findViewById(R.id.register_account_btn)
        imgBack = findViewById(R.id.register_back_img)
        scrollMain = findViewById(R.id.register_main_scroll)
    }

    private fun initEvent() {
        btnAccept!!.setOnClickListener { view: View? ->
            if (checkField()) {
                val accountDetails = setViewToModel(AccountDetails::class.java) as AccountDetails
                if (selectedAccount == null) {
                    AppDatabase.getInstance(this).accountDetailsDao().insertAll(accountDetails)
                    setResult(RESULT_OK)
                    finish()
                } else {
                    val result =
                        AppDatabase.getInstance(this).accountDetailsDao().update(accountDetails)
                    if (result == 1) setResult(RESULT_OK) else Toast.makeText(
                        this,
                        getString(R.string.operation_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
        imgBack!!.setOnClickListener { v: View? ->
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.exit)
                .setMessage(R.string.close_without_save)
                .setNegativeButton(getString(R.string.no), null)
                .setPositiveButton(getString(R.string.yes)) { dialog: DialogInterface?, which: Int -> finish() }
                .show()
        }
        DatePickerMethod.getInstance().setDatePicker(fragmentManager, edtAccountDate, null, false)
    }

    private fun checkField(): Boolean {
        return checkField(edtAccountName, scrollMain)
    }
}