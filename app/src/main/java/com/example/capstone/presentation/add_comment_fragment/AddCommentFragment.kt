package com.example.capstone.presentation.add_comment_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstone.R

class AddCommentFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[AddCommentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_comment_fragment, container, false)
    }
}