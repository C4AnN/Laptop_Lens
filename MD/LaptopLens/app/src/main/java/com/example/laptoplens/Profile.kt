package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
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
    private lateinit var positionTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var addressTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        fullNameTextView = findViewById(R.id.fullNameTextView)
        positionTextView = findViewById(R.id.positionTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        addressTextView = findViewById(R.id.addressTextView)

        val intent = intent
        fullNameTextView.text = intent.getStringExtra("FULL_NAME")
        positionTextView.text = intent.getStringExtra("POSITION")
        phoneTextView.text = intent.getStringExtra("PHONE")
        addressTextView.text = intent.getStringExtra("ADDRESS")

        val btneditprofil = findViewById<Button>(R.id.btneditprofil)
        btneditprofil.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

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

    private fun logout() {
        // Retrieve the stored email and password from SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)

        if (email != null && password != null) {
            val logoutRequest = LogoutRequest(email, password)
            val call = RetrofitClient.apiService.logout(logoutRequest)

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body()?.status == "success") {
                        // Clear the stored user data
                        sharedPreferences.edit().clear().apply()

                        Toast.makeText(this@Profile, "Logout berhasil!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@Profile, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@Profile, "Logout gagal: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@Profile, "Logout gagal: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Tidak dapat menemukan kredensial pengguna.", Toast.LENGTH_SHORT).show()
        }
    }
}
