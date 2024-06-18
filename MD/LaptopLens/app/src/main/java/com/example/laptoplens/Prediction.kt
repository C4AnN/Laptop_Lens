package com.example.laptoplens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Prediction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prediction)

        val lowEndChart = findViewById<LineChart>(R.id.low_end_chart)
        val midEndChart = findViewById<LineChart>(R.id.mid_end_chart)
        val highEndChart = findViewById<LineChart>(R.id.high_end_chart)

        // Fetch predictions from API
        RetrofitClient.getApiService(this).getPredictions().enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { predictionResponse ->
                        Log.d("Prediction", "API call successful: $predictionResponse")
                        displayPredictions(predictionResponse.result.low, lowEndChart)
                        displayPredictions(predictionResponse.result.mid, midEndChart)
                        displayPredictions(predictionResponse.result.high, highEndChart)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Prediction", "Response not successful: $errorBody")
                    displayError(lowEndChart, midEndChart, highEndChart)
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Log.e("Prediction", "API call failed: ${t.message}", t)
                displayError(lowEndChart, midEndChart, highEndChart)
            }
        })
    }

    private fun displayPredictions(predictions: List<SalesData>, chart: LineChart) {
        val entries = predictions.mapIndexed { index, salesData ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            try {
                val date = dateFormat.parse(salesData.date)
                val timestamp = date?.time?.toFloat() ?: index.toFloat() // Default to index if parsing fails
                Entry(timestamp, salesData.sales.toFloat())
            } catch (e: Exception) {
                Log.e("Prediction", "Error parsing date: ${salesData.date}", e)
                Entry(index.toFloat(), salesData.sales.toFloat()) // Handle parsing error gracefully
            }
        }

        val dataSet = LineDataSet(entries, "Sales Data").apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = getColor(R.color.purple_500)
            valueTextColor = getColor(R.color.black)
            valueTextSize = 12f
        }

        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate()

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f

        // Set custom value formatter for XAxis to display dates
        xAxis.valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            override fun getFormattedValue(value: Float): String {
                val dateMillis = value.toLong()
                return dateFormat.format(Date(dateMillis))
            }
        }
    }

    private fun displayError(vararg charts: LineChart) {
        val errorMessage = "Failed to load data"
        Log.e("Prediction", errorMessage)
        charts.forEach { chart ->
            chart.clear()
            val entries = listOf(Entry(0f, 0f))
            val dataSet = LineDataSet(entries, errorMessage)
            val lineData = LineData(dataSet)
            chart.data = lineData
            chart.invalidate()
        }
    }
}
