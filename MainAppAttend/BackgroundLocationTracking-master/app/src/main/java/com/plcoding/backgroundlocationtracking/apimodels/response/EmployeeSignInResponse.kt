package com.plcoding.backgroundlocationtracking.apimodels.response

data class EmployeeSignInResponse(
    val status: String,
    val code: Int,
    val user: EmployeeSignInData
)


data class EmployeeSignInData(
    val user_id: Int,
    val name: String,
    val email: String,
    val employeeId: String,
    val mobile_number: String,
    val position: String,
    val org_id: Int,
    val office_id: Int,
    val approved: Int
)
