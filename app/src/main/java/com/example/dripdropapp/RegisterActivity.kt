package com.example.dripdropapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)

        // View bindings based on your XML
        val logo = findViewById<ImageView>(R.id.registerLogo)
        val title = findViewById<TextView>(R.id.registerTitle)
        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val registerBtn = findViewById<Button>(R.id.btnRegister)
        val alreadyAccount = findViewById<TextView>(R.id.tvAlreadyAccount)

        // Load animations
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val buttonPress = AnimationUtils.loadAnimation(this, R.anim.button_press)

        // Apply slide animation to all views
        logo.startAnimation(slideIn)
        title.startAnimation(slideIn)
        name.startAnimation(slideIn)
        email.startAnimation(slideIn)
        password.startAnimation(slideIn)
        registerBtn.startAnimation(slideIn)
        alreadyAccount.startAnimation(slideIn)

        // Button press animation
        registerBtn.setOnClickListener {
            registerBtn.startAnimation(buttonPress)
            Handler(Looper.getMainLooper()).postDelayed({
                // Handle register logic or navigation here
            }, 200)
        }
    }
}
