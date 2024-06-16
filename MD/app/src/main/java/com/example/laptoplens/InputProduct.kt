package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InputProduct : AppCompatActivity()
{
    lateinit var productnameinput : EditText
    lateinit var brandinput: EditText
    lateinit var priceinput : EditText
    lateinit var dateinput: EditText
    lateinit var quantityinput : EditText
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inputproduct)

        productnameinput = findViewById(R.id.productnameinput)
        brandinput = findViewById(R.id.brandinput)
        priceinput = findViewById(R.id.priceinput)
        dateinput = findViewById(R.id.dateinput)
        quantityinput = findViewById(R.id.quantityinput)

        val btnsaveinput = findViewById<Button>(R.id.btnsaveinput)
        btnsaveinput.setOnClickListener {
            intent = Intent(this, Inventory::class.java)
            startActivity(intent)
        }
    }
}