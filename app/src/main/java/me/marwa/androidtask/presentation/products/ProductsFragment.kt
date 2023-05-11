package me.marwa.androidtask.presentation.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import me.marwa.androidtask.databinding.FragmentProductsBinding


class ProductsFragment : Fragment() {
    private lateinit var adapter: ProductsAdapter
    private lateinit var binding: FragmentProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.rvProducts
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {

    }

    private fun initViews() {
        setupProductRV()
    }

    private fun setupProductRV() {
        context?.let { context ->
            val linearLayoutManager = GridLayoutManager(context,2)
            binding.rvProducts.layoutManager = linearLayoutManager
            adapter = ProductsAdapter()
            binding.rvProducts.adapter = adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}