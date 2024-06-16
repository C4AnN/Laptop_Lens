package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignUp : AppCompatActivity()
{
    lateinit var firstname: EditText
    lateinit var lastname: EditText
    lateinit var email : EditText
    lateinit var password: EditText
    lateinit var reenterpassword : EditText
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        reenterpassword = findViewById(R.id.reenterpassword)

        val btnsignup_signup = findViewById<Button>(R.id.btnsignup_signup)
        btnsignup_signup.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val btnsignin_signup = findViewById<Button>(R.id.btnsignin_signup)
        btnsignin_signup.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}