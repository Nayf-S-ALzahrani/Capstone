package com.example.capstone.homeFragment

import androidx.lifecycle.ViewModel
import com.example.capstone.models.Memory

class HomeViewModel : ViewModel() {

    val memories = listOf<Memory>(
        Memory(title = "test 1"),
        Memory(title ="test 2"),
        Memory(title ="test 3"),
        Memory(title ="test 4"),
        Memory(title ="test 5")
    )

}