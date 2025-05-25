package com.example.dripdropapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.view.animation.AnimationUtils

class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_introduction)

        // Find views
        val logo = findViewById<ImageView>(R.id.logoDripDrop)
        val appName = findViewById<TextView>(R.id.tvWelcomeDripDrop)
        val tagline = findViewById<TextView>(R.id.tvRightAddressForShopping)
        val dripInButton = findViewById<Button>(R.id.drip_in_intro)

        // Load animations from res/anim/
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scaleUpAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val buttonFadeInAnim = AnimationUtils.loadAnimation(this, R.anim.button_fade_in)
        val buttonPressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press)

        // Start animations
        logo.startAnimation(scaleUpAnim)
        appName.startAnimation(fadeInAnim)
        tagline.startAnimation(fadeInAnim)
        dripInButton.startAnimation(buttonFadeInAnim)

        // Button click with press animation + navigation
        dripInButton.setOnClickListener {
            dripInButton.startAnimation(buttonPressAnim)
            val intent = Intent(this, AccountOptionActivity::class.java)
            startActivity(intent)
        }
    }
}
