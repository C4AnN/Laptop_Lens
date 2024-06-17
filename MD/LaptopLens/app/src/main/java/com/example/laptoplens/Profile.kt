package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Profile : AppCompatActivity() {
    private lateinit var fullNameTextView: TextView
    private lateinit var positionTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var addressTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        fullNameTextView = findViewById(R.id.fullNameTextView)
        positionTextView = findViewById(R.id.positionTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        addressTextView = findViewById(R.id.addressTextView)

        val intent = intent
        fullNameTextView.text = intent.getStringExtra("FULL_NAME")
        positionTextView.text = intent.getStringExtra("POSITION")
        phoneTextView.text = intent.getStringExtra("PHONE")
        addressTextView.text = intent.getStringExtra("ADDRESS")

        val btneditprofil = findViewById<Button>(R.id.btneditprofil)
        btneditprofil.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        val btnlogout = findViewById<Button>(R.id.btnlogout)
        btnlogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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

        val image_inventory = findViewById<ImageButton>(R.id.image_inventory)
        image_inventory.setOnClickListener {
            val intent = Intent(this, Inventory::class.java)
            startActivity(intent)
        }
    }
}
