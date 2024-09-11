package com.plcoding.backgroundlocationtracking.apimodels.request

data class EmployeeRegistration(
    val Name: String,
    val EmployeeId: String,
    val MobileNumber: String,
    val Organization: String,
    val Position: String,
    val Email: String,
    val Password: String,
    val Org_Id: Int,
    val Office_Id: Int
)
