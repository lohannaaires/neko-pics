package com.lohanna.nekopics.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lohanna.nekopics.model.ResponseModel
import com.lohanna.nekopics.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val catsList = MutableLiveData<List<ResponseModel>>()
    val errorMessage = MutableLiveData<String>()

    fun getCats() {

        val response = repository.getCats()

        response.enqueue(object : Callback<List<ResponseModel>> {
            override fun onResponse(call: Call<List<ResponseModel>>, response: Response<List<ResponseModel>>) {
                catsList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<ResponseModel>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}