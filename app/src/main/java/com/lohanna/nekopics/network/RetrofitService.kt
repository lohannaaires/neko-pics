package com.lohanna.nekopics.network

import com.lohanna.nekopics.model.ResponseModel
import com.lohanna.nekopics.ui.API_KEY
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @Headers("x-api-key: $API_KEY")
    @GET("images/search/")
    fun getCats(
        @Query("order") page: Int,
        @Query("limit") limit: Int
    ) : Call<List<ResponseModel>>


    companion object {

        private var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.thecatapi.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }

}