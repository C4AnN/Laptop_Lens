package com.example.laptoplens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InputPrediction : AppCompatActivity() {

    private lateinit var inputCSV: EditText
    private val PICK_CSV_FILE = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inputprediction)

        inputCSV = findViewById(R.id.inputCSV)

        val btnpredict = findViewById<Button>(R.id.btnpredict)
        btnpredict.setOnClickListener {
            val intent = Intent(this, Prediction::class.java)
            startActivity(intent)
        }

        val btnUploadCSV = findViewById<Button>(R.id.btnUploadCSV)
        btnUploadCSV.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "text/csv"
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("text/csv", "application/vnd.ms-excel", "text/comma-separated-values", "application/csv", "text/plain"))
            }
            startActivityForResult(Intent.createChooser(intent, "Select CSV"), PICK_CSV_FILE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_CSV_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                handleCSVFile(uri)
            }
        }
    }

    private fun handleCSVFile(uri: Uri) {
        val fileName = getFileName(uri)
        if (fileName != null) {
            inputCSV.setText(fileName)
            Toast.makeText(this, "CSV file selected: $fileName", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Unable to get the CSV file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileName(uri: Uri): String? {
        var fileName: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    fileName = it.getString(displayNameIndex)
                }
            }
        }
        return fileName
    }
}
