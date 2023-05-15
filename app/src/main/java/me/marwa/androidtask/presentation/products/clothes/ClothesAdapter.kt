package me.marwa.androidtask.presentation.products.clothes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.marwa.androidtask.R
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.MenBinding
import me.marwa.androidtask.databinding.WomenBinding

class ClothesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context
    private val clothes = arrayListOf<Product>()
    override fun getItemViewType(position: Int): Int {
        return when (clothes[position].category) {
            "men's clothing" -> MEN
            else -> WOMEN
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            WOMEN ->
                WomenViewHolder(
                    WomenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )

            else -> MenViewHolder(
                MenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    inner class WomenViewHolder(private val binding: WomenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide.with(context).asBitmap().load(product.image).into(binding.ivItemPic)
            binding.lbItemName.text = product.title
            binding.lbItemPrice.text = "${product.price} $"
            binding.lbRating.text = "${product.rating?.count}"
            binding.lbType.text = context.getString(R.string.women)
        }

        fun onEvent() {
            binding.root.setOnClickListener {

            }
            binding.btnCart.setOnClickListener {

            }
        }
    }

    inner class MenViewHolder(private val binding: MenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide.with(context).asBitmap().load(product.image).into(binding.ivItemPic)
            binding.lbItemName.text = product.title
            binding.lbItemPrice.text = "${product.price} $"
            binding.lbRating.text = "${product.rating?.count}"
            binding.lbType.text = context.getString(R.string.men)
        }

        fun onEvent() {
            binding.root.setOnClickListener {

            }
            binding.btnCart.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        return clothes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MEN -> {
                val mHolder = holder as MenViewHolder
                mHolder.bind(clothes[position])
                mHolder.onEvent()
            }

            else -> {
                val wHolder = holder as WomenViewHolder
                wHolder.bind(clothes[position])
                wHolder.onEvent()
            }
        }
    }

    fun setData(clothes: List<Product>) {
        this.clothes.clear()
        this.clothes.addAll(clothes)
        notifyDataSetChanged()
    }

    companion object {
        const val MEN = 0
        const val WOMEN = 1
    }
}