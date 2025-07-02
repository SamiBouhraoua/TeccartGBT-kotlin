package com.example.projetsession

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @POST("chat/completions")
    @Headers(
        "Authorization: Bearer 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",
        "Content-Type: application/json"
    )
    fun createMessage(@Body request: Request): Call<Reponse>
}