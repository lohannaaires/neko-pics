package com.lohanna.nekopics.viewmodel

import androidx.lifecycle.*
import com.lohanna.nekopics.di.DBRepository
import com.lohanna.nekopics.di.Transformer
import com.lohanna.nekopics.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfflineViewModel @Inject constructor(private val dbRepository: DBRepository) : ViewModel() {

    var catsList = Transformations.map(getAllCats()) { list ->

        val temp = list.map {
            Transformer.convertCatEntityToCatModel(it)
        }
        if (temp.isNullOrEmpty()) {
            DataHandler.ERROR(null, "LIST IS EMPTY OR NULL")
        } else {
            DataHandler.SUCCESS(temp)
        }

    }

    private fun getAllCats() = dbRepository.getAllCats()

}