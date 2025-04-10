package com.example.slotmachinegal.home

import androidx.navigation.fragment.findNavController
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun initialize() {
        findNavController().navigate(R.id.action_homeFragment_to_nfcPay)


        binding.btnOne.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
        }

        binding.btnTwo.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_otherSolutionFragment)
        }

        binding.btnThree.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_solucion2)
        }

        binding.btnFour.setOnClickListener{
           // startActivity(Intent(requireActivity(), FirstActivity::class.java))
            //findNavController().navigate(R.id.action_homeFragment_to_saveFiles)
            findNavController().navigate(R.id.action_homeFragment_to_nfcPay)
        }
    }


}