package com.lohanna.nekopics.di

import com.lohanna.nekopics.api.RetrofitService
import com.lohanna.nekopics.model.CatModel
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val retrofitService: RetrofitService) {

    suspend fun getCats(page: Int, limit: Int): Response<List<CatModel>> {
        return retrofitService.getCats(page, limit)
    }

}