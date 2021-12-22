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
import com.example.capstone.R
import com.example.capstone.databinding.HomeFragmentBinding
import com.example.capstone.databinding.ImageListItemBinding
import com.example.capstone.domain.models.Memory


class HomeFragment : Fragment() {

    private val homeViewModel by lazy {
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
            adapter = MemoriesAdapter(homeViewModel.memories)
        }

        return binding.root
    }


    private inner class MemoriesViewHolder(val binding: ImageListItemBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
        }

        var memory = Memory()
        fun bind(memory: Memory){
            this.memory = memory
            binding.memoryTitleTv.text = memory.title
        }

        override fun onClick(v: View?) {
            when(v){
                itemView ->{
                    val navController = findNavController()
                    val name = bundleOf("name" to memory.title)
                    navController.navigate(R.id.action_homeFragment_to_memoryDetailsFragment, name)
                }
            }
        }
    }

    private inner class MemoriesAdapter(val memories: List<Memory>): RecyclerView.Adapter<MemoriesViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoriesViewHolder {
            val binding = ImageListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return MemoriesViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MemoriesViewHolder, position: Int) {
            val memory = memories[position]
            holder.bind(memory)
        }

        override fun getItemCount(): Int = memories.size

    }
}