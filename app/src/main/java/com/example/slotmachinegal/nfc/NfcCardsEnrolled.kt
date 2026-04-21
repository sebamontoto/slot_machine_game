package com.example.slotmachinegal.nfc

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentNfcCardsEnrolledBinding

class NfcCardsEnrolled :
    BaseFragment<FragmentNfcCardsEnrolledBinding>(FragmentNfcCardsEnrolledBinding::inflate) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun initialize() {
        val transitionName = "shared_card_transition"
        binding.cardViewGeneral.transitionName = transitionName

        val extras = FragmentNavigatorExtras(
            binding.cardViewGeneral to transitionName
        )


        binding.cardsEnrolledBtnPay.setOnClickListener {
            viewModel.setDataCard(CardModel("Master", "8888", 1))
            findNavController().navigate(
                R.id.action_nfcCardsEnrolled2_to_nfcPay,
                null,
                null,
                extras
            )
        }

    }


}