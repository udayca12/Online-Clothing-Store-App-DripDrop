package com.example.dripdropapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccountOptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_option)

        val loginButton = findViewById<Button>(R.id.buttonLoginAccount)
        val registerButton = findViewById<Button>(R.id.buttonRegisterAccountOptions)
        val logo = findViewById<ImageView>(R.id.logoDripDrop)
        val appName = findViewById<TextView>(R.id.tvAppName)
        val tagline = findViewById<TextView>(R.id.tvRightAddressForShopping)

        // Load animations
        val slideInBottomAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val slideInTopAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_top)
        val buttonPressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press)

        // Apply slide-in-top animation to logo, name, tagline
        logo.startAnimation(slideInTopAnim)
        appName.startAnimation(slideInTopAnim)
        tagline.startAnimation(slideInTopAnim)

        // Apply slide-in-bottom animation to buttons
        loginButton.startAnimation(slideInBottomAnim)
        registerButton.startAnimation(slideInBottomAnim)

        // OnClick animations
        loginButton.setOnClickListener {
            loginButton.startAnimation(buttonPressAnim)
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }, 200)
        }

        registerButton.setOnClickListener {
            registerButton.startAnimation(buttonPressAnim)
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }, 200)
        }
    }
}
