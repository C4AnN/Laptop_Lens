package com.example.laptoplens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar

class RecordSale : AppCompatActivity()
{
    private lateinit var namerecord: EditText
    private lateinit var daterecord: EditText
    private lateinit var quantityrecord: EditText
    private lateinit var pricerecord: EditText

    @SuppressLint("MissingInflatedId", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recordsale)

        namerecord = findViewById(R.id.productnamerecord)
        daterecord = findViewById(R.id.daterecord)
        quantityrecord = findViewById(R.id.quantityrecord)
        pricerecord = findViewById(R.id.pricerecord)

        daterecord.setOnClickListener {
            // Get Current Date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                    // Set the selected date in the EditText
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    daterecord.setText(selectedDate)
                }, year, month, day)
            datePickerDialog.show()
        }

        val btnsaveecord = findViewById<Button>(R.id.btnsaverecord)
        btnsaveecord.setOnClickListener {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy")
            val outputFormat = SimpleDateFormat("yyyy-MM-dd")

            val date = inputFormat.parse(daterecord.text.toString())

            val data = OutgoingStockReq(
                name = namerecord.text.toString(),
                date = outputFormat.format(date),
                price = java.lang.Double.parseDouble(pricerecord.text.toString()),
                quantity = Integer.parseInt(quantityrecord.text.toString())
            )

            val apiService = RetrofitClient.getApiService(this)
            val call = apiService.postOutgoingStock(data)

            call.enqueue(object : Callback<OutgoingResp> {
                override fun onResponse(
                    call: Call<OutgoingResp>,
                    response: Response<OutgoingResp>
                ) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@RecordSale, "Success to insert data!", Toast.LENGTH_SHORT).show()
                        namerecord.text.clear()
                        pricerecord.text.clear()
                        daterecord.text.clear()
                        quantityrecord.text.clear()
                    }
                }

                override fun onFailure(call: Call<OutgoingResp>, t: Throwable) {
                    Toast.makeText(this@RecordSale, "Failed to insert data!", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}