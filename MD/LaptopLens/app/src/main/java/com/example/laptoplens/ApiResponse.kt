package com.example.laptoplens

data class ApiResponse(
    val status: String,
    val message: String,
    val refreshToken: String? // Ini opsional jika tidak selalu ada di respons
)


