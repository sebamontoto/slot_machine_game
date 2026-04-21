package com.example.slotmachinegal.nfc

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

fun TextView.fadeText(newText: String, duration: Long = 200) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            text = newText
            animate().alpha(1f).setDuration(duration).start()
        }.start()
}

fun View.fadeOut(duration: Long = 400) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .start()
}

fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
