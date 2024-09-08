package com.plcoding.backgroundlocationtracking.models.request

data class EmployeeRegistration(
    val Name: String,
    val Employee_ID: String,
    val Contact_No: String,
    val Organization: String,
    val Position: String,
    val Email: String,
    val Password: String
)
