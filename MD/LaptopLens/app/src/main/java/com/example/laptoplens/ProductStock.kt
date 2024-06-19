package com.example.laptoplens

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductStock : AppCompatActivity() {
    private lateinit var productdetail: EditText
    private lateinit var productNameTextView: TextView
    private lateinit var stockTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.productstock)

        productdetail = findViewById(R.id.productdetail)
        productNameTextView = findViewById(R.id.productNameValueTextView)
        stockTextView = findViewById(R.id.stockValueTextView)

        val btnsearchproduct = findViewById<Button>(R.id.btnsearchproduct)

        btnsearchproduct.setOnClickListener {
            val productName = productdetail.text.toString()

            val apiService = RetrofitClient.getApiService(this)
            val call = apiService.getStock(productName)

            call.enqueue(object : Callback<StockResponse> {
                override fun onResponse(call: Call<StockResponse>, response: Response<StockResponse>) {
                    if (response.isSuccessful) {
                        val stockResponse = response.body()
                        if (stockResponse?.status == "Success" && !stockResponse.data.isNullOrEmpty()) {
                            val productData = stockResponse.data[0] // Ambil data produk pertama
                            updateProductDetails(productData)
                        } else {
                            handleErrorResponse()
                        }
                    } else {
                        handleErrorResponse()
                    }
                }

                override fun onFailure(call: Call<StockResponse>, t: Throwable) {
                    Log.e("ProductStock", "API call failed", t)
                    handleErrorResponse()
                }
            })
        }
    }

    private fun updateProductDetails(productData: ProductData) {
        productNameTextView.text = productData.name
        stockTextView.text = productData.total_stocks.toString()
    }

    private fun handleErrorResponse() {
        productNameTextView.text = "Barang Tidak Ditemukan"
        stockTextView.text = "-"
    }
}
