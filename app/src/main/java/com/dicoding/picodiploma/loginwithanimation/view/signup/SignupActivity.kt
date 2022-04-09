package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.model.UserModel
import com.dicoding.picodiploma.loginwithanimation.model.UserPreference
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import kotlinx.coroutines.NonCancellable.start

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playanimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignupViewModel::class.java]
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Masukkan email"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                else -> {
                    signupViewModel.saveUser(UserModel(name, email, password, false))
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Akunnya sudah jadi nih. Yuk, login dan belajar coding.")
                        setPositiveButton("Lanjut") { _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun playanimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        //menjalankan animasi untuk beberapa item dibawah gambar
        val titletextview =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nametxtview =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameedttxtlay =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val nameedittext =
            ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(500)
        val emailtextview =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailedittextlayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordtextview =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordeditextlayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signupbutton =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply{
            playSequentially(titletextview,nametxtview,nameedttxtlay,nameedittext,emailtextview ,emailedittextlayout,passwordtextview,passwordeditextlayout,signupbutton)
            start()
        }
    }

}