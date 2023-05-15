package me.marwa.androidtask.domain.repository

import me.marwa.androidtask.data.datasource.remote.remote_repository.ProductsRemoteDS
import me.marwa.androidtask.data.model.ApiResponseCallbacks
import me.marwa.androidtask.data.model.ResponseProducts
import javax.inject.Inject

interface ProductsRepository {
    fun getProducts(apiResponseCallbacks: ApiResponseCallbacks<ResponseProducts>)
}

class ProductsRepositoryImp @Inject constructor(private val productsRemoteDS: ProductsRemoteDS) :
    ProductsRepository {
    override fun getProducts(apiResponseCallbacks: ApiResponseCallbacks<ResponseProducts>) =
        productsRemoteDS.getProducts(apiResponseCallbacks)

}