package com.plcoding.backgroundlocationtracking.apimodels.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val status: String,  // "success" or "error"
    val code: Int,       // HTTP status code, e.g., 200, 400, 500
    val message: String  // Response message, e.g., "Employee registration successful" or error details
)

