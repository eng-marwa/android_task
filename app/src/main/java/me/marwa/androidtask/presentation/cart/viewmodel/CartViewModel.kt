package me.marwa.androidtask.presentation.cart.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.marwa.androidtask.app.BaseException
import me.marwa.androidtask.domain.entity.CartEntity
import me.marwa.androidtask.domain.use_cases.CartUseCases
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCases: CartUseCases,
) : ViewModel() {
    val cartItemsLiveData = MutableLiveData<Either<List<CartEntity>, BaseException>>()
    val savedCartItemsLiveData = MutableLiveData<Either<CartEntity, BaseException>>()
    val deletedCartItemsLiveData = MutableLiveData<Either<Int, BaseException>>()
    val updatedCartItemsLiveData = MutableLiveData<Either<Int, BaseException>>()
    fun getCartItems() {
        viewModelScope.launch {
            cartUseCases.invoke(onSuccess = {
                cartItemsLiveData.value = Either.Left(it)
            }, onError = {
                it?.let {
                    cartItemsLiveData.value = Either.Right(it)
                }
            })
        }
    }

    fun saveItem(it: CartEntity?) {
        viewModelScope.launch {
            cartUseCases.saveItem(it, onSuccess = {
                it?.let {
                    savedCartItemsLiveData.value = Either.Left(it)
                }
            }, onError = {
                savedCartItemsLiveData.value = Either.Right(it)

            })
        }
    }

    fun deleteItem(it: CartEntity?) {
        viewModelScope.launch {
            cartUseCases.deleteItem(it, onSuccess = {
                it?.let {
                    deletedCartItemsLiveData.value = Either.Left(it)
                }
            }, onError = {
                deletedCartItemsLiveData.value = Either.Right(it)

            })
        }
    }

    fun updateItem(it: CartEntity?) {
        viewModelScope.launch {
            cartUseCases.updateItem(it, onSuccess = {
                it?.let {
                    updatedCartItemsLiveData.value = Either.Left(it)
                }
            }, onError = {
                updatedCartItemsLiveData.value = Either.Right(it)

            })
        }
    }

    fun updateQty(cart: CartEntity) {
        cart.qty++
        updateItem(cart)
    }

    companion object {
        private const val TAG = "CartViewModel"

    }


}