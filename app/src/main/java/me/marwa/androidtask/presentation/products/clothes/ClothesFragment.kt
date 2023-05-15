package me.marwa.androidtask.presentation.products.clothes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import me.marwa.androidtask.ProductsViewModel
import me.marwa.androidtask.databinding.FragmentClothesBinding
import me.marwa.androidtask.utils.showToast

class ClothesFragment : Fragment() {
    private var _binding: FragmentClothesBinding? = null
    val binding get() = _binding!!

    private lateinit var adapter: ClothesAdapter
    private val productViewModel by activityViewModels<ProductsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentClothesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeClothes()
    }

    private fun initViews() {
        setupClothesRV()
    }

    private fun setupClothesRV() {
        context?.let { context ->
            val linearLayoutManager = GridLayoutManager(context, 2)
            binding.rvClothes.layoutManager = linearLayoutManager
            adapter = ClothesAdapter()
            binding.rvClothes.adapter = adapter
        }
    }

    private fun observeClothes() {
        productViewModel.clothesLiveData.observe(viewLifecycleOwner) {
            it?.fold({
                var clothes = it.shuffled()
                if (::adapter.isInitialized) {
                    adapter.setData(clothes)
                }
            }, {
                showToast(it.getMessage())
            })
        }
    }

    companion object {
        private const val TAG = "ClothesFragment"

        @JvmStatic
        fun newInstance() =
            ClothesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}