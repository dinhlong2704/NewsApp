package com.example.newsapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.ActivityLogInBinding
import com.example.newsapp.firebase.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import com.example.newsapp.viewmodel.SignInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    lateinit var viewModel: SignInViewModel
    private lateinit var googleAuthUiClient: GoogleAuthUiClient
    var tenThongTinDangNhap: String = "login"
    override fun onPause() {
        super.onPause()
        saveLogInState()
    }

    fun saveLogInState() {
        val sharedPreferences = getSharedPreferences(tenThongTinDangNhap, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", binding.etUserLogin.text.toString())
        editor.putString("password", binding.etPassWordLogin.text.toString())
        editor.putBoolean("save", binding.cbRememberMe.isChecked)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences(tenThongTinDangNhap, MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")
        val save = sharedPreferences.getBoolean("save", false)
        if (save) {
            binding.etUserLogin.setText(email)
            binding.etPassWordLogin.setText(password)
            binding.cbRememberMe.isChecked = save
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.etUserLogin.text.toString()
        val password = binding.etPassWordLogin.text.toString()


        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        googleAuthUiClient = GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )

        // Kiểm tra nếu đã đăng nhập
        if (googleAuthUiClient.getSignedInUser() != null) {
            startActivity(Intent(this, NewsActivity::class.java))
            finish()
        }

        // Quan sát trạng thái đăng nhập
        viewModel.state.observe(this) { state ->
            if (state.isSignInSuccessful) {
                Toast.makeText(this, "Sign in successful", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, NewsActivity::class.java))
                viewModel.resetState()
                finish()
            }
            state.signInError?.let { error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }

        // Handle event navigate sign up
        binding.tvNavigateSignIn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Handle forget password
        binding.tvForgetPass.setOnClickListener {
            val intent = Intent(this, ForgetPassActivity::class.java);
            startActivity(intent);
        }
        binding.btnGoogle.setOnClickListener{
            loginWithGG()
        }
    }

    private fun loginWithGG() {
            CoroutineScope(Dispatchers.Main).launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                signInIntentSender?.let {
                    signInLauncher.launch(
                        IntentSenderRequest.Builder(it).build()
                    )
                }
            }

    }

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            CoroutineScope(Dispatchers.Main).launch {
                val signInResult = googleAuthUiClient.signInWithIntent(
                    intent = result.data ?: return@launch
                )
                viewModel.onSignInResult(signInResult)
            }
        }
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}