package me.marwa.androidtask.presentation.products.details

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.marwa.androidtask.R
import me.marwa.androidtask.presentation.MainActivity
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.FragmentProductDetailsBinding
import me.marwa.androidtask.domain.entity.CartEntity
import me.marwa.androidtask.presentation.cart.viewmodel.CartViewModel
import me.marwa.androidtask.presentation.search.viewmodel.SearchViewModel
import me.marwa.androidtask.utils.network
import me.marwa.androidtask.utils.showToast

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var product: Product
    private lateinit var binding: FragmentProductDetailsBinding
    private val cartViewModel by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("product", Product::class.java)!!
        } else {
            arguments?.getParcelable("product")!!
        }
        initViews()
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
        if (::product.isInitialized) {
            binding.lbItemName.text = product.title
            binding.lbItemPrice.text = "${product.price} $"
            binding.lbType.text = product.category
            binding.lbDescriptionText.text = product.description
            binding.lbRatingValue.text = "${product.rating?.rate}"
            context?.let {
                Glide.with(it).asBitmap().load(product.image).into(binding.ivImage)
            }
            binding.btnAddToCart.setOnClickListener {
                val total =
                    product.price!!.times(binding.lbQuantityValue.text.toString().toInt())
                val qty = binding.lbQuantityValue.text.toString().toInt()
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
                                qty = qty, itemTotal = total
                            )
                            collectItems(cart)
                        }

                        content.await()
                    }
                }
            }
            binding.btnIncrement.setOnClickListener {
                var count = binding.lbQuantityValue.text.toString().toInt()
                binding.lbQuantityValue.text = "${++count}"
            }
            binding.btnDecrement.setOnClickListener {
                var count = binding.lbQuantityValue.text.toString().toInt()
                if (count != 0) {
                    binding.lbQuantityValue.text = "${--count}"
                }
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
                                binding.btnAddToCart.visibility = View.GONE

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

    companion object {
        private const val TAG = "ProductDetailsFragment"

        @JvmStatic
        fun newInstance() =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}