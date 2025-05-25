package com.example.dripdropapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

        val logo = findViewById<ImageView>(R.id.logoDripDrop)
        val heading = findViewById<TextView>(R.id.LetsLogin)
        val subheading = findViewById<TextView>(R.id.DontHaveAccount)
        val email = findViewById<EditText>(R.id.edEmailLogin)
        val password = findViewById<EditText>(R.id.edPassword)
        val forgot = findViewById<TextView>(R.id.ForgotPassword)
        val loginBtn = findViewById<Button>(R.id.LoginLogin)

        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val buttonPress = AnimationUtils.loadAnimation(this, R.anim.button_press)

        logo.startAnimation(slideIn)
        heading.startAnimation(slideIn)
        subheading.startAnimation(slideIn)
        email.startAnimation(slideIn)
        password.startAnimation(slideIn)
        forgot.startAnimation(slideIn)
        loginBtn.startAnimation(slideIn)

        loginBtn.setOnClickListener {
            loginBtn.startAnimation(buttonPress)
            Handler(Looper.getMainLooper()).postDelayed({
                // Navigation will be added later
            }, 200)
        }
    }
}
