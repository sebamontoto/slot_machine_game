package com.example.slotmachinegal.gamification

// CardFragment.kt
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentCardBinding

class CardFragment : BaseFragment<FragmentCardBinding>(FragmentCardBinding::inflate) {
    private lateinit var cardAdapter: CardAdapter
    private val scrollHandler = Handler(Looper.getMainLooper())
    private val scrollDuration = 6000L // Duración total de la animación en milisegundos

    override fun initialize() {

        // Obtener el tamaño de la pantalla
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels

        // Calcular la altura de cada tarjeta
        val cardHeight = screenHeight / 5 // Ajustar según sea necesario

        // Crea una lista de tarjetas con las URL de las imágenes
        val cardList = listOf(
                CardModel(R.drawable.gamification_card_violet),
                CardModel(R.drawable.gamification_card_green),
                CardModel(R.drawable.gamification_card_blue),
                CardModel(R.drawable.gamification_card_orange),
                CardModel(R.drawable.gamification_card_blue)
        )

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        cardAdapter = CardAdapter(cardList, cardHeight)
        binding.recyclerView.adapter = cardAdapter

        // Asegúrate de que haya suficientes tarjetas para centrar la tercera
        binding.recyclerView.scrollToPosition(200)

        binding.scrollButton.setOnClickListener {
            // Inicia un scroll animado
            startAutoScroll()
        }

    }

    private fun startAutoScroll() {
        // Detener cualquier animación anterior
        scrollHandler.removeCallbacksAndMessages(null)

        // Iniciar un scroll animado hacia abajo continuo
        val scrollInterval = 5L // Reducir el intervalo para un desplazamiento más rápido
        val totalScrollDuration = 2000L // Duración total del desplazamiento
        val initialSpeed = 50 // Velocidad inicial, ajustar según sea necesario
        val acceleration = 0.5 // Aceleración negativa para disminuir la velocidad

        val smoothScroller = object : LinearSmoothScroller(requireContext()) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START // Ajustar según tus preferencias
            }
        }

        val startTime = System.currentTimeMillis()

        scrollHandler.post(object : Runnable {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - startTime

                val currentSpeed = (initialSpeed - acceleration * elapsedTime).toInt().coerceAtLeast(0)

                // Calcular el desplazamiento en función de la velocidad actual
                val displacement = currentSpeed * scrollInterval / 1000

                // Utilizar el LinearSmoothScroller para un desplazamiento suave
                smoothScroller.targetPosition = (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1
                binding.recyclerView.layoutManager?.startSmoothScroll(smoothScroller)

                if (elapsedTime < totalScrollDuration) {
                    // Programar la siguiente iteración si no ha pasado el tiempo total
                    scrollHandler.postDelayed(this, scrollInterval)
                }
            }
        })
    }

    private fun startAutoScroll2() {
        // Detener cualquier animación anterior
        scrollHandler.removeCallbacksAndMessages(null)

        // Iniciar un scroll animado hacia arriba durante 4 segundos
        binding.recyclerView.smoothScrollBy(0, -binding.recyclerView.height)
        scrollHandler.postDelayed({
            // Detener el scroll después de 4 segundos
            binding.recyclerView.stopScroll()
        }, scrollDuration)
    }

    override fun onDestroyView() {
        // Limpiar cualquier referencia de Handler para evitar fugas de memoria
        scrollHandler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }

}
