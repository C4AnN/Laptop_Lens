package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val image_predict = findViewById<ImageButton>(R.id.image_predict)
        image_predict.setOnClickListener {
            val intent = Intent(this, InputPrediction::class.java)
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