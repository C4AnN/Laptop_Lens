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
import java.text.SimpleDateFormat
import java.util.*

class InputProduct : AppCompatActivity() {
    lateinit var productnameinput: EditText
    lateinit var brandinput: EditText
    lateinit var priceinput: EditText
    lateinit var dateinput: EditText
    lateinit var quantityinput: EditText

    @SuppressLint("MissingInflatedId", "NewApi", "SimpleDateFormat")
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


        val btnsaveinput = findViewById<Button>(R.id.btnsaveinputproduct)
        btnsaveinput.setOnClickListener {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy")
            val outputFormat = SimpleDateFormat("yyyy-MM-dd")

            val date = inputFormat.parse(dateinput.text.toString())

            val data = IncomingStockReq(
                name = productnameinput.text.toString(),
                vendor_name = brandinput.text.toString(),
                date = outputFormat.format(date),
                price = java.lang.Double.parseDouble(priceinput.text.toString()),
                quantity = Integer.parseInt(quantityinput.text.toString())
            )
            val apiService = RetrofitClient.getApiService(this)
            val call = apiService.postIncomingStock(data)

            call.enqueue(object : Callback<IncomingResp>{
                override fun onResponse(
                    call: Call<IncomingResp>,
                    response: Response<IncomingResp>
                ) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@InputProduct, "Success to insert data!", Toast.LENGTH_SHORT).show()
                        productnameinput.text.clear()
                        brandinput.text.clear()
                        priceinput.text.clear()
                        dateinput.text.clear()
                        quantityinput.text.clear()
                    }
                }

                override fun onFailure(call: Call<IncomingResp>, t: Throwable) {
                    Toast.makeText(this@InputProduct, "Failed to insert data!", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}
