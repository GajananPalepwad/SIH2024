package com.plcoding.backgroundlocationtracking.api

import com.plcoding.backgroundlocationtracking.models.request.EmployeeRegistration
import com.plcoding.backgroundlocationtracking.models.response.ApiResponse
import com.plcoding.backgroundlocationtracking.models.response.OrganisationListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("employee_reg.php")
    fun employeeRegister(@Body user: EmployeeRegistration):Call< ApiResponse>

    @GET("get_organisation_list.php")
    fun getOrganisationList():Call<OrganisationListResponse>

}
