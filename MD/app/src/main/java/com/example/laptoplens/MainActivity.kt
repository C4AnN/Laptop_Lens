package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity()
{
    lateinit var emailsignin : EditText
    lateinit var passwordsignin: EditText
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        emailsignin = findViewById(R.id.emailsignin)
        passwordsignin = findViewById(R.id.passwordsignin)

        val btnforgotpassword = findViewById<Button>(R.id.btnforgotpassword)
        btnforgotpassword.setOnClickListener {
            intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
        val btnsignin_signin = findViewById<Button>(R.id.btnsignin_signin)
        btnsignin_signin.setOnClickListener {
            intent = Intent(this, Inventory::class.java)
            startActivity(intent)
        }
        val btnsignup_signin = findViewById<Button>(R.id.btnsignup_signin)
        btnsignup_signin.setOnClickListener {
            intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}