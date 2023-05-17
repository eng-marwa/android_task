package me.marwa.androidtask.domain.repository

import me.marwa.androidtask.data.datasource.remote.remote_repository.ProductsRemoteDS
import me.marwa.androidtask.data.datasource.remote.api.ApiResponseCallbacks
import me.marwa.androidtask.data.model.Product
import javax.inject.Inject

interface ProductsRepository {
    fun getProducts(apiResponseCallbacks: ApiResponseCallbacks<List<Product>>)
}

class ProductsRepositoryImp @Inject constructor(private val productsRemoteDS: ProductsRemoteDS) :
    ProductsRepository {
    override fun getProducts(apiResponseCallbacks: ApiResponseCallbacks<List<Product>>) =
        productsRemoteDS.getProducts(apiResponseCallbacks)

}