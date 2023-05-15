package me.marwa.androidtask.data.datasource.remote.remote_repository

import me.marwa.androidtask.data.datasource.remote.api.ApiServices
import me.marwa.androidtask.data.datasource.remote.api.BaseApiProvider
import me.marwa.androidtask.data.datasource.remote.api.ApiResponseCallbacks
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.data.model.ResponseProducts
import me.marwa.androidtask.utils.ui
import javax.inject.Inject

interface ProductsRemoteDS {
    fun getProducts(apiResponseCallbacks: ApiResponseCallbacks<List<Product>>)
}

class ProductsRemoteDSImp @Inject constructor(private val apiService: ApiServices) :
    ProductsRemoteDS , BaseApiProvider(){
    override fun getProducts(apiResponseCallbacks: ApiResponseCallbacks<List<Product>>) {
        ui {
            apiRequest({ apiService.getProducts() }, apiResponseCallbacks)
        }
    }

}