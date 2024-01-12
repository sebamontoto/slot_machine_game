package com.example.slotmachinegal.files

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.slotmachinegal.R

class SecondActivity : AppCompatActivity() {

    lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Inicializa el ViewModel de la misma manera que en la primera actividad
        sharedViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[SharedViewModel::class.java]

        sharedViewModel.hasChance.observe(this){
            Log.e("Marshal", "Chance SecondActivity: $it", )

        }

        Log.e("Marshal", "onCreate: ${sharedViewModel.hasChance.value}", )
    }
}