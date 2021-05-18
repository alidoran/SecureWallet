package views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import ir.doran_program.SecureWallet.R
import models.SignUpModel
import tools.Common
import tools.PrefManager
import views.SignUpActivity
import java.util.*

class SignUpActivity : BaseActivity() {
    private var edtName: TextInputEditText? = null
    private var edtLName: TextInputEditText? = null
    private var layEmail: TextInputLayout? = null
    private var edtEmail: TextInputEditText? = null
    private var edtUserName: TextInputEditText? = null
    private var edtPassword: TextInputEditText? = null
    private var layRePassword: TextInputLayout? = null
    private var edtRePassword: TextInputEditText? = null
    private var scrollMain: ScrollView? = null
    private var btnAccept: MaterialButton? = null
    private var linSectionOne: LinearLayoutCompat? = null
    private var linSectionTwo: LinearLayoutCompat? = null
    private var googleBtnSign: SignInButton? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var txtSectionTwoTitle: MaterialTextView? = null
    private var chkFingerPrint: MaterialCheckBox? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: PromptInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initView()
        initEvent()
        seTag()
        googleSignIn()
        initBiometricSensor()
        linSectionTwo!!.visibility = View.GONE
    }

    private fun showHelp() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.help)
            .setMessage(R.string.sign_help)
            .show()
    }

    private fun initView() {
        edtName = findViewById(R.id.signup_firs_name_edt)
        edtLName = findViewById(R.id.signup_last_name_edt)
        layEmail = findViewById(R.id.signup_email_lay)
        edtEmail = findViewById(R.id.signup_email_edt)
        edtUserName = findViewById(R.id.sign_up_user_name_edt)
        edtPassword = findViewById(R.id.sign_up_password_edt)
        layRePassword = findViewById(R.id.sign_up_two_password_lay)
        edtRePassword = findViewById(R.id.sign_up_two_password_edt)
        scrollMain = findViewById(R.id.sign_up_main_scroll)
        btnAccept = findViewById(R.id.sign_up_accept_btn)
        linSectionOne = findViewById(R.id.sign_up_section_one_lin)
        linSectionTwo = findViewById(R.id.sign_up_section_two_lin)
        googleBtnSign = findViewById(R.id.sign_up_google_btn)
        txtSectionTwoTitle = findViewById(R.id.sign_up_section_two_title_txt)
        chkFingerPrint = findViewById(R.id.sign_up_finger_print_chk)
    }

    private fun initEvent() {
        btnAccept!!.setOnClickListener { v: View? ->
            if (checkField()) {
                val signUpModel = setViewToModel(SignUpModel::class.java)
                val signStringModel = Gson().toJson(signUpModel)
                PrefManager.getInstance().setSignDetails(signStringModel)
                setResult(RESULT_OK)
                finish()
            }
        }
        chkFingerPrint!!.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) biometricPrompt!!.authenticate(
                promptInfo!!
            )
        }
    }

    private fun seTag() {
        setViewModelText(edtName, "name")
        setViewModelText(edtLName, "lName")
        setViewModelText(edtEmail, "email")
        setViewModelText(edtUserName, "userName")
        setViewModelText(edtPassword, "password")
        setViewModelText(chkFingerPrint, "biometric")
    }

    private fun checkField(): Boolean {
        var chk: Boolean
        val editTextList: MutableList<EditText?> = ArrayList()
        editTextList.add(edtName)
        editTextList.add(edtLName)
        editTextList.add(edtEmail)
        editTextList.add(edtUserName)
        editTextList.add(edtPassword)
        chk = checkField(editTextList, scrollMain)
        val samePassField = edtPassword!!.text.toString() == edtRePassword!!.text.toString()
        if (!samePassField) {
            chk = false
            layRePassword!!.isErrorEnabled = true
            layRePassword!!.error = getString(R.string.same_pass_error_message)
        }
        if (!edtEmail!!.text.toString().endsWith("@gmail.com")) {
            chk = false
            layEmail!!.isErrorEnabled = true
            layEmail!!.error = getString(R.string.need_gmail)
        }
        return chk
    }

    //region Google_SignIn
    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        googleBtnSign!!.setSize(SignInButton.SIZE_STANDARD)
        googleBtnSign!!.setOnClickListener { v: View? -> signIn() }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun updateUI(signInAccount: GoogleSignInAccount?) {
        if (signInAccount != null) {
            PrefManager.getInstance().googleId =
                Common.getInstance().stringToSha256(signInAccount.id)
            linSectionOne!!.visibility = View.GONE
            linSectionTwo!!.visibility = View.VISIBLE
            edtEmail!!.setText(signInAccount.email)
            (edtEmail!!.parent.parent as TextInputLayout).isEnabled = false
            val title =
                getString(R.string.hi) + getString(R.string.space) + signInAccount.displayName + getString(
                    R.string.space
                ) + getString(R.string.fill_field)
            txtSectionTwoTitle!!.text = title
        } else showHelp()
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, REQ_GOOGLE_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            updateUI(account)
        } catch (e: ApiException) {
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    //endregion
    private fun initBiometricSensor() {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this,
            executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    chkFingerPrint!!.isChecked = false
                    Toast.makeText(
                        this@SignUpActivity,
                        Common.getInstance().biometricHandleError(errorCode),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    chkFingerPrint!!.isChecked = false
                    Toast.makeText(
                        this@SignUpActivity,
                        getString(R.string.biometric_authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        promptInfo = PromptInfo.Builder()
            .setTitle(getString(R.string.bio_secure_login))
            .setSubtitle(getString(R.string.log_in_biometric))
            .setNegativeButtonText(getString(R.string.cancel))
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) if (requestCode == REQ_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    companion object {
        private const val REQ_GOOGLE_SIGN_IN = 9001
    }
}