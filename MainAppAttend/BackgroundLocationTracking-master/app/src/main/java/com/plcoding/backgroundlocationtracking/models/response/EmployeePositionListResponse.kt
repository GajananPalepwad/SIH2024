package com.plcoding.backgroundlocationtracking.models.response

data class EmployeePositionListResponse(
    val status: String,
    val code: Int,
    val data: List<OfficePositionData>
)

data class OfficePositionData(
    val position_id: Int,
    val position_name: String

)
