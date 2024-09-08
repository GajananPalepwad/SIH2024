package com.plcoding.backgroundlocationtracking.api

import com.plcoding.backgroundlocationtracking.models.request.EmployeeRegistration
import com.plcoding.backgroundlocationtracking.models.response.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("employee_reg.php")
    fun employeeRegister(@Body user: EmployeeRegistration):Call< ApiResponse>

//    companion object {
//        fun register(): ApiResponse {
//            return ApiResponse()
//        }
//    }

}
