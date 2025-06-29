package com.example.dripdropapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.dripdropapp.Product


class ProductDetailActivity : AppCompatActivity() {

    private lateinit var ivProductImage: ImageView
    private lateinit var tvProductName: TextView
    private lateinit var tvPrice: TextView
    private lateinit var sizeGroup: RadioGroup
    private lateinit var tvQuantity: TextView
    private lateinit var btnMinus: Button
    private lateinit var btnPlus: Button
    private lateinit var btnAddToCart: Button
    private lateinit var btnBuyNow: Button

    private var quantity = 1
    private var selectedSize = "M"
    private lateinit var category: String
    private lateinit var productKey: String
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail) // XML file used for all categories

        // Bind views
        ivProductImage = findViewById(R.id.ivProductImage)
        tvProductName = findViewById(R.id.tvProductName)
        tvPrice = findViewById(R.id.tvPrice)
        sizeGroup = findViewById(R.id.sizeGroup)
        tvQuantity = findViewById(R.id.tvQuantity)
        btnMinus = findViewById(R.id.btnMinus)
        btnPlus = findViewById(R.id.btnPlus)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        btnBuyNow = findViewById(R.id.btnBuyNow)

        // Get intent data
        category = intent.getStringExtra("CATEGORY") ?: ""
        productKey = intent.getStringExtra("PRODUCT_KEY") ?: ""

        fetchProductDetails()

        // Size selector
        sizeGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedSize = when (checkedId) {
                R.id.rbSizeS -> "S"
                R.id.rbSizeM -> "M"
                R.id.rbSizeL -> "L"
                R.id.rbSizeXL -> "XL"
                else -> "M"
            }
        }

        // Quantity buttons
        btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                tvQuantity.text = quantity.toString()
            }
        }

        btnPlus.setOnClickListener {
            quantity++
            tvQuantity.text = quantity.toString()
        }

        // Button actions
        btnAddToCart.setOnClickListener { addToCart() }
        btnBuyNow.setOnClickListener { buyNow() }
    }

    private fun fetchProductDetails() {
        val ref = FirebaseDatabase.getInstance().getReference("products")
            .child(category)
            .child(productKey)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                product = snapshot.getValue(Product::class.java) ?: return
                tvProductName.text = product.name
                tvPrice.text = "₹${product.price}"

                if (product.image.isNotEmpty()) {
                    Glide.with(this@ProductDetailActivity)
                        .load(product.image)
                        .placeholder(R.drawable.blury_background)
                        .error(R.drawable.blury_background)
                        .into(ivProductImage)
                } else {
                    ivProductImage.setImageResource(R.drawable.blury_background)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProductDetailActivity, "Failed to load product.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addToCart() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Toast.makeText(this, "Please log in to add to cart", Toast.LENGTH_SHORT).show()
            return
        }

        val cartRef = FirebaseDatabase.getInstance().getReference("carts")
            .child(user.uid)
            .push()

        val cartItem = CartItem(
            productId = productKey,
            category = category,
            size = selectedSize,
            quantity = quantity
        )

        cartRef.setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to add product to cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buyNow() {
        Toast.makeText(this, "Proceed to buy now (feature coming soon)", Toast.LENGTH_SHORT).show()
        // Navigate to checkout activity if implemented
    }
}
