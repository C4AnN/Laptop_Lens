package com.example.laptoplens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    // Set the selected date in the EditText
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    dateinput.setText(selectedDate)
                }, year, month, day)
            datePickerDialog.show()
        }

        val btnsaveinput = findViewById<Button>(R.id.btnsaveinput)
        btnsaveinput.setOnClickListener {
            saveProduct()
        }
    }

    private fun saveProduct() {
        val name = productnameinput.text.toString().trim()
        val vendorName = brandinput.text.toString().trim()
        val date = dateinput.text.toString().trim()
        val price = priceinput.text.toString().trim().toIntOrNull() ?: 0
        val quantity = quantityinput.text.toString().trim().toIntOrNull() ?: 0

        if (name.isEmpty() || vendorName.isEmpty() || date.isEmpty() || price <= 0 || quantity <= 0) {
            Toast.makeText(this, "Please fill in all fields with valid data", Toast.LENGTH_SHORT).show()
            return
        }

        val product = ProductIncome(name, vendorName, date, price, quantity)
        val apiService = RetrofitClient.getApiService(this)
        apiService.addProduct(product).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@InputProduct, "Product added successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@InputProduct, Inventory::class.java))
                } else {
                    Toast.makeText(this@InputProduct, "Failed to add product", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@InputProduct, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
