package com.plcoding.backgroundlocationtracking.apimodels.request

data class CheckOutRequest(
    val user_id: Int,
    val office_id: Int,
    val check_out_time: String,
    val attendance_date: String
)
