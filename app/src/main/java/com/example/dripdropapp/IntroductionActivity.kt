package com.example.dripdropapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        // Find views
        val logo = findViewById<ImageView>(R.id.logoDripDrop)
        val appName = findViewById<TextView>(R.id.tvWelcomeDripDrop)
        val tagline = findViewById<TextView>(R.id.tvRightAddressForShopping)
        val dripInButton = findViewById<Button>(R.id.drip_in_intro)

        // Load animations
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scaleUpAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val slideInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val buttonPressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press)

        // Start animations
        logo.startAnimation(scaleUpAnim)
        appName.startAnimation(fadeInAnim)
        tagline.startAnimation(fadeInAnim)
        dripInButton.startAnimation(slideInAnim)

        // Button click with press animation and activity switch
        dripInButton.setOnClickListener {
            dripInButton.startAnimation(buttonPressAnim)
            val intent = Intent(this, AccountOptionActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
