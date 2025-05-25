package com.example.dripdropapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class IntroductionActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_introduction)
        val dripInButton = findViewById<Button>(R.id.drip_in_intro)
        dripInButton.setOnClickListener {
            // Intent to move from IntroductionActivity to AccountOptionActivity
            val intent = Intent(this, AccountOptionActivity::class.java)
            startActivity(intent)
        }
    }
}