package com.example.slotmachinegal.test_other_solution

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentOtherSolutionBinding

class OtherSolutionFragment : BaseFragment<FragmentOtherSolutionBinding>(
    FragmentOtherSolutionBinding::inflate) {

    private lateinit var adapter: SlotAdapter

    override fun initialize() {
        // Crea una lista de recursos de imágenes
        val imageResources = listOf(
            R.drawable.new_blue_game,
            R.drawable.new_orange_game,
            R.drawable.new_green_game,
            R.drawable.new_purple_game,
            R.drawable.new_orange_game
        )


        // Configure RecyclerView and its adapter with the list of images
        val layoutManager = LoopingLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        adapter = SlotAdapter(requireContext(), imageResources)

        binding.recyclerView.adapter = adapter

        // Set the RecyclerView in the adapter
        adapter.setRecyclerView(binding.recyclerView)

        // Configure the button to start the scroll
        binding.btnSpin.setOnClickListener {
            // Start the automatic scroll
            adapter.startScroll()
        }
    }

    private fun startFastScroll() {
        val pixelsToScroll = 1000 // Ajusta según sea necesario
        binding.recyclerView.smoothScrollBy(0, pixelsToScroll)

        // Detén el scroll después de 4 segundos
        Handler().postDelayed({
            binding.recyclerView.smoothScrollBy(0, 0) // Detén el scroll
        }, 4000)
    }

}