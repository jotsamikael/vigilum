package com.example.vigilum.data.remote.request

data class PersonalInfoRequest (
    val firstname:String,
    val lastname:String,
    val sex:String,
    val phone:String,
    val documentIdNumber:String,
    val enterprise:String,
)