package com.example.pallete.common

import com.example.pallete.retrofit.RetrofitClient
import com.example.pallete.retrofitservices.RetrofitServices

object Common {
    private val BASE_URL = "http://192.168.0.108:3000"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}