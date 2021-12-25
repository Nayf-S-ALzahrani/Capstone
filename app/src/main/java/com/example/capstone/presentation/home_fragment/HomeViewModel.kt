package com.example.capstone.presentation.home_fragment

import androidx.lifecycle.ViewModel
import com.example.capstone.domain.models.Memory

class HomeViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val memories = listOf<Memory>(
        Memory(title = "test 1", type = "image"),
        Memory(title ="test 2", type = "voice_note"),
        Memory(title ="test 3", type = "video"),
        Memory(title ="test 4", type = "voice_note"),
        Memory(title ="test 5", type = "image")
    )

}