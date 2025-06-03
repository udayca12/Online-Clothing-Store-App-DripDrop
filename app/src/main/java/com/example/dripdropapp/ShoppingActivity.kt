package com.example.dripdropapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        // Animation code (if any)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        findViewById<ImageView>(R.id.ivProfile).startAnimation(fadeIn)

        // Profile click listener
        findViewById<ImageView>(R.id.ivProfile).setOnClickListener {
            startActivity(Intent(this, UserAccount::class.java))
        }
        findViewById<ImageView>(R.id.ivCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // Category click listeners
        setupCategoryClick(R.id.llMensTshirt, "tshirts")
        setupCategoryClick(R.id.llMensJacket, "Jackets")
        setupCategoryClick(R.id.llMensJeans, "Jeans")
        setupCategoryClick(R.id.llMensShoes, "Shoes")
        setupCategoryClick(R.id.llWomensDress, "WomensDress")
        setupCategoryClick(R.id.llWomensTop, "WomensTop")
        setupCategoryClick(R.id.llWomensJeans, "WomensJeans")
        setupCategoryClick(R.id.llWomensHeels, "WomensHeels")
    }

    private fun setupCategoryClick(layoutId: Int, firebaseCategoryKey: String) {
        findViewById<LinearLayout>(layoutId).setOnClickListener {
            openProductDisplay(firebaseCategoryKey)
        }
    }

    private fun openProductDisplay(category: String) {
        val intent = Intent(this, ProductDisplayActivity::class.java)
        intent.putExtra("CATEGORY", category)
        startActivity(intent)
    }



}
