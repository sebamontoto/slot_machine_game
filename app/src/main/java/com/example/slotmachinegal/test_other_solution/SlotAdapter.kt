package com.example.slotmachinegal.test_other_solution

import android.content.Context
import android.os.Handler
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Scroller
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.ItemSlotBinding

class SlotAdapter(private val context: Context, private val imageResources: List<Int>) :
    RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

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

    fun setRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    private fun scroll() {
        if (scrolling) {
            val pixelsToScroll = 10000 // Ajusta según sea necesario
            val duration = 3000L // Duración total en milisegundos

            val scroller = CustomSmoothScroller(context, duration)
            scroller.targetPosition = Int.MAX_VALUE / 2

            recyclerView?.layoutManager?.startSmoothScroll(scroller)

            Handler().postDelayed({ stopScroll() }, duration)
        }
    }

    inner class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(imageResource: Int) {
            imageView.setImageResource(imageResource)
        }
    }

    /*private class CustomSmoothScroller(context: Context, private val duration: Long) :
        LinearSmoothScroller(context) {

        override fun calculateTimeForScrolling(dx: Int): Int {
            // Ajusta el tiempo de desplazamiento
            val time = super.calculateTimeForScrolling(dx)
            return (time * (duration / 1000f)).toInt()
        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            return 0.5f
        }
    }*/

    private class CustomSmoothScroller(context: Context, private val duration: Long) :
        LinearSmoothScroller(context) {

        private val startTime = SystemClock.uptimeMillis()

        override fun calculateTimeForScrolling(dx: Int): Int {
            val elapsed = SystemClock.uptimeMillis() - startTime
            return if (elapsed >= duration) duration.toInt() else elapsed.toInt()
        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            val elapsed = SystemClock.uptimeMillis() - startTime
            val remainingTime = duration - elapsed
            return if (remainingTime <= 0) 0f else super.calculateSpeedPerPixel(displayMetrics)
        }
    }
}