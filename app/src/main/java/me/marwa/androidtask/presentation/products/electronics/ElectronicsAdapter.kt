package me.marwa.androidtask.presentation.products.electronics

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.ElectronicsBinding
import me.marwa.androidtask.domain.entity.CartEntity
import me.marwa.androidtask.presentation.products.clothes.ClothesAdapter

class ElectronicsAdapter(private var itemClicked: ProductItemAction) : RecyclerView.Adapter<ElectronicsAdapter.ElectronicsViewHolder>() {
    private lateinit var context: Context
    private val products = arrayListOf<Product>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectronicsViewHolder {
        context = parent.context
        return ElectronicsViewHolder(
            ElectronicsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class ElectronicsViewHolder(private val binding: ElectronicsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide.with(context).asBitmap().load(product.image).into(binding.ivItemPic)
            binding.lbItemName.text = product.title
            binding.lbItemPrice.text = "${product.price} $"

        }

        fun onEvent(product: Product) {
            binding.root.setOnClickListener {
                itemClicked.onItemClick(product)
            }
            binding.btnCart.setOnClickListener {

            }
        }
    }


    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ElectronicsViewHolder, position: Int) {
        holder.bind(products[position])
        holder.onEvent(products[position])
    }

    fun setData(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    interface ProductItemAction {
        fun onItemClick(item: Product)
    }
}