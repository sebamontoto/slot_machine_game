package com.example.slotmachinegal.test_other_solution

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Scroller
import androidx.recyclerview.widget.RecyclerView
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.ItemSlotBinding

class SlotAdapter(private val context: Context, private val imageResources: List<Int>) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

    private var recyclerView: RecyclerView? = null
    private var scrolling = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_slot, parent, false)
        return SlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val imageResource = imageResources[position % imageResources.size]
        holder.bind(imageResource)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun startScroll() {
        scrolling = true
        scroll()
    }

    fun stopScroll() {
        scrolling = false
    }

    private fun scroll() {
        if (scrolling) {
            recyclerView?.smoothScrollBy(0, 1)
            Handler().postDelayed({ scroll() }, 16) // Ajusta la velocidad según sea necesario
        }
    }

    inner class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(imageResource: Int) {
            imageView.setImageResource(imageResource)
        }
    }
}