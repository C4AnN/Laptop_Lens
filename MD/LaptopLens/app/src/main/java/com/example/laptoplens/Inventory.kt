package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Inventory : AppCompatActivity()
{
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inventory)

        val btninput = findViewById<Button>(R.id.btninput)
        btninput.setOnClickListener {
            intent = Intent(this, InputProduct::class.java)
            startActivity(intent)
        }
        val btnrecord = findViewById<Button>(R.id.btnrecord)
        btnrecord.setOnClickListener {
            intent = Intent(this, RecordSale::class.java)
            startActivity(intent)
        }
        val btnstock = findViewById<Button>(R.id.btnstock)
        btnstock.setOnClickListener {
            intent = Intent(this, ProductStock::class.java)
            startActivity(intent)
        }
        val image_home = findViewById<ImageButton>(R.id.image_home)
        image_home.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        val image_predict = findViewById<ImageButton>(R.id.image_predict)
        image_predict.setOnClickListener {
            val intent = Intent(this, InputPrediction::class.java)
            startActivity(intent)
        }
        val image_profile = findViewById<ImageButton>(R.id.image_profile)
        image_profile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }
}