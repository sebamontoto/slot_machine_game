package com.example.slotmachinegal.test_other_solution

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class LoopingLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val childCount = childCount

            for (i in 0 until childCount) {
                val child = getChildAt(i)!!

                val lp = child.layoutParams as RecyclerView.LayoutParams
                val left = getDecoratedLeft(child) - lp.leftMargin
                val right = getDecoratedRight(child) + lp.rightMargin

                if (right < 0) {
                    offsetChildrenHorizontal(width)
                } else if (left > width - paddingRight) {
                    offsetChildrenHorizontal(-width)
                }
            }
            scrolled
        } else {
            super.scrollHorizontallyBy(dx, recycler, state)
        }
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val childCount = childCount

            for (i in 0 until childCount) {
                val child = getChildAt(i)!!

                val lp = child.layoutParams as RecyclerView.LayoutParams
                val top = getDecoratedTop(child) - lp.topMargin
                val bottom = getDecoratedBottom(child) + lp.bottomMargin

                if (bottom < 0) {
                    offsetChildrenVertical(height)
                } else if (top > height - paddingBottom) {
                    offsetChildrenVertical(-height)
                }
            }
            scrolled
        } else {
            super.scrollVerticallyBy(dy, recycler, state)
        }
    }
}