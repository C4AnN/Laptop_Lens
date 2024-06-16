package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ForgotPassword : AppCompatActivity()
{
    lateinit var emailforget : EditText
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgotpassword)

        emailforget = findViewById(R.id.emailforget)

        val btnnextforget = findViewById<Button>(R.id.btnnextforget)
        btnnextforget.setOnClickListener {
            intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
        }
    }
}