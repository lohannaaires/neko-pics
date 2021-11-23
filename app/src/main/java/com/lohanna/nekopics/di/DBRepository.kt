package com.lohanna.nekopics.di

import androidx.lifecycle.LiveData
import com.lohanna.nekopics.db.AppDatabase
import com.lohanna.nekopics.db.entity.CatEntity
import com.lohanna.nekopics.di.Transformer.convertCatModelToCatEntity
import com.lohanna.nekopics.model.CatModel
import javax.inject.Inject

class DBRepository @Inject constructor(private val appDatabase: AppDatabase) {

    suspend fun insert(cat: CatModel): Long {
        return appDatabase.catDao()
            .insert(convertCatModelToCatEntity(cat))
    }

    suspend fun deleteAll() {
        appDatabase.catDao().deleteAll()
    }

    fun getAllCats(): LiveData<List<CatEntity>> {
        return appDatabase.catDao().getAllCats()
    }

}