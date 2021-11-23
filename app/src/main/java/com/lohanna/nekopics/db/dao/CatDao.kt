package com.lohanna.nekopics.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lohanna.nekopics.db.entity.CatEntity

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(catEntity: CatEntity): Long

    @Query("SELECT * FROM CAT")
    fun getAllCats():LiveData<List<CatEntity>>

    @Query("DELETE FROM CAT")
    suspend fun deleteAll()

}