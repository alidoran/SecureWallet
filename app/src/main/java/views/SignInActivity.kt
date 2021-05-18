package views

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import constants.StaticManager.Companion.GoogleId
import constants.StaticManager.Companion.gMailAddress
import ir.doran_program.SecureWallet.R
import tools.Common
import tools.PrefManager
import views.MainActivity
import views.SignInActivity

class SignInActivity : BaseActivity() {
    private var btnOk: MaterialButton? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: PromptInfo? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var edtPass: TextInputEditText? = null
    private var linBio: LinearLayoutCompat? = null
    private var linGoogle: LinearLayoutCompat? = null
    private var txtBioConnect: MaterialTextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val signUpModel = PrefManager().signDetails
        if (signUpModel == null) intentSignUp()
        setContentView(R.layout.activity_sign_in)
        initView()
        initBiometricSensor()
        initEvent()
        googleSignIn()
    }

    private fun intentSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivityForResult(intent, REQ_SIGN_UP)
    }

    private fun initView() {
        btnOk = findViewById(R.id.sign_in_ok_btn)
        edtPass = findViewById(R.id.sign_in_pass_edt)
        linBio = findViewById(R.id.sign_in_bio_lin)
        linGoogle = findViewById(R.id.sign_in_google_lin)
        txtBioConnect = findViewById(R.id.sign_in_bio_connect_txt)
    }

    private fun initEvent() {
        btnOk!!.setOnClickListener { v: View? ->
            biometricPrompt!!.authenticate(
                promptInfo!!
            )
        }
        edtPass!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (PrefManager.getInstance().signDetails.password == s.toString()) acceptPass()
            }
        })
    }

    //region Google_SignIn
    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInButton = findViewById<SignInButton>(R.id.sign_in_google_btn)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener { v: View? -> signIn() }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun updateUI(signInAccount: GoogleSignInAccount?) {
        val signUpModel = PrefManager().signDetails
        if (signInAccount != null && signUpModel != null) {
            if (signUpModel.email == signInAccount.email) {
                gMailAddress = signInAccount.email
                GoogleId = signInAccount.id
                linGoogle!!.visibility = View.GONE
                if (signUpModel.isBiometric) {
                    txtBioConnect!!.visibility = View.VISIBLE
                    biometricPrompt!!.authenticate(promptInfo!!)
                } else {
                    edtPass!!.visibility = View.VISIBLE
                    txtBioConnect!!.visibility = View.GONE
                }
            } else {
                linGoogle!!.visibility = View.VISIBLE
                mGoogleSignInClient!!.signOut()
                Toast.makeText(this, getString(R.string.select_correct_email), Toast.LENGTH_SHORT)
                    .show()
            }
        }
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

            // Signed in successfully, show authenticated UI.
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
                    Toast.makeText(
                        this@SignInActivity,
                        Common.getInstance().biometricHandleError(errorCode),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    acceptPass()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        this@SignInActivity,
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

    private fun acceptPass() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) when (requestCode) {
            REQ_GOOGLE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
            REQ_SIGN_UP -> {
            }
        } else {
            if (requestCode == REQ_SIGN_UP) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.signup_force_title)
                    .setMessage(R.string.signup_force_message)
                    .setPositiveButton(getString(R.string.accept)) { dialog: DialogInterface?, which: Int -> intentSignUp() }
                    .show()
            }
        }
    }

    companion object {
        private const val REQ_GOOGLE_SIGN_IN = 9001
        private const val REQ_SIGN_UP = 696
    }
}