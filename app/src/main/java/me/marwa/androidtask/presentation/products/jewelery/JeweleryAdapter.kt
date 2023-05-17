package me.marwa.androidtask.presentation.products.jewelery

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.JewerlyBinding
import me.marwa.androidtask.databinding.ProductItemBinding
import me.marwa.androidtask.presentation.products.electronics.ElectronicsAdapter

class JeweleryAdapter(private var itemClicked: ProductItemAction) : RecyclerView.Adapter<JeweleryAdapter.JewelryViewHolder>() {
    private lateinit var context: Context
    private val products = arrayListOf<Product>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JewelryViewHolder {
        context = parent.context
        return JewelryViewHolder(
            JewerlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class JewelryViewHolder(private val binding: JewerlyBinding) :
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
            itemClicked.addItemToCart(product)
            }
        }
    }


    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: JewelryViewHolder, position: Int) {
        holder.bind(products[position])
        holder.onEvent(products[position])
    }

    fun setData(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    interface ProductItemAction {
        fun onItemClick(product: Product)
        fun addItemToCart(product: Product)
    }
}