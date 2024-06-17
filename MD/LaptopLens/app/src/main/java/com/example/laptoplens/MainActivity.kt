package com.example.laptoplens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var emailsignin: EditText
    private lateinit var passwordsignin: EditText
    private lateinit var apiService: ApiService
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        emailsignin = findViewById(R.id.emailsignin)
        passwordsignin = findViewById(R.id.passwordsignin)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Check if user is already logged in
        if (isLoggedIn()) {
            navigateToHome()
            return  // Exit onCreate to prevent further initialization
        }

        passwordsignin.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handleSignIn()
                // Close the keyboard
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(passwordsignin.windowToken, 0)
                true
            } else {
                false
            }
        }

        val btnSignIn = findViewById<Button>(R.id.btnsignin_signin)
        btnSignIn.setOnClickListener {
            handleSignIn()
        }

        val btnSignUp = findViewById<Button>(R.id.btnsignup_signin)
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        apiService = RetrofitClient.apiService
    }

    override fun onBackPressed() {
        if (isLoggedIn()) {
            // If logged in, move task to back (minimize app)
            moveTaskToBack(true)
        } else {
            // If not logged in, allow back press (normal behavior)
            super.onBackPressed()
        }
    }

    private fun handleSignIn() {
        val emailInput = emailsignin.text.toString().trim()
        val passwordInput = passwordsignin.text.toString().trim()

        if (emailInput.isEmpty()) {
            emailsignin.error = "Email is required"
            emailsignin.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailsignin.error = "Enter a valid email"
            emailsignin.requestFocus()
            return
        }

        if (passwordInput.isEmpty()) {
            passwordsignin.error = "Password is required"
            passwordsignin.requestFocus()
            return
        }

        val loginRequest = LoginRequest(emailInput, passwordInput)
        Log.d("Login", "Sending login request")
        apiService.login(loginRequest).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                Log.d("Login", "Received response: ${response.code()}")
                if (response.isSuccessful) {
                    Log.d("Login", "Response is successful")
                    val responseBody = response.body()
                    Log.d("Login", "Response body: $responseBody")
                    if (responseBody?.status == "success") {
                        Log.d("Login", "Login success: ${responseBody.message}")
                        saveLoginDetails(emailInput, passwordInput)
                        Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                        navigateToHome()
                    } else {
                        Log.d("Login", "Login failed: ${responseBody?.message}")
                        Toast.makeText(this@MainActivity, responseBody?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("Login", "Response is not successful: ${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("Login", "Login error: ${t.message}")
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveLoginDetails(email: String, password: String) {
        // Save login details to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    private fun clearLoginDetails() {
        // Clear login details from SharedPreferences
        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.remove("password")
        editor.apply()
    }

    private fun isLoggedIn(): Boolean {
        // Check if user is logged in
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    private fun navigateToHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}
