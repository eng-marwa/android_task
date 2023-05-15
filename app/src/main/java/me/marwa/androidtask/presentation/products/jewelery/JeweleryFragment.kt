package me.marwa.androidtask.presentation.products.jewelery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import me.marwa.androidtask.ProductsViewModel
import me.marwa.androidtask.R
import me.marwa.androidtask.databinding.FragmentClothesBinding
import me.marwa.androidtask.databinding.FragmentJeweleryBinding
import me.marwa.androidtask.presentation.products.clothes.ClothesAdapter
import me.marwa.androidtask.presentation.products.clothes.ClothesFragment
import me.marwa.androidtask.utils.showToast


class JeweleryFragment : Fragment() {
    private var _binding: FragmentJeweleryBinding? = null
    val binding get() = _binding!!

    private lateinit var adapter: JeweleryAdapter
    private val productViewModel by activityViewModels<ProductsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJeweleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeJewelery()
    }

    private fun initViews() {
        setupJeweleryRV()
    }

    private fun setupJeweleryRV() {
        context?.let { context ->
            binding.rvJewelery.layoutManager = GridLayoutManager(context,3)
            adapter = JeweleryAdapter()
            binding.rvJewelery.adapter = adapter
        }
    }

    private fun observeJewelery() {
        productViewModel.jeweleryLiveData.observe(viewLifecycleOwner) {
            it?.fold({
                if (::adapter.isInitialized)
                    adapter.setData(it)
            }, {
                showToast(it.getMessage())
            })
        }
    }

    companion object {
        private const val TAG = "JeweleryFragment"
        @JvmStatic
        fun newInstance() =
            JeweleryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}