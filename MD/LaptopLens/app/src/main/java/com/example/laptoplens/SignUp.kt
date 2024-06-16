package com.example.laptoplens

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {

    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var reenterpassword: EditText
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        reenterpassword = findViewById(R.id.reenterpassword)

        reenterpassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validateInput()
                // Close the keyboard
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(reenterpassword.windowToken, 0)
                true
            } else {
                false
            }
        }

        val btnSignup = findViewById<Button>(R.id.btnsignup_signup)
        btnSignup.setOnClickListener {
            if (validateInput()) {
                signUpUser()
            }
        }

        val btnSignin = findViewById<Button>(R.id.btnsignin_signup)
        btnSignin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        apiService = RetrofitClient.apiService
    }

    private fun validateInput(): Boolean {
        val firstnameInput = firstname.text.toString().trim()
        val lastnameInput = lastname.text.toString().trim()
        val emailInput = email.text.toString().trim()
        val passwordInput = password.text.toString().trim()
        val reenterpasswordInput = reenterpassword.text.toString().trim()

        if (firstnameInput.isEmpty()) {
            firstname.error = "First name is required"
            firstname.requestFocus()
            return false
        }

        if (lastnameInput.isEmpty()) {
            lastname.error = "Last name is required"
            lastname.requestFocus()
            return false
        }

        if (emailInput.isEmpty()) {
            email.error = "Email is required"
            email.requestFocus()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.error = "Enter a valid email"
            email.requestFocus()
            return false
        }

        if (passwordInput.isEmpty()) {
            password.error = "Password is required"
            password.requestFocus()
            return false
        }

        if (passwordInput.length < 6) {
            password.error = "Password should be at least 6 characters long"
            password.requestFocus()
            return false
        }

        if (reenterpasswordInput.isEmpty()) {
            reenterpassword.error = "Please re-enter your password"
            reenterpassword.requestFocus()
            return false
        }

        if (passwordInput != reenterpasswordInput) {
            reenterpassword.error = "Passwords do not match"
            reenterpassword.requestFocus()
            return false
        }

        return true
    }

    private fun signUpUser() {
        val user = User(
            firstName = firstname.text.toString(),
            lastName = lastname.text.toString(),
            email = email.text.toString(),
            password = password.text.toString(),
            confirmPassword = reenterpassword.text.toString()
        )

        apiService.signUp(user).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful && response.body()?.message == "User created successfully") {
                    Toast.makeText(this@SignUp, "Sign up successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@SignUp, response.body()?.message ?: "Sign up failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@SignUp, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
