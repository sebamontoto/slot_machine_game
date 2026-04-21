package com.example.slotmachinegal.nfc

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardModel(
    val title: String,
    val number: String,
    val backgroundColor: Int
) : Parcelable
