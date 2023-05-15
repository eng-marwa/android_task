package me.marwa.androidtask.presentation.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import me.marwa.androidtask.app.BaseException
import me.marwa.androidtask.data.model.ResponseProducts
import me.marwa.androidtask.domain.use_cases.ProductsUseCases
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsUseCases: ProductsUseCases) :
    ViewModel() {
    val productsLiveData = MutableLiveData<Either<ResponseProducts, BaseException>>()
    fun getProducts() {
        productsUseCases.invoke(onSuccess = {
            productsLiveData.value = Either.Left(it)
        }, onError = {
            it?.let {
                productsLiveData.value = Either.Right(it)

            }
        })
    }


}