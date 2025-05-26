package com.example.dripdropapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        val logo = findViewById<ImageView>(R.id.registerLogo)
        val title = findViewById<TextView>(R.id.registerTitle)
        val nameField = findViewById<EditText>(R.id.etName)
        val emailField = findViewById<EditText>(R.id.etEmail)
        val passwordField = findViewById<EditText>(R.id.etPassword)
        val registerBtn = findViewById<Button>(R.id.btnRegister)
        val alreadyAccount = findViewById<TextView>(R.id.tvAlreadyAccount)

        // Animations
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val buttonPress = AnimationUtils.loadAnimation(this, R.anim.button_press)

        logo.startAnimation(slideIn)
        title.startAnimation(slideIn)
        nameField.startAnimation(slideIn)
        emailField.startAnimation(slideIn)
        passwordField.startAnimation(slideIn)
        registerBtn.startAnimation(slideIn)
        alreadyAccount.startAnimation(slideIn)

        registerBtn.setOnClickListener {
            registerBtn.startAnimation(buttonPress)
            Handler(Looper.getMainLooper()).postDelayed({
                val name = nameField.text.toString().trim()
                val email = emailField.text.toString().trim()
                val password = passwordField.text.toString().trim()

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    return@postDelayed
                }

                if (password.length < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    return@postDelayed
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Update user's display name
                            val user = auth.currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name) // Set the name here
                                .build()

                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, LoginActivity::class.java))
                                        finish()
                                    } else {
                                        Log.e("RegisterError", "Name update failed", updateTask.exception)
                                        Toast.makeText(this, "Registration incomplete: Couldn't save name", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Log.e("RegisterError", "Registration failed", task.exception)
                            Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

            }, 200)
        }

        alreadyAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
