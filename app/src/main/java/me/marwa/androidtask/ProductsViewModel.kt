package me.marwa.androidtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import me.marwa.androidtask.app.BaseException
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.data.model.ResponseProducts
import me.marwa.androidtask.domain.use_cases.ProductsUseCases
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsUseCases: ProductsUseCases) :
    ViewModel() {
    val clothesLiveData = MutableLiveData<Either<List<Product>, BaseException>>()
    val electronicsLiveData = MutableLiveData<Either<List<Product>, BaseException>>()
    val jeweleryLiveData = MutableLiveData<Either<List<Product>, BaseException>>()
    fun getProducts() {
        productsUseCases.invoke(onSuccess = {
            clothesLiveData.value = Either.Left(it.filter {
                it.category == "men's clothing" || it.category == "women's clothing"
            })
            electronicsLiveData.value =
                Either.Left(it.filter { it.category == "electronics" })
            jeweleryLiveData.value = Either.Left(it.filter { it.category == "jewelery" })


        }, onError = {
            it?.let {
                clothesLiveData.value = Either.Right(it)
                electronicsLiveData.value = Either.Right(it)
                jeweleryLiveData.value = Either.Right(it)

            }
        })
    }


}