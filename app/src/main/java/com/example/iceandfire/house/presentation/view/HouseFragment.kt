package com.example.iceandfire.house.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.iceandfire.databinding.FragmentHouseBinding
import com.example.iceandfire.house.presentation.adapter.HousePagingAdapter
import com.example.iceandfire.house.presentation.viewmodel.HouseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val INITIAL_POSITION_RECYCLERVIEW = 0

class HouseFragment : Fragment() {

    private val houseViewModel: HouseViewModel by viewModel()
    private var _binding: FragmentHouseBinding? = null
    private val binding get() = _binding!!
    private val adapter: HousePagingAdapter by lazy {
        HousePagingAdapter { url -> houseViewModel.onHouseItemClicked(url) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHouseBinding.inflate(inflater, container, false)
        setupHouseRecyclerView()
        setupBackToTopButton()
        setupAdapter()
        setupHouseListener()

        return binding.root
    }

    private fun setupHouseRecyclerView() = with(binding) {
        recyclerViewHouse.adapter = adapter
    }

    private fun setupBackToTopButton() = with(binding) {
        fabBackToTopHouse.setOnClickListener {
            recyclerViewHouse.smoothScrollToPosition(INITIAL_POSITION_RECYCLERVIEW)
        }
    }

    private fun setupHouseListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                houseViewModel.getHouses().collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> controlVisibility(true)
                else -> controlVisibility(false)
            }
        }
    }

    private fun controlVisibility(isLoading: Boolean) = with(binding) {
        loadingHouse.isVisible = isLoading
        contentHouse.isVisible = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}