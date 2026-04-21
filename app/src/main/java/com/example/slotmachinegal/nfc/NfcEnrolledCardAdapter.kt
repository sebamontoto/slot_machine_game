package com.example.slotmachinegal.nfc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.ItemNfcEnrolledCardBinding

class NfcEnrolledCardAdapter(
    private var enrolledCardList: List<PaymentCardNfcModel>,
) : RecyclerView.Adapter<NfcEnrolledCardAdapter.ViewHolderNfcEnrolledCard>() {

    private var screenHeightPx = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNfcEnrolledCard {
        val binding =
            ItemNfcEnrolledCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderNfcEnrolledCard(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderNfcEnrolledCard, position: Int) {
        val paddingCarrousel =
            holder.itemView.resources.getDimension(R.dimen.horizontal_padding) * 2
        val displayMetrics = holder.itemView.resources.displayMetrics
        val screenWidthPx = displayMetrics.widthPixels - paddingCarrousel
        screenHeightPx = screenWidthPx * 0.58

        holder.bind(enrolledCardList[position], position, screenHeightPx)
    }

    override fun getItemCount(): Int = enrolledCardList.size

    inner class ViewHolderNfcEnrolledCard(private val binding: ItemNfcEnrolledCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PaymentCardNfcModel, position: Int, screenHeightPx: Double) {

            with(binding) {

//                val cardStyle = CardStyleProvider.getStyle(item)
//                val backgroundResource = cardStyle.backgroundColor

//                enrolledCardBackground.layoutParams.height = screenHeightPx.toInt()
//                enrolledCardIvFavorite.visibleOrGone(false)
//                enrolledCardTypeCard.visibleOrGone(false)

                // Si el background es un drawable, usamos setBackgroundResource, sino usamos setBackgroundColor
//                if (backgroundResource is Int && backgroundResource > 0) {
//                    enrolledCardBackground.setBackgroundResource(backgroundResource)
//                } else {
////                    enrolledCardBackground.setBackgroundColor(app.getColor(backgroundResource))
//                }
//
//                enrolledCardIvFavorite.visibleOrGone(position == 0)
////                enrolledCardIvBank.setImageResource(cardStyle.bankLogoRes)
////                enrolledCardIvBrand.setImageResource(cardStyle.cardBrandLogoRes)
//
//                enrolledCardTxtLastNumber.apply {
////                    setTextColor(context.getColor(cardStyle.textColor))
////                    text = resources.getString(R.string.nfc_card_last_number, item.cardNumber)
//                }
//
//                enrolledCardTypeCard.visibleOrGone(item.ownerType == "ADICIONAL")
//
//                enrolledCardTxtType.apply {
////                    setTextColor(context.getColor(cardStyle.textColor))
////                    text = context.getString(cardStyle.cardType)
//                }

            }

        }

    }


}