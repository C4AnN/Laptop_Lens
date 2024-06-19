package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile : AppCompatActivity() {

    private lateinit var fullNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        fullNameTextView = findViewById(R.id.fullNameTextView)
        emailTextView = findViewById(R.id.emailTextView)

        // Check if user is logged in
        if (!isLoggedIn()) {
            redirectToLogin()
            return
        }

        val apiService = RetrofitClient.getApiService(this)
        val call = apiService.getUserData()

        call.enqueue(object : Callback<UserDataResponse>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<UserDataResponse>,
                response: Response<UserDataResponse>
            ) {
                if(response.isSuccessful) {
                    val userData = response.body()
                    if (userData != null && userData.status == "Success") {
                        fullNameTextView.text = userData.data.firstName + " " +  userData.data.lastName
                        emailTextView.text = userData.data.email
                    }
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                Toast.makeText(this@Profile, "Something happened", Toast.LENGTH_SHORT).show()
            }

        })

        fullNameTextView = findViewById(R.id.fullNameTextView)

        // Retrieve user details from SharedPreferences
        fullNameTextView.text = sharedPreferences.getString("FULL_NAME", "")

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
