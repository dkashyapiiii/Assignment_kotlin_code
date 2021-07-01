package com.diksha.employeedata.retrofit

import com.diksha.employeedata.ModelClass.EmployeeModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @get:GET("getAllDetails")
    val employeeModel: Call<EmployeeModel?>?
}