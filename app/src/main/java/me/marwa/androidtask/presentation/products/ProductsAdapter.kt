//package me.marwa.androidtask.presentation.products
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import me.marwa.androidtask.R
//import me.marwa.androidtask.data.model.Product
//import me.marwa.androidtask.databinding.CategoryRowBinding
//
//class ProductsAdapter() : RecyclerView.Adapter<ViewHolder>() {
//
//    private val productsDiffCallBack = object : DiffUtil.ItemCallback<Product>() {
//        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
//            return oldItem.id == newItem.id;
//        }
//
//        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
//            return oldItem == newItem
//        }
//    }
//    val listDiffer = AsyncListDiffer(this, productsDiffCallBack)
//
//    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(position)
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item,null,false)
//        return ItemViewHolder(view)
//    }
//
//    inner class CategoryViewHolder(binding:CategoryRowBinding):RecyclerView.ViewHolder(binding.root){}
//    inner class ProductViewHolder(binding:):RecyclerView.ViewHolder(binding.rooe){}
//
//    override fun getItemCount(): Int {
//        return listDiffer.currentList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    companion object{
//    }
//}