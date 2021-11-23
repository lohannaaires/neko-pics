package com.lohanna.nekopics.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatModel(
    val id: String,
    val url: String
):Parcelable

