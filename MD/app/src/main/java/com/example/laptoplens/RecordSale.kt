package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class RecordSale : AppCompatActivity()
{
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recordsale)

        val btnbackrecord = findViewById<Button>(R.id.btnbackrecord)
        btnbackrecord.setOnClickListener {
            intent = Intent(this, Inventory::class.java)
            startActivity(intent)
        }
    }
}