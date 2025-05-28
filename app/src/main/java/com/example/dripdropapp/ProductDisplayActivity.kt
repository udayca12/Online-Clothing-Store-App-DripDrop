package com.example.dripdropapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class ProductDisplayActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var productsContainer: LinearLayout
    private lateinit var tvEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_display)

        findViewById<ImageView>(R.id.ivProfile).setOnClickListener {
            startActivity(Intent(this, UserAccount::class.java))
        }

        val category = intent.getStringExtra("CATEGORY") ?: run {
            finish()
            return
        }
        productsContainer = findViewById(R.id.productsContainer)
        tvEmpty = findViewById(R.id.tvEmpty)

        // Reference: products/category
        database = FirebaseDatabase.getInstance().getReference("products").child(category)
        fetchProducts()
    }

    private fun fetchProducts() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productsContainer.removeAllViews()
                if (!snapshot.exists() || snapshot.children.count() == 0) {
                    tvEmpty.visibility = View.VISIBLE
                    return
                }
                tvEmpty.visibility = View.GONE
                for (productSnap in snapshot.children) {
                    val product = productSnap.getValue(Product::class.java)
                    product?.let { addProductCard(it) }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                tvEmpty.text = "Error loading products"
                tvEmpty.visibility = View.VISIBLE
            }
        })
    }

    private fun addProductCard(product: Product) {
        val cardView = layoutInflater.inflate(R.layout.product_card, productsContainer, false)
        val ivProductImage = cardView.findViewById<ImageView>(R.id.ivProductImage)
        val tvProductName = cardView.findViewById<TextView>(R.id.tvProductName)
        val tvProductPrice = cardView.findViewById<TextView>(R.id.tvProductPrice)
        val ratingBar = cardView.findViewById<RatingBar>(R.id.ratingBar)

        Glide.with(this).load(product.image).into(ivProductImage)
        tvProductName.text = product.name
        tvProductPrice.text = "₹${product.price}"
        ratingBar.rating = product.rating.toFloat()

        productsContainer.addView(cardView)
    }
}
