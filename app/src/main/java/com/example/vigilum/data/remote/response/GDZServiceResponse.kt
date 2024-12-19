package com.example.vigilum.data.remote.response

data class GDZServiceResponse(
    val status: String,
    val message: String,
    val totalResults: Int,
    val objectList: List<GDZService>

    )