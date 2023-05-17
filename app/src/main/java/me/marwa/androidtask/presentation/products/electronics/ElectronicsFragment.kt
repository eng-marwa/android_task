package me.marwa.androidtask.presentation.products.electronics

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import me.marwa.androidtask.presentation.ProductsViewModel
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.FragmentElectronicsBinding
import me.marwa.androidtask.presentation.ItemActivity
import me.marwa.androidtask.utils.showToast

class ElectronicsFragment : Fragment(), ElectronicsAdapter.ProductItemAction {
    private var _binding: FragmentElectronicsBinding? = null
    val binding get() = _binding!!

    private lateinit var adapter: ElectronicsAdapter
    private val productViewModel by activityViewModels<ProductsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentElectronicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeElectronics()
    }

    private fun initViews() {
        setupElectronicsRV()
    }

    private fun setupElectronicsRV() {
        context?.let { context ->
            binding.rvElectronics.layoutManager = GridLayoutManager(context, 1)
            adapter = ElectronicsAdapter(this)
            binding.rvElectronics.adapter = adapter
        }
    }

    private fun observeElectronics() {
        productViewModel.electronicsLiveData.observe(viewLifecycleOwner) {
            it?.fold({
                if (::adapter.isInitialized)
                    adapter.setData(it)
            }, {
                showToast(it.getMessage())
            })
        }
    }

    companion object {
        private const val TAG = "ElectronicsFragment"

        @JvmStatic
        fun newInstance() =
            ElectronicsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItemClick(item: Product) {
        activity?.let {
            startActivity(Intent(it, ItemActivity::class.java).apply {
                putExtra("product",item)
            })
        }
    }
}