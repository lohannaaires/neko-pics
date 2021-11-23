package com.lohanna.nekopics.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CAT")
data class CatEntity(
    @PrimaryKey
    val id: String,
    val url: String
)