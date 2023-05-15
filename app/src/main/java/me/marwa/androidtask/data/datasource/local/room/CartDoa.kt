package me.marwa.androidtask.data.datasource.local.room

import androidx.room.*
import me.marwa.androidtask.domain.entity.CartEntity

@Dao
interface CartDao {

    @Query("SELECT * FROM CartEntity  where cantDeleted == 0")
    suspend fun cartItems(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(cart: CartEntity): Long

    @Delete
    suspend fun deleteItem(cart: CartEntity): Int

    @Update
    suspend fun updateItem(cart: CartEntity): Int

    @Query("DELETE FROM CartEntity where cantDeleted == 0")
    suspend fun deleteAllItems()

    @Query("SELECT EXISTS(SELECT * FROM CartEntity where cantDeleted == 1)")
    fun isExists(): Boolean

}