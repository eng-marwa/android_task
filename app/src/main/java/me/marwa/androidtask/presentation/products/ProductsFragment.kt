package me.marwa.androidtask.presentation.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.marwa.androidtask.ProductsViewModel
import me.marwa.androidtask.databinding.FragmentProductsBinding

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private val viewModel by viewModels<ProductsViewModel>()

    //    private lateinit var adapter: ProductsAdapter
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
//        observeViewModel()
    }

//    private fun observeViewModel() {
//        viewModel.productsLiveData.observe(viewLifecycleOwner) {
//            it?.fold({
//                adapter.listDiffer.submitList(it?.products)
//            }, {
//                showToast(it?.getMessage())
//            })
//        }
//    }

    private fun initViews() {
//        setupCategoriesRV()
//        getCategories()
    }

//    private fun setupCategoriesRV() {
//        context?.let { context ->
//            val linearLayoutManager = GridLayoutManager(context, 2)
//            binding.rvCategories.layoutManager = linearLayoutManager
//            adapter = ProductsAdapter()
//            binding.rvCategories.adapter = adapter
//        }
//    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}