package com.example.iceandfire.character

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CharacterViewModel : ViewModel() {

    private val _text = MutableStateFlow("This is character Fragment")
    val text: StateFlow<String> get() = _text
}