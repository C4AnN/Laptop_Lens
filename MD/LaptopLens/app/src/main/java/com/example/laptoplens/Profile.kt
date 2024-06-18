package com.example.laptoplens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Profile : AppCompatActivity() {

    private lateinit var fullNameTextView: TextView
    private lateinit var positionTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Check if user is logged in
        if (!isLoggedIn()) {
            redirectToLogin()
            return
        }

        fullNameTextView = findViewById(R.id.fullNameTextView)
        positionTextView = findViewById(R.id.positionTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        addressTextView = findViewById(R.id.addressTextView)

        // Retrieve user details from SharedPreferences
        fullNameTextView.text = sharedPreferences.getString("FULL_NAME", "")
        positionTextView.text = sharedPreferences.getString("POSITION", "")
        phoneTextView.text = sharedPreferences.getString("PHONE", "")
        addressTextView.text = sharedPreferences.getString("ADDRESS", "")

        val btnlogout = findViewById<Button>(R.id.btnlogout)
        btnlogout.setOnClickListener {
            logout()
        }

        val image_home = findViewById<ImageButton>(R.id.image_home)
        image_home.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val image_predict = findViewById<ImageButton>(R.id.image_predict)
        image_predict.setOnClickListener {
            val intent = Intent(this, InputPrediction::class.java)
            startActivity(intent)
        }

        val image_inventory = findViewById<ImageButton>(R.id.image_inventory)
        image_inventory.setOnClickListener {
            val intent = Intent(this, Inventory::class.java)
            startActivity(intent)
        }
    }

    private fun isLoggedIn(): Boolean {
        // Check if user is logged in
        val email = sharedPreferences.getString("email", null)
        val token = sharedPreferences.getString("token", null)
        return !email.isNullOrEmpty() && !token.isNullOrEmpty()
    }

    private fun redirectToLogin() {
        Toast.makeText(this, "You are not logged in!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun logout() {
        // Clear login details from SharedPreferences
        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.remove("token")
        editor.apply()

        Toast.makeText(this, "Logout successful!", Toast.LENGTH_SHORT).show()

        // Redirect to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
