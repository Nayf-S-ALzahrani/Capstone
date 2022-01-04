package com.example.capstone.presentation.feature_New_memory.newVoiceNoteMemoryFragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class NewVoiceNoteMemoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var dirPath = ""
    var fileName = ""
    var isRecording = false
    var isPaused = false

    lateinit var amplitudes: ArrayList<Float>
}