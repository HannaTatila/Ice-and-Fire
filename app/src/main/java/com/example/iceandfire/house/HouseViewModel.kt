package com.example.iceandfire.house

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HouseViewModel : ViewModel() {

    private val _text = MutableStateFlow("This is houses Fragment")
    val text: StateFlow<String> get() = _text
}