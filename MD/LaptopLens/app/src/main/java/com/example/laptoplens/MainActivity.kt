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

        // Initialize ApiService with context
        apiService = RetrofitClient.getApiService(this)

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
    }

    override fun onBackPressed() {
        if (isLoggedIn()) {
            moveTaskToBack(true) // Minimize app if logged in
        } else {
            if (isLoggedOut()) {
                finishAffinity() // Exit the app if it's the main activity
            } else {
                super.onBackPressed() // Otherwise, navigate back normally
            }
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
                    val responseBody = response.body()
                    if (responseBody?.status == "success") {
                        Log.d("Login", "Response is successful")
                        // Check for tokenId or refreshToken in response headers
                        response.headers()["Authorization"]?.let { token ->
                            if (token.startsWith("Bearer ")) {
                                val actualToken = token.substring(7)
                                saveLoginDetails(emailInput, actualToken)
                                Log.d("Login", "Token saved from response header: $actualToken")
                            }
                        }
                        // Alternatively, check tokenId in the response body
                        responseBody.tokenId?.let { tokenId ->
                            saveLoginDetails(emailInput, tokenId)
                            Log.d("Login", "Token saved from response body: $tokenId")
                        }
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

    private fun saveLoginDetails(email: String, token: String) {
        // Save login details to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("token", token)
        editor.apply()
        Log.d("Login", "Saved token to SharedPreferences: $token")
    }

    private fun clearLoginDetails() {
        // Clear login details from SharedPreferences
        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.remove("token")
        editor.apply()
    }

    private fun isLoggedIn(): Boolean {
        // Check if user is logged in
        val email = sharedPreferences.getString("email", null)
        val token = sharedPreferences.getString("token", null)
        return !email.isNullOrEmpty() && !token.isNullOrEmpty()
    }

    private fun isLoggedOut(): Boolean {
        // Check if user is logged out
        val email = sharedPreferences.getString("email", null)
        val token = sharedPreferences.getString("token", null)
        return email.isNullOrEmpty() && token.isNullOrEmpty()
    }

    private fun navigateToHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}
