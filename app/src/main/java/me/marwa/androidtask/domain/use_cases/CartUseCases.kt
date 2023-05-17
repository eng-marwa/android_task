package me.marwa.androidtask.domain.use_cases

import me.marwa.androidtask.app.BaseException
import me.marwa.androidtask.domain.entity.CartEntity
import me.marwa.androidtask.domain.repository.CartRepository

class CartUseCases(private val cartRepository: CartRepository) {
    suspend fun invoke(
        onSuccess: (List<CartEntity>) -> Unit,
        onError: (BaseException?) -> Unit,
    ) {
        val list = cartRepository.cartItems()
        if (list.isNullOrEmpty()) {
            onError(BaseException(message = "Empty Cart"))
        } else {
            onSuccess(list)
        }
    }

    suspend fun saveItem(it: CartEntity?, onSuccess: () -> Unit, onError: (BaseException) -> Unit) {
        it?.let {
            val row = cartRepository.addItem(it)
            if (row > 0) onSuccess()
            else onError(BaseException(message = "Item not added"))
        }

    }

    suspend fun deleteItem(
        it: CartEntity?,
        onSuccess: (Int) -> Unit,
        onError: (BaseException) -> Unit
    ) {
        it?.let {
            val row = cartRepository.deleteItem(it)
            if (row > 0) onSuccess(row)
            else onError(BaseException(message = "Item not removed"))
        }

    }

    suspend fun updateItem(
        it: CartEntity?,
        onSuccess: (Int) -> Unit,
        onError: (BaseException) -> Unit
    ) {
        it?.let {
            val row = cartRepository.updateItem(it)
            if (row > 0) onSuccess(row)
            else onError(BaseException(message = "Item not updated"))
        }

    }

    suspend fun deleteAllItems(onSuccess: (Boolean) -> Unit,
                               onError: (BaseException) -> Unit) {

        if(cartRepository.deleteAllItems()){
            onSuccess(true)
        }else{
            onError(BaseException(message = "Can't clear your cart"))
        }

    }


}