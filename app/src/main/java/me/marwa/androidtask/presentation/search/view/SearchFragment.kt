package me.marwa.androidtask.presentation.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.marwa.androidtask.R
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.FragmentSearchBinding
import me.marwa.androidtask.presentation.search.viewmodel.SearchViewModel
import me.marwa.androidtask.utils.CheckConnection
import me.marwa.androidtask.utils.showToast

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.ProductItemAction {
    private lateinit var adapter: SearchAdapter
    private var isConnected: Boolean = false
    private val searchViewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            isConnected = CheckConnection().isNetworkAvailable(it)
        }
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        searchViewModel.productLiveData.observe(viewLifecycleOwner) {
            it?.fold({
                if (::adapter.isInitialized) {
                    adapter.setData(it)
                }
            }, {
                showToast(it.getMessage())
            })
        }

    }

    private fun initViews() {
        setupSearchRV()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (isConnected) {
                    searchViewModel.searchFor(query)
                } else {
                    showToast(getString(R.string.check_internet_connection))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! > 2) {
                    if (isConnected) {
                        searchViewModel.autoSearchFor(newText)

                    } else {
                        showToast(getString(R.string.check_internet_connection))
                    }

                }
                return true
            }
        })
    }


    private fun setupSearchRV() {
        context?.let {
            binding.rvSearchResult.layoutManager = LinearLayoutManager(it)
            adapter = SearchAdapter(this)
            binding.rvSearchResult.adapter = adapter
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItemClick(item: Product) {
        findNavController().navigate(SearchFragmentDirections.actionNavigationSearchToNavigationDetails(item))
    }
}