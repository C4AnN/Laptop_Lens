package com.example.laptoplens

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Prediction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prediction)

        val lowEndPredictionView = findViewById<LinearLayout>(R.id.low_end_prediction_view)
        val midEndPredictionView = findViewById<LinearLayout>(R.id.mid_end_prediction_view)
        val highEndPredictionView = findViewById<LinearLayout>(R.id.high_end_prediction_view)

        // Fetch predictions from API
        RetrofitClient.apiService.getPredictions().enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { predictionResponse ->
                        displayPredictions(predictionResponse.result.low, lowEndPredictionView)
                        displayPredictions(predictionResponse.result.mid, midEndPredictionView)
                        displayPredictions(predictionResponse.result.high, highEndPredictionView)
                    }
                } else {
                    displayError(lowEndPredictionView, midEndPredictionView, highEndPredictionView)
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                displayError(lowEndPredictionView, midEndPredictionView, highEndPredictionView)
            }
        })
    }

    private fun displayPredictions(predictions: List<SalesData>, predictionView: LinearLayout) {
        predictions.forEach { prediction ->
            predictionView.addView(createPredictionTextView("${prediction.date}: ${prediction.sales}"))
        }
    }

    private fun createPredictionTextView(prediction: String): TextView {
        return TextView(this).apply {
            text = prediction
            textSize = 18f
            setTextColor(resources.getColor(R.color.black, theme))
        }
    }

    private fun displayError(lowEndPredictionView: LinearLayout, midEndPredictionView: LinearLayout, highEndPredictionView: LinearLayout) {
        val errorMessage = "Failed to load data"
        lowEndPredictionView.addView(createPredictionTextView(errorMessage))
        midEndPredictionView.addView(createPredictionTextView(errorMessage))
        highEndPredictionView.addView(createPredictionTextView(errorMessage))
    }
}
