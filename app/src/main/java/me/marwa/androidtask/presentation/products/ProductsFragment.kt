package me.marwa.androidtask.presentation.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.marwa.androidtask.R
import me.marwa.androidtask.databinding.FragmentProductsBinding
import me.marwa.androidtask.utils.showToast

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private val viewModel by viewModels<ProductsViewModel>()
    private lateinit var adapter: ProductsAdapter
    private var _binding: FragmentProductsBinding? = null
    val binding get() = _binding!!
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
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            it?.fold({
                adapter.listDiffer.submitList(it?.products)
            }, {
                showToast(it?.getMessage())
            })
        }
    }

    private fun initViews() {
        setupProductRV()
        viewModel.getProducts()
    }

    private fun setupProductRV() {
        context?.let { context ->
            val linearLayoutManager = GridLayoutManager(context, 2)
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