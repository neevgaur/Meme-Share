package com.gaurneev.memeshare

import retrofit2.Call
import retrofit2.http.GET

interface apiInterface {

    @GET("gimme")
    fun getMeme():Call<Data>
}