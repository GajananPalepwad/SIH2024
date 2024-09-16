package com.plcoding.backgroundlocationtracking.apimodels.request

data class CheckInRequest(
    val user_id: Int,
    val office_id: Int,
    val check_in_time: String, //hh:mm:ss
    val attendance_date: String //yyyy-mm-dd
)


