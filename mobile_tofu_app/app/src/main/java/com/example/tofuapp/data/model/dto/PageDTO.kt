package com.example.tofuapp.data.model.dto

data class PageDTO<T>(
    val content: List<T>,
    val first: Boolean,
    val last: Boolean,
    val totalElements: Int,
    val totalPages: Int
)