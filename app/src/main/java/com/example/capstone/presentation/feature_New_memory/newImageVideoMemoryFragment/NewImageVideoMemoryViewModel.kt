package com.example.capstone.presentation.feature_New_memory.newImageVideoMemoryFragment

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.capstone.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewImageVideoMemoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var mediaType: String? = null
    var mediaUri: Uri? = null

    init {
        mediaType = savedStateHandle.get<String>(Constants.MEDIA_TYPE)
    }
}