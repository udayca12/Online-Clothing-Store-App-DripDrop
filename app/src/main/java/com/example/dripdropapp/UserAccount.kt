package com.example.dripdropapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserAccount : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var btnLogout: Button
    private lateinit var llRecentPurchases: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)

        auth = FirebaseAuth.getInstance()

        // Initialize views
        tvUserName = findViewById(R.id.tvUserName)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        btnLogout = findViewById(R.id.btnLogout)
        llRecentPurchases = findViewById(R.id.llRecentPurchases)

        // Get current user
        val user: FirebaseUser? = auth.currentUser

        if (user != null) {
            val name = user.displayName ?: "No Name"
            val email = user.email ?: "No Email"
            tvUserName.text = name
            tvUserEmail.text = email
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            redirectToLogin()
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            redirectToLogin()
        }

        // Show dummy purchases
        showDummyPurchases()
    }

    // These functions must be outside onCreate()
    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showDummyPurchases() {
        val dummy = TextView(this).apply {
            text = "No purchases yet."
            textSize = 16f
            setTextColor(resources.getColor(android.R.color.darker_gray))
        }
        llRecentPurchases.addView(dummy)
    }
}