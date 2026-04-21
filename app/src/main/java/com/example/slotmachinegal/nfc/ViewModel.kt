package com.example.slotmachinegal.nfc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _cardModel = MutableLiveData<CardModel>()
    val cardModel: LiveData<CardModel> = _cardModel

    fun setDataCard(card: CardModel){
        _cardModel.postValue(card)
    }
}