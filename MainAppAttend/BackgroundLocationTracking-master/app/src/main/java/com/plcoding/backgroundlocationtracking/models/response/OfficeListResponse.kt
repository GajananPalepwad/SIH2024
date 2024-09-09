package com.plcoding.backgroundlocationtracking.models.response

data class OfficeListResponse(
    val status: String,
    val code: Int,
    val data: List<OfficeListData>
)

data class OfficeListData(
    val office_id: Int,
    val office_name: String,
    val latitude: Double,
    val longitude: Double
)
