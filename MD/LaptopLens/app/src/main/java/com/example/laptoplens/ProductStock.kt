package com.example.laptoplens

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductStock : AppCompatActivity() {
    private lateinit var productdetail: EditText
    private lateinit var stockInfoLayoutContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.productstock)

        productdetail = findViewById(R.id.productdetail)
        stockInfoLayoutContainer = findViewById(R.id.stockInfoLayoutContainer)

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
                            updateProductDetails(stockResponse.data)
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

    private fun updateProductDetails(productDataList: List<ProductData>) {
        stockInfoLayoutContainer.removeAllViews()
        for (productData in productDataList) {
            val productLayout = LinearLayout(this)
            productLayout.orientation = LinearLayout.VERTICAL
            productLayout.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            productLayout.setPadding(24, 24, 24, 24)
            productLayout.setBackgroundResource(R.drawable.oval_button)

            val productNameTextView = TextView(this)
            productNameTextView.text = "Product Name: ${productData.name}"
            productNameTextView.setTextSize(18f)
            productNameTextView.setTextColor(resources.getColor(android.R.color.black))
            productNameTextView.setTypeface(null, android.graphics.Typeface.BOLD)
            productLayout.addView(productNameTextView)

            val stockTextView = TextView(this)
            stockTextView.text = "Stock: ${productData.total_stocks}"
            stockTextView.setTextSize(18f)
            stockTextView.setTextColor(resources.getColor(android.R.color.black))
            stockTextView.setTypeface(null, android.graphics.Typeface.BOLD)
            productLayout.addView(stockTextView)

            stockInfoLayoutContainer.addView(productLayout)
        }
    }

    private fun handleErrorResponse() {
        stockInfoLayoutContainer.removeAllViews()
        val errorTextView = TextView(this)
        errorTextView.text = "Barang Tidak Ditemukan"
        errorTextView.setTextSize(18f)
        errorTextView.setTextColor(resources.getColor(android.R.color.black))
        errorTextView.setTypeface(null, android.graphics.Typeface.BOLD)
        stockInfoLayoutContainer.addView(errorTextView)
    }
}
