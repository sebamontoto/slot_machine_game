package com.example.slotmachinegal.gamification

// CardAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.slotmachinegal.R

class CardAdapter(private val cards: List<CardModel>, private val cardHeight: Int) :
        RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    override fun getItemCount(): Int {
        // Devuelve un número lo suficientemente grande para simular una lista infinita
        return Int.MAX_VALUE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)

        // Establecer la altura de la tarjeta dinámicamente
        val layoutParams = view.layoutParams
        layoutParams.height = cardHeight
        view.layoutParams = layoutParams

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position % cards.size] // Utiliza el módulo para repetir la lista
        holder.bind(card)
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(card: CardModel) {
            Glide.with(itemView)
                    .load(card.imageUrl)
                    .into(imageView)
        }
    }
}
