package com.example.slotmachinegal.files

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.slotmachinegal.databinding.ActivityFirstBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding
    lateinit var sharedViewModel: SharedViewModel

    private val PREF_KEY = "mi_lista_pref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[SharedViewModel::class.java]

        sharedViewModel.hasChance.observe(this){
            Log.e("Marshal", "Chance FirstActivity: $it")
            Toast.makeText(this, "Chance FirstActivity: $it", Toast.LENGTH_SHORT).show()
        }

        binding.btnGoTo.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        binding.btnSetText.setOnClickListener {
            sharedViewModel.updateHasChance("Text from Activity one")
        }
        


        val myList = listOf("Item 1", "Item 2", "Item 3")
        saveList(this, myList)


        val retrievedList = getList(this)

        Log.e("Marshal", "list: ${retrievedList[0]}", )
        Log.e("Marshal", "list: ${retrievedList[1]}", )
        Log.e("Marshal", "list: ${retrievedList[2]}", )

    }

    // Método para convertir la lista a una cadena JSON
    fun saveList(context: Context, list: List<String>) {
        val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val gson = Gson()
        val json = gson.toJson(list)

        editor.putString(PREF_KEY, json)
        editor.apply()
    }

    // Método para recuperar la lista desde la cadena JSON almacenada
    fun getList(context: Context): List<String> {
        val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val json = prefs.getString(PREF_KEY, "")

        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type

        return gson.fromJson(json, type)
    }
}