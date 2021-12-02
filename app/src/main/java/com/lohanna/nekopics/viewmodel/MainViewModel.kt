package com.lohanna.nekopics.viewmodel

import androidx.lifecycle.*
import com.lohanna.nekopics.di.DBRepository
import com.lohanna.nekopics.di.NetworkRepository
import com.lohanna.nekopics.di.Transformer
import com.lohanna.nekopics.model.CatModel
import com.lohanna.nekopics.utils.Constants.LIMIT
import com.lohanna.nekopics.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dbRepository: DBRepository
): ViewModel() {

    private val _catsList = MutableLiveData<DataHandler<List<CatModel>>>()

    fun getCats() {
        _catsList.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getCats(1, LIMIT)
            _catsList.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<List<CatModel>>): DataHandler<List<CatModel>> {

        if (response.isSuccessful) {
            viewModelScope.launch {
                dbRepository.deleteAll()
            }

            response.body()?.let { it ->
                viewModelScope.launch {
                    it.forEach {
                        dbRepository.insert(it)
                    }
                }
                return DataHandler.SUCCESS(it)
            }

        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    var catsList: LiveData<DataHandler<List<CatModel>>> = Transformations.map(getAllCats()) { list ->
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