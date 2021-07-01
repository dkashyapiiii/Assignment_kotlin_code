package com.diksha.employeedata.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.diksha.employeedata.ModelClass.EmployeeModel
import com.diksha.employeedata.retrofit.ApiRequest
import com.diksha.employeedata.retrofit.RetrofitRequest.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {
    private val apiRequest: ApiRequest
    val employeeModel: LiveData<EmployeeModel?>
        get() {
            val data = MutableLiveData<EmployeeModel?>()
            apiRequest.employeeModel?.enqueue(object : Callback<EmployeeModel?> {
                override fun onResponse(
                    call: Call<EmployeeModel?>,
                    response: Response<EmployeeModel?>
                ) {
                    Log.d(TAG, "onResponse response:: $response")
                    if (response.body() != null) {
                        data.setValue(response.body())
                    }
                }

                override fun onFailure(call: Call<EmployeeModel?>, t: Throwable) {
                    data.setValue(null)
                }
            })
            return data
        }

    companion object {
        private val TAG = ArticleRepository::class.java.simpleName
    }

    init {
        apiRequest = retrofitInstance!!.create(ApiRequest::class.java)
    }
}