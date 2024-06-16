package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ProductStock : AppCompatActivity()
{
    lateinit var productdetail : EditText
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.productstock)

        productdetail = findViewById(R.id.productdetail)

        val btnsearchproduct = findViewById<Button>(R.id.btnsearchproduct)
        btnsearchproduct.setOnClickListener {
            intent = Intent(this, ProductStock::class.java)
            startActivity(intent)
        }
    }
}