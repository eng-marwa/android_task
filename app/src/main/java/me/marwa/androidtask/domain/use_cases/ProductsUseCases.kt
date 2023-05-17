package me.marwa.androidtask.domain.use_cases

import me.marwa.androidtask.app.BaseException
import me.marwa.androidtask.data.datasource.remote.api.ApiResponseCallbacks
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.domain.repository.ProductsRepository

class ProductsUseCases(private val productsRepository: ProductsRepository) {
    fun invoke(
        onSuccess: (List<Product>) -> Unit,
        onError: (BaseException?) -> Unit,
    ) {
        productsRepository.getProducts(ApiResponseCallbacks(onResult = {
            it?.let { onSuccess(it) }
        }, onError = {
            onError(it)
        }))
    }
}