package com.example.capstone.presentation.feature_Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.capstone.R
import com.example.capstone.utils.Constants
import com.example.capstone.databinding.HomeFragmentBinding
import com.example.capstone.databinding.ImageListItemBinding
import com.example.capstone.databinding.VideoListItemBinding
import com.example.capstone.databinding.VoiceNoteListItemBinding
import com.example.capstone.domain.models.Memory
import com.littlemango.stacklayoutmanager.StackLayoutManager
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {

    private val viewModel by viewModels<HomeViewModel>()
    val state by lazy { viewModel.state.value }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        viewModel.getCurrentUser()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.memoriesRv)

        val stackLayoutManager =
            StackLayoutManager(StackLayoutManager.ScrollOrientation.RIGHT_TO_LEFT)

        observeUserState()

        binding.memoriesRv.apply {
            layoutManager = stackLayoutManager
            adapter = MemoriesAdapter(viewModel.memories)
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
//            binding.memoriesRv.smoothScrollToPosition(0)
        binding.addNewFab.setOnClickListener(this)
        binding.mapsBtn.setOnClickListener(this)
    }

    private fun observeUserState(){
        viewModel.state.observe(viewLifecycleOwner){ result ->
            when{
                result.currentUser?.uid.isNullOrEmpty() -> {
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
                result.error.isNotEmpty() -> {
                    //TODO: do something
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.addNewFab -> {
                findNavController().navigate(R.id.action_homeFragment_to_oneOrCollectionFragment)
            }
            binding.mapsBtn -> {
                viewModel.logout()
            }
        }
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
            return when (viewType) {
                R.layout.image_list_item -> {
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
            when (holder) {
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