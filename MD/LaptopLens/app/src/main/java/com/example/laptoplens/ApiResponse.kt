package com.example.laptoplens

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SalesData(
    val date: String,
    val sales: Int
)

data class Result(
    val low: List<SalesData>,
    val mid: List<SalesData>,
    val high: List<SalesData>
)

data class PredictionResponse(
    val result: Result,
    val date: String
)

data class ApiResponse(
    val status: String,
    val message: String,
    val refreshToken: String? // Optional field for refresh token
)

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

data class LogoutRequest(
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)






