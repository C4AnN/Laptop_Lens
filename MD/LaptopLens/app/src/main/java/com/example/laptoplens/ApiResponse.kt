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
    val tokenId: String? // Optional field for refresh token
)

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

data class LogoutRequest(
    val email: String
)

data class LoginRequest(
    val email: String,
    val password: String,
    val tokenId: String? = null
)

data class StockResponse(
    val status: String,
    val data: List<ProductData>
)

data class IncomingStockReq (
    val name: String,
    val vendor_name: String,
    val price: Double,
    val date: String,
    val quantity: Int
)

data class OutgoingStockReq (
    val name: String,
    val price: Double,
    val date: String,
    val quantity: Int
)

data class IncomingStockData (
    val id: Int,
    val name: String,
    val vendor_name: String,
    val price: String,
    val date: String,
    val sales: Int,
    val createdAt: String,
    val updatedAt: String
)

data class OutgoingStockData (
    val id: Int,
    val name: String,
    val price: String,
    val date: String,
    val total_stocks: Int,
    val createdAt: String,
    val updatedAt: String
)

data class IncomingResp (
    val status: String,
    val data: IncomingStockData
)

data class OutgoingResp (
    val status: String,
    val data: OutgoingStockData
)

data class ProductData(
    val id: Int,
    val name: String,
    val vendor_name: String,
    val price: String,
    val total_stocks: Int,
    val createdAt: String,
    val updatedAt: String
)

data class UserData (
    val firstName: String,
    val lastName: String,
    val email: String
)

data class UserDataResponse (
    val status: String,
    val data: UserData
)

data class ExportRequest(
    val month: Int
)

data class ProductOutData(
    val name: String,
    val price: Double,
    val stock: Int
)