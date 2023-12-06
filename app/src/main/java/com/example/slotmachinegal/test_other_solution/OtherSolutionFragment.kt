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
            R.drawable.new_blue_game
        )


        // Configura el RecyclerView y su adaptador con la lista de imágenes
        val layoutManager = LoopingLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        adapter = SlotAdapter(requireActivity(), imageResources)

        binding.recyclerView.adapter = adapter

        // Configura el botón para iniciar el giro
        binding.btnSpin.setOnClickListener {
            // Inicia el scroll automático rápido durante 4 segundos
            startFastScroll()
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