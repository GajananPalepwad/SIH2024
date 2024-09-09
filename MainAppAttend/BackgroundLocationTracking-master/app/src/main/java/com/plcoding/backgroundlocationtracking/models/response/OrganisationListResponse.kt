package com.plcoding.backgroundlocationtracking.models.response

data class OrganisationListResponse(
    val status: String,
    val code: Int,
    val data: List<OrganisationData>
)

data class OrganisationData(

    val org_id: Int,
    val org_name: String,
    val org_address: String,
    val org_email: String,
)


