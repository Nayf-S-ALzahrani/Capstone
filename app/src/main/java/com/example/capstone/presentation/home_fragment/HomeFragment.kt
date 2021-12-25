package com.example.capstone.presentation.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewbinding.ViewBinding
import com.example.capstone.R
import com.example.capstone.common.Constants
import com.example.capstone.databinding.HomeFragmentBinding
import com.example.capstone.databinding.ImageListItemBinding
import com.example.capstone.databinding.VideoListItemBinding
import com.example.capstone.databinding.VoiceNoteListItemBinding
import com.example.capstone.domain.models.Memory


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.memoriesRv)


        binding.memoriesRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MemoriesAdapter(viewModel.memories)
        }
        return binding.root
    }

    private inner class MemoriesImageViewHolder(val binding: ImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)

        }

        var memory = Memory()
        fun bind(memory: Memory) {
            this.memory = memory
            binding.memoryTitleTv.text = memory.title
        }

        override fun onClick(v: View?) {
            when (v) {
                itemView -> {
                    val navController = findNavController()
                    val name = bundleOf("name" to memory.title)
                    navController.navigate(R.id.action_homeFragment_to_memoryDetailsFragment, name)
                }
            }
        }

    }

    private inner class MemoriesVoiceNoteViewHolder(val binding: VoiceNoteListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)

        }

        var memory = Memory()
        fun bind(memory: Memory) {
            this.memory = memory
            binding.memoryTitleTv.text = memory.title
        }

        override fun onClick(v: View?) {
            when (v) {
                itemView -> {
                    val navController = findNavController()
                    val name = bundleOf("name" to memory.title)
                    navController.navigate(R.id.action_homeFragment_to_memoryDetailsFragment, name)
                }
            }
        }

    }

    private inner class MemoriesVideoViewHolder(val binding: VideoListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)

        }

        var memory = Memory()
        fun bind(memory: Memory) {
            this.memory = memory
            binding.memoryTitleTv.text = memory.title
        }

        override fun onClick(v: View?) {
            when (v) {
                itemView -> {
                    val navController = findNavController()
                    val name = bundleOf("name" to memory.title)
                    navController.navigate(R.id.action_homeFragment_to_memoryDetailsFragment, name)
                }
            }
        }

    }
    private inner class MemoriesAdapter(val memories: List<Memory>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when(viewType){
                R.layout.image_list_item ->{
                    val binding = ImageListItemBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                    MemoriesImageViewHolder(binding)
                }
                R.layout.voice_note_list_item -> {
                    val binding = VoiceNoteListItemBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                    MemoriesVoiceNoteViewHolder(binding)
                }
                R.layout.video_list_item -> {
                    val binding = VideoListItemBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                    MemoriesVideoViewHolder(binding)
                }
                else -> throw IllegalArgumentException("unknown view type $viewType")
            }

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val memory = memories[position]
            when(holder){
                is MemoriesImageViewHolder -> {
                    holder.bind(memory)
                }
                is MemoriesVoiceNoteViewHolder -> {
                    holder.bind(memory)
                }
                is MemoriesVideoViewHolder -> {
                    holder.bind(memory)
                }
                else -> throw IllegalArgumentException("unknown holder at position: $position")
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (memories[position].type) {
                Constants.IMAGE_MEMORY -> R.layout.image_list_item
                Constants.VOICE_NOTE_MEMORY -> R.layout.voice_note_list_item
                Constants.VIDEO_MEMORY -> R.layout.video_list_item
                else -> throw IllegalArgumentException("unknown view type at position: $position")
            }
        }

        override fun getItemCount(): Int = memories.size

    }
}