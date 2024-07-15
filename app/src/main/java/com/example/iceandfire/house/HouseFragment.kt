package com.example.iceandfire.house

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.iceandfire.databinding.FragmentHouseBinding
import kotlinx.coroutines.launch

class HouseFragment : Fragment() {

    private var _binding: FragmentHouseBinding? = null
    private val binding get() = _binding!!
    private lateinit var houseViewModel: HouseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        houseViewModel = ViewModelProvider(this)[HouseViewModel::class.java]
        _binding = FragmentHouseBinding.inflate(inflater, container, false)
        setupStateListener()

        return binding.root
    }

    private fun setupStateListener() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                houseViewModel.text.collect { text ->
                    textHouse.text = text
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}