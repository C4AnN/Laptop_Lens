package com.example.laptoplens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar

class RecordSale : AppCompatActivity() {
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
                    if (response.isSuccessful) {
                        Toast.makeText(this@RecordSale, "Success to insert data!", Toast.LENGTH_SHORT).show()
                        namerecord.text.clear()
                        pricerecord.text.clear()
                        daterecord.text.clear()
                        quantityrecord.text.clear()
                    } else {
                        Toast.makeText(this@RecordSale, "Failed to insert data!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<OutgoingResp>, t: Throwable) {
                    Toast.makeText(this@RecordSale, "Failed to insert data!", Toast.LENGTH_SHORT).show()
                }
            })
        }

        val btnExport = findViewById<Button>(R.id.btnexport)
        btnExport.setOnClickListener {
            exportDataForPreviousMonth()
        }
    }

    private fun exportDataForPreviousMonth() {
        // Calculate the previous month
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val previousMonth = calendar.get(Calendar.MONTH) + 1  // Months are 0-based

        val apiService = RetrofitClient.getApiService(this)
        val call = apiService.exportData(previousMonth)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        try {
                            val fileName = "exported_datasales_monthly.csv"
                            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            val file = File(downloadsDir, fileName)

                            // Check if the file already exists
                            if (file.exists()) {
                                // Delete the existing file
                                file.delete()
                            }

                            // Create new FileOutputStream
                            val outputStream = FileOutputStream(file)
                            outputStream.use {
                                it.write(body.bytes())
                            }
                            Toast.makeText(this@RecordSale, "Data exported successfully! Saved to $fileName", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Log.e("RecordSale", "Error saving CSV file", e)
                            Toast.makeText(this@RecordSale, "Failed to save CSV file", Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Toast.makeText(this@RecordSale, "Failed to export data: Empty response body", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Log response error body
                    val errorBody = response.errorBody()?.string()
                    Log.e("RecordSale", "Failed to export data: $errorBody")
                    Toast.makeText(this@RecordSale, "Failed to export data: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RecordSale, "Failed to export data!", Toast.LENGTH_SHORT).show()
                Log.e("RecordSale", "Error: ${t.message}")
            }
        })
    }

}
