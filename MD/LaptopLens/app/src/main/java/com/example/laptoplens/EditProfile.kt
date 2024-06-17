package com.example.laptoplens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditProfile : AppCompatActivity() {
    lateinit var fullnameEdit: EditText
    lateinit var positionEdit: EditText
    lateinit var phoneEdit: EditText
    lateinit var addressEdit: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)

        fullnameEdit = findViewById(R.id.fullnameEdit)
        positionEdit = findViewById(R.id.positionEdit)
        phoneEdit = findViewById(R.id.phoneEdit)
        addressEdit = findViewById(R.id.addressEdit)

        val btnselesaiedit = findViewById<Button>(R.id.btnselesaiedit)
        btnselesaiedit.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            intent.putExtra("FULL_NAME", fullnameEdit.text.toString())
            intent.putExtra("POSITION", positionEdit.text.toString())
            intent.putExtra("PHONE", phoneEdit.text.toString())
            intent.putExtra("ADDRESS", addressEdit.text.toString())
            startActivity(intent)
        }
    }
}
