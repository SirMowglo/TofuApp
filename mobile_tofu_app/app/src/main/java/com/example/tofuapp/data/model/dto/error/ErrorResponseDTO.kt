package com.example.tofuapp.data.model.dto.error

import com.google.gson.annotations.SerializedName

data class ErrorResponseDTO(
    @SerializedName("dateTime")
    val dateTime: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("status")
    val status: String
)