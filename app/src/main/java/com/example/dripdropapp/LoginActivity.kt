package com.example.dripdropapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

        // Firebase Auth instance
        auth = FirebaseAuth.getInstance()

        val logo = findViewById<ImageView>(R.id.logoDripDrop)
        val heading = findViewById<TextView>(R.id.LetsLogin)
        val subheading = findViewById<TextView>(R.id.DontHaveAccount)
        val email = findViewById<EditText>(R.id.edEmailLogin)
        val password = findViewById<EditText>(R.id.edPassword)
        val forgot = findViewById<TextView>(R.id.ForgotPassword)
        val loginBtn = findViewById<Button>(R.id.LoginLogin)

        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val buttonPress = AnimationUtils.loadAnimation(this, R.anim.button_press)

        // Start animations
        logo.startAnimation(slideIn)
        heading.startAnimation(slideIn)
        subheading.startAnimation(slideIn)
        email.startAnimation(slideIn)
        password.startAnimation(slideIn)
        forgot.startAnimation(slideIn)
        loginBtn.startAnimation(slideIn)

        loginBtn.setOnClickListener {
            loginBtn.startAnimation(buttonPress)

            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            // Basic validation
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Delay to show button press animation
            Handler(Looper.getMainLooper()).postDelayed({
                // Firebase login
                auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                            // Navigate to Shopping Page
                            val intent = Intent(this, ShoppingActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed: Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
            }, 200)
        }
    }
}
