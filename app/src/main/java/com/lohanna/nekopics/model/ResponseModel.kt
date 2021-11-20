package com.lohanna.nekopics.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String
)

