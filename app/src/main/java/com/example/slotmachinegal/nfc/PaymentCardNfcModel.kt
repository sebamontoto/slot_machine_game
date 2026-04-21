package com.example.slotmachinegal.nfc

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaymentCardNfcModel : Serializable {

    @SerializedName("CARD_TYPE")
    var cardType: String? = null

    @SerializedName("CARD_NUMBER")
    val cardNumber: String? = null

    @SerializedName("ENCRYPTED_CARD_DATA")
    val encryptedCardData: String? = null

    @SerializedName("CARD_EXPIRATION_DATE")
    val expDate: String? = null

    @SerializedName("PROVIDER")
    val provider: String? = null

    @SerializedName("SUBPRODUCT")
    val subProduct: String? = null

    @SerializedName("CARD_BRAND")
    val brand: String? = null

    @SerializedName("TEMP_CARD_ID")
    val tempCardId: String? = null

    @SerializedName("Origin")
    val origin: String? = null

    @SerializedName("OwnerType")
    val ownerType: String? = null

    @SerializedName("ART_TYPE")
    val artType: String? = null

    fun getLastDigits(): String? {
        return if (!cardNumber.isNullOrEmpty() && cardNumber.length >= 4) {
            cardNumber.takeLast(4)
        } else {
            null
        }
    }
}


