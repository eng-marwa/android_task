package me.marwa.androidtask.presentation.products.jewelery

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.marwa.androidtask.R
import me.marwa.androidtask.presentation.ProductsViewModel
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.FragmentJeweleryBinding
import me.marwa.androidtask.domain.entity.CartEntity
import me.marwa.androidtask.presentation.ItemActivity
import me.marwa.androidtask.presentation.cart.viewmodel.CartViewModel
import me.marwa.androidtask.utils.network
import me.marwa.androidtask.utils.showToast

@AndroidEntryPoint
class JeweleryFragment : Fragment(), JeweleryAdapter.ProductItemAction {
    private var _binding: FragmentJeweleryBinding? = null
    val binding get() = _binding!!

    private lateinit var adapter: JeweleryAdapter
    private val productViewModel by activityViewModels<ProductsViewModel>()
    private val cartViewModel by viewModels<CartViewModel>()


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
        observeViewModel()
    }
    private fun observeViewModel() {
        cartViewModel.savedCartItemsLiveData.observe(viewLifecycleOwner) {
            it.fold({
                showToast(getString(R.string.item_added_successfully))
            }, {
                showToast(it.getMessage())
            })
        }

    }
    private fun initViews() {
        setupJeweleryRV()
    }

    private fun setupJeweleryRV() {
        context?.let { context ->
            binding.rvJewelery.layoutManager = GridLayoutManager(context, 3)
            adapter = JeweleryAdapter(this)
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

    override fun onItemClick(item: Product) {
        activity?.let {
            startActivity(Intent(it, ItemActivity::class.java).apply {
                putExtra("product", item)
            })
        }
    }

    override fun addItemToCart(product: Product) {
        network {
            val theBitmap =
                Glide.with(requireContext()).asBitmap()
                    .load(product.image)
                    .submit(100, 100)
                    .get()
            lifecycleScope.launch {
                val content = async(Dispatchers.IO) {
                    val cart = CartEntity(
                        productId = product.id ?: "",
                        productName = product.title ?: "",
                        category = product.category ?: "",
                        productPrice = product.price ?: 0.0,
                        productDescription = product.description ?: "",
                        productImage = theBitmap,
                        qty = 1, itemTotal = product.price ?: 0.0
                    )
                    collectItems(cart)
                }

                content.await()
            }
        }
    }

    private suspend fun collectItems(cart: CartEntity) {
        var updated = false
        cartViewModel.getCartItems()
        withContext(Dispatchers.Main) {
            cartViewModel.cartItemsLiveData.observe(viewLifecycleOwner) {
                it?.fold({ cartItems ->
                    if (it != null) {
                        for (i in cartItems.indices) {
                            if (cartItems[i].productId == cart.productId) {
                                updated = true
                                cartViewModel.updateQty(cartItems[i])
                                break
                            }

                        }
                    }
                    if (!updated) {
                        cartViewModel.saveItem(cart)
                    }
                }, {
                    cartViewModel.saveItem(cart)

                })
            }
        }
    }
}