package me.marwa.androidtask.presentation.cart.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.marwa.androidtask.databinding.CartBinding
import me.marwa.androidtask.domain.entity.CartEntity
import me.marwa.androidtask.presentation.products.electronics.ElectronicsAdapter

class CartAdapter :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var context: Context
    private var b: Boolean = true
    val updateItemLiveEvent = MutableLiveData<CartEntity>()
    val deleteItemLiveEvent = MutableLiveData<CartEntity>()
    val totalLiveEvent = MutableLiveData<Double>()
    val deleteAllItemsLiveEvent = MutableLiveData<Boolean>()
    private var total = 0.0
    private val totalMap = mutableMapOf<String, Double>()
    var sum = 0.0
    val list = arrayListOf<CartEntity>()


    inner class CartViewHolder(itemView: CartBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val ivItemPic = itemView.ivItemPic
        val lbItemName = itemView.lbItemName
        val lbQty = itemView.lbQty
        val lbItemPrice = itemView.lbItemPrice
        val btnDecrement = itemView.btnDecrement
        val btnIncrement = itemView.btnIncrement
        val btnDelete = itemView.btnDelete

    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = list[position]
        holder.lbItemName.text = cart.productName
        holder.lbQty.text = "${cart.qty}"
        holder.lbItemPrice.text = "${cart.productPrice * cart.qty}"

        if (b) {
            sum += holder.lbItemPrice.text.toString().toDouble()
            totalMap[cart.productId] = holder.lbItemPrice.text.toString().toDouble()
            updateTotal()
        }

        context.let {
            try {
                Glide.with(context)
                    .asBitmap()
                    .load(cart.productImage)
                    .into(holder.ivItemPic)
            } catch (e: Exception) {
            }
        }
        holder.btnIncrement.setOnClickListener {
            var count = holder.lbQty.text.toString().toInt()
            holder.lbQty.text = "${++count}"
            holder.lbItemPrice.text = "${count * cart.productPrice}"
            cart.qty = count
            updateItem(cart)
            sum = 0.0
            sum += holder.lbItemPrice.text.toString().toDouble()
            totalMap[cart.productId] = holder.lbItemPrice.text.toString().toDouble()
            updateTotal()

        }

        holder.btnDecrement.setOnClickListener {
            var count = holder.lbQty.text.toString().toInt()
            if (count != 1) {
                holder.lbQty.text = "${--count}"
                holder.lbItemPrice.text = "${count * cart.productPrice}"
                cart.qty = count
                updateItem(cart)
                sum = 0.0
                sum += holder.lbItemPrice.text.toString().toDouble()
                totalMap[cart.productId] = holder.lbItemPrice.text.toString().toDouble()
                updateTotal()
            }
        }



        holder.itemView.setOnClickListener {
            val qty = holder.lbQty.text.toString().toInt()
            cart.qty = qty
            context.let {

            }
        }

        holder.btnDelete.setOnClickListener {
            deleteItem(cart)
            b = false
        }
    }


    private fun updateTotal() {
        var total = 0.0
        totalMap.values.forEach { v -> total += v }
        totalLiveEvent.value = total
    }

    private fun updateItem(cart: CartEntity) {
        updateItemLiveEvent.value = cart
    }

    private fun deleteItem(cart: CartEntity) {
        deleteItemLiveEvent.value = cart
        list.remove(cart)
        notifyDataSetChanged()
    }

    fun setData(cartItems: List<CartEntity>) {
        clear()
        list.addAll(cartItems)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        context = parent.context
        return CartViewHolder(
            CartBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }


}