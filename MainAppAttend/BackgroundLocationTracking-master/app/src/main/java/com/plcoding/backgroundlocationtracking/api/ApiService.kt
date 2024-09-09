package com.plcoding.backgroundlocationtracking.api

import com.plcoding.backgroundlocationtracking.models.request.EmployeeRegistration
import com.plcoding.backgroundlocationtracking.models.response.ApiResponse
import com.plcoding.backgroundlocationtracking.models.response.EmployeePositionListResponse
import com.plcoding.backgroundlocationtracking.models.response.OfficeListResponse
import com.plcoding.backgroundlocationtracking.models.response.OrganisationListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("employee_reg.php")
    fun employeeRegister(@Body user: EmployeeRegistration):Call< ApiResponse>

    @GET("get_organisation_list.php")
    fun getOrganisationList():Call<OrganisationListResponse>

    @GET("get_office_list.php")
    fun getOfficeList(@Query ("organization_id") org_id:Int):Call<OfficeListResponse>

    @GET("get_position_list.php")
    fun getPositionList(@Query ("office_id") office_id:Int):Call<EmployeePositionListResponse>

}
