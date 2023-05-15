package me.marwa.androidtask.data.datasource.remote.api

import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.data.model.ResponseProducts
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

}