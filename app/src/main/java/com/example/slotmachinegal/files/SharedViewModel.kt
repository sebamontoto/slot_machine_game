package com.example.slotmachinegal.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    // Define los datos que deseas compartir entre las actividades
    // Puedes tener variables LiveData aquí para notificar cambios a las actividades


    private val _hasChance = MutableLiveData<String>()
    val hasChance: LiveData<String> = _hasChance

    fun updateHasChance(text: String){
        _hasChance.postValue(text)
    }
}