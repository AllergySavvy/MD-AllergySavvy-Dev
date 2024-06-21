package com.capstone.allergysavvy.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.local.pref.UserPreference
import com.capstone.allergysavvy.data.local.pref.dataStore
import com.capstone.allergysavvy.di.Injection
import com.capstone.allergysavvy.ui.category.form.FormActivity
import com.capstone.allergysavvy.ui.main.MainActivity
import com.capstone.allergysavvy.ui.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var splashViewModel: SplashViewModel
    private var isUserAllergies: Boolean? = null
    private var isUserHaveAllergiesIngredient: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModel()
        checkUserToken()
    }

    private fun setupViewModel() {
        val userPreference = UserPreference.getInstance(application.dataStore)
        splashViewModel = ViewModelProvider(
            this,
            SplashViewModelFactory(userPreference, Injection.getUserDataRepository(application))
        )[SplashViewModel::class.java]
    }

    private fun checkUserToken() {
        splashViewModel.isUserTokenAvailable.observe(this) { isUserTokenAvailable ->
            if (isUserTokenAvailable) {
                checkUserHaveAllergiesIngredient()
                if (isUserHaveAllergiesIngredient == true) {
                    moveToMainActivity()
                } else {
                    checkUserCategory()
                    if (isUserAllergies == true) {
                        moveToFormActivity()
                    } else {
                        moveToMainActivity()
                    }
                }
            } else {
                moveToOnboarding()
            }
        }
    }

    private fun moveToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500)
    }

    private fun moveToFormActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreen, FormActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500)
    }

    private fun checkUserCategory() {
        splashViewModel.isUserAllergy.observe(this) { isUserAllergy ->
            isUserAllergies = isUserAllergy
        }
    }

    private fun checkUserHaveAllergiesIngredient() {
        splashViewModel.isUserHaveAllergyIngredient.observe(this) { isUserHaveAllergyIngredient ->
            isUserHaveAllergiesIngredient = isUserHaveAllergyIngredient
        }
    }

    private fun moveToOnboarding() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreen, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500)
    }
}
