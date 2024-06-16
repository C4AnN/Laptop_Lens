package com.example.laptoplens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class InputProduct : AppCompatActivity() {
    lateinit var productnameinput: EditText
    lateinit var brandinput: EditText
    lateinit var priceinput: EditText
    lateinit var dateinput: EditText
    lateinit var quantityinput: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inputproduct)

        productnameinput = findViewById(R.id.productnameinput)
        brandinput = findViewById(R.id.brandinput)
        priceinput = findViewById(R.id.priceinput)
        dateinput = findViewById(R.id.dateinput)
        quantityinput = findViewById(R.id.quantityinput)

        // Setup DatePickerDialog for dateinput
        dateinput.setOnClickListener {
            // Get Current Date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                    // Set the selected date in the EditText
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    dateinput.setText(selectedDate)
                }, year, month, day)
            datePickerDialog.show()
        }

        val btnsaveinput = findViewById<Button>(R.id.btnsaveinput)
        btnsaveinput.setOnClickListener {
            intent = Intent(this, Inventory::class.java)
            startActivity(intent)
        }
    }
}
