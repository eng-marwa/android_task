package me.marwa.androidtask.domain.use_cases

import me.marwa.androidtask.app.BaseException
import me.marwa.androidtask.data.model.ApiResponseCallbacks
import me.marwa.androidtask.data.model.ResponseProducts
import me.marwa.androidtask.domain.repository.ProductsRepository

class ProductsUseCases(private val productsRepository: ProductsRepository) {
    fun invoke(
        onSuccess: (ResponseProducts) -> Unit,
        onError: (BaseException?) -> Unit,
    ) {
        productsRepository.getProducts(ApiResponseCallbacks(onResult = {
            it?.let { onSuccess(it) }
        }, onError = {
            onError(it)
        }))
    }
}