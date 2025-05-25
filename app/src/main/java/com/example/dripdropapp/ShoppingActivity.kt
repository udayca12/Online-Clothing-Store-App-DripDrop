package com.example.dripdropapp

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        // Load animations
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in)

        // Navbar Elements
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        val ivCart = findViewById<ImageView>(R.id.ivCart)
        val tvTitle = findViewById<TextView>(R.id.tvShoppingTitle)

        // Category Spinner
        val spinnerCategories = findViewById<Spinner>(R.id.spinnerCategories)

        // Sections (Men & Women)
        val llMensTshirt = findViewById<LinearLayout>(R.id.llMensTshirt)
        val llMensJacket = findViewById<LinearLayout>(R.id.llMensJacket)
        val llMensJeans = findViewById<LinearLayout>(R.id.llMensJeans)
        val llMensShoes = findViewById<LinearLayout>(R.id.llMensShoes)
        val llWomensDress = findViewById<LinearLayout>(R.id.llWomensDress)
        val llWomensTop = findViewById<LinearLayout>(R.id.llWomensTop)
        val llWomensJeans = findViewById<LinearLayout>(R.id.llWomensJeans)
        val llWomensHeels = findViewById<LinearLayout>(R.id.llWomensHeels)

        // Apply animations
        ivProfile.startAnimation(fadeIn)
        ivCart.startAnimation(fadeIn)
        tvTitle.startAnimation(zoomIn)

        spinnerCategories.startAnimation(slideIn)

        llMensTshirt.startAnimation(zoomIn)
        llMensJacket.startAnimation(zoomIn)
        llMensJeans.startAnimation(zoomIn)
        llMensShoes.startAnimation(zoomIn)

        llWomensDress.startAnimation(zoomIn)
        llWomensTop.startAnimation(zoomIn)
        llWomensJeans.startAnimation(zoomIn)
        llWomensHeels.startAnimation(zoomIn)
    }
}