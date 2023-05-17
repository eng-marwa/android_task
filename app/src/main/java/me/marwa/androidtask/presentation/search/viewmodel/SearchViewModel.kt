package me.marwa.androidtask.presentation.search.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import me.marwa.androidtask.app.BaseException
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.domain.use_cases.ProductsUseCases
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productsUseCases: ProductsUseCases) :
    ViewModel() {
    val productLiveData = MutableLiveData<Either<List<Product>, BaseException>>()

    fun searchFor(searchText: String?) {
        val productsList = arrayListOf<Product>()
        productsUseCases.invoke(onSuccess = {
            it.forEach { product ->
                if (product.title?.lowercase() == searchText?.lowercase() || product.category?.lowercase() == searchText?.lowercase()) {
                    productsList.add(product)
                }
            }
            Log.d(TAG, "searchFor: ${productsList}")

            productLiveData.value = Either.Left(productsList)
        }, onError = {
            it?.let {
                productLiveData.value = Either.Right(BaseException(message = "No data found"))
            }
        })
    }

    fun autoSearchFor(searchText: String?) {
        val productsList = arrayListOf<Product>()
        productsUseCases.invoke(onSuccess = {
            it.forEach { product ->
                if (product.title?.lowercase()
                        ?.startsWith(searchText?.lowercase()!!) == true || product.category?.lowercase()
                        ?.startsWith(searchText?.lowercase()!!) == true
                ) {
                    productsList.add(product)
                }
            }
            Log.d(TAG, "autoSearchFor: ${searchText?.lowercase()}")

            Log.d(TAG, "autoSearchFor: ${productsList}")
            productLiveData.value = Either.Left(productsList)
        }, onError = {
            it?.let {
                productLiveData.value = Either.Right(BaseException(message = "No data found"))
            }
        })
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}