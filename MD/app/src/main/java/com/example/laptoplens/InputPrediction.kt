package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class InputPrediction : AppCompatActivity()
{
    lateinit var inputCSV : EditText
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inputprediction)

        inputCSV = findViewById(R.id.inputCSV)

        val btnpredict = findViewById<Button>(R.id.btnpredict)
        btnpredict.setOnClickListener {
            intent = Intent(this, Prediction::class.java)
            startActivity(intent)
        }
        val image_home = findViewById<ImageButton>(R.id.image_home)
        image_home.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        val image_inventory = findViewById<ImageButton>(R.id.image_inventory)
        image_inventory.setOnClickListener {
            val intent = Intent(this, Inventory::class.java)
            startActivity(intent)
        }
        val image_profile = findViewById<ImageButton>(R.id.image_profile)
        image_profile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }
}