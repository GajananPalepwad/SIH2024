package com.plcoding.backgroundlocationtracking.apimodels.response

data class UserDetailsResponse(
    val status: String,
    val code: Int,
    val message: String,
    val user: UserDetailsData,
    val office: OfficeDetailsData

)
data class UserDetailsData(
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
data class OfficeDetailsData(
    val latitude: Double,
    val longitude: Double
)

