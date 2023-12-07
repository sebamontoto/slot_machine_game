package com.example.slotmachinegal.gamification

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CenterItemDecoration(private val centerView: View) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutManager = parent.layoutManager
        val position = parent.getChildAdapterPosition(view)

        if (layoutManager is LinearLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == RecyclerView.HORIZONTAL) {
                val centerOffset = (centerView.width - view.width) / 2
                outRect.left = centerOffset
                outRect.right = centerOffset
            } else if (orientation == RecyclerView.VERTICAL) {
                val centerOffset = (centerView.height - view.height) / 2
                outRect.top = centerOffset
                outRect.bottom = centerOffset
            }

            // Asegurar que el primer y último elemento estén centrados
            if (position == 0 || position == state.itemCount - 1) {
                val extraOffset = centerView.width / 2
                if (orientation == RecyclerView.HORIZONTAL) {
                    if (position == 0) {
                        outRect.left += extraOffset
                    } else {
                        outRect.right += extraOffset
                    }
                } else if (orientation == RecyclerView.VERTICAL) {
                    if (position == 0) {
                        outRect.top += extraOffset
                    } else {
                        outRect.bottom += extraOffset
                    }
                }
            }
        }
    }
}
