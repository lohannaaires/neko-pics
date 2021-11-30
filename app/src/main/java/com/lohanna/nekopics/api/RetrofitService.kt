package com.lohanna.nekopics.api

import com.lohanna.nekopics.model.CatModel
import com.lohanna.nekopics.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @Headers("x-api-key: $API_KEY")
    @GET("images/search/")
    suspend fun getCats(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : Response<List<CatModel>>

}