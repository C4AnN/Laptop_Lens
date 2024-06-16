package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ResetPassword : AppCompatActivity()
{
    lateinit var passwordreset : EditText
    lateinit var reenterpasswordreset: EditText
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resetpassword)

        passwordreset = findViewById(R.id.passwordreset)
        reenterpasswordreset = findViewById(R.id.reenterpasswordreset)

        val btnselesaireset = findViewById<Button>(R.id.btnselesaireset)
        btnselesaireset.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}