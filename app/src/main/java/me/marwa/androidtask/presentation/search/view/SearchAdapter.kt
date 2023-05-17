package me.marwa.androidtask.presentation.search.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.SearchRowBinding
import me.marwa.androidtask.presentation.products.jewelery.JeweleryAdapter

class SearchAdapter(private var itemClicked: ProductItemAction) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private lateinit var context: Context
    private val products = arrayListOf<Product>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        context = parent.context
        return SearchViewHolder(
            SearchRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class SearchViewHolder(private val binding: SearchRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide.with(context).asBitmap().load(product.image).into(binding.ivItemPic)
            binding.lbItemName.text = product.title
        }

        fun onEvent(product: Product) {
            binding.root.setOnClickListener {
                itemClicked.onItemClick(product)
            }

        }
    }
    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
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
companion object{
    private const val TAG = "SearchAdapter"
}
}

