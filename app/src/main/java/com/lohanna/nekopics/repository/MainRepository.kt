package com.lohanna.nekopics.repository

import com.lohanna.nekopics.network.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    fun getCats() = retrofitService.getCats(1, 10)
}