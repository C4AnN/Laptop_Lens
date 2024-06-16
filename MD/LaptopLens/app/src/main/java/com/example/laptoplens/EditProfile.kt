package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditProfile : AppCompatActivity()
{
    lateinit var firstnameedit : EditText
    lateinit var lastnameedit: EditText
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)

        firstnameedit = findViewById(R.id.firstnameedit)
        lastnameedit = findViewById(R.id.lastnameedit)

        val btnselesaiedit = findViewById<Button>(R.id.btnselesaiedit)
        btnselesaiedit.setOnClickListener {
            intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }
}