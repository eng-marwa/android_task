package me.marwa.androidtask.domain.repository

import me.marwa.androidtask.data.datasource.local.room.CartDao
import me.marwa.androidtask.domain.entity.CartEntity
import javax.inject.Inject

interface CartRepository {
    suspend fun cartItems(): List<CartEntity>
    suspend fun addItem(cart: CartEntity): Long
    suspend fun deleteItem(cart: CartEntity): Int
    suspend fun updateItem(cart: CartEntity): Int
}

class CartRepositoryImp @Inject constructor(private val cartDao: CartDao) :
    CartRepository {
    override suspend fun cartItems(): List<CartEntity> {
        return cartDao.cartItems()
    }

    override suspend fun addItem(cart: CartEntity): Long {
        return cartDao.addItem(cart)
    }

    override suspend fun deleteItem(cart: CartEntity): Int {
        return cartDao.deleteItem(cart)
    }

    override suspend fun updateItem(cart: CartEntity): Int {
        return cartDao.updateItem(cart)
    }


}
