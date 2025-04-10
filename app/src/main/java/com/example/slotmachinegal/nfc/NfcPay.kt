package com.example.slotmachinegal.nfc

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.os.HandlerCompat.postDelayed
import androidx.transition.Visibility
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.databinding.FragmentNfcPayBinding

class NfcPay : BaseFragment<FragmentNfcPayBinding>(FragmentNfcPayBinding::inflate) {

    private var loopAnimator: AnimatorSet? = null

    override fun initialize() {

        binding.containerGeneral.post {
            val card = binding.containerGeneral
            card.cameraDistance = 8000 * resources.displayMetrics.density

            // Pivot en la base
            card.pivotY = card.height.toFloat()
            card.pivotX = card.width / 2f

            startCardTiltAnimation2()
        }
    }

    private fun startCardTiltAnimation2() {
        val card = binding.containerGeneral
        val shine = binding.shineOverlay

        // Preparación de la cámara y punto de pivote
        val density = resources.displayMetrics.density
        card.cameraDistance = 8000 * density
        card.pivotY = card.height.toFloat() // base de la tarjeta
        card.pivotX = card.width / 2f

        // Reinicio de estado
        shine.alpha = 0f
        shine.translationX = 0f

        // Rotar tarjeta hacia atrás
        val tiltForward = ObjectAnimator.ofFloat(card, "rotationX", 0f, 25f).apply {
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 100
        }

        // Brillo aparece suavemente
        val fadeInShine = ObjectAnimator.ofFloat(shine, "alpha", 0f, 1f).apply {
            duration = 300
            startDelay = 300
        }



        // Desvanecer el brillo
        val fadeOutShine = ObjectAnimator.ofFloat(shine, "alpha", 1f, 0f).apply {
            duration = 400
            startDelay = 500
        }

        // Rotar de vuelta a posición original
        val tiltBack = ObjectAnimator.ofFloat(card, "rotationX", 25f, 0f).apply {
            duration = 400
            startDelay = 600
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Agrupamos animaciones
        val animationSet = AnimatorSet().apply {
            playTogether(tiltForward, fadeInShine)
            play(fadeOutShine).after(fadeInShine)
            //play(tiltBack).after(fadeOutShine)
            playTogether(tiltBack, fadeOutShine)

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Loop controlado con delay entre ciclos
                    card.postDelayed({ startCardTiltAnimation2() }, 1000)
                }
            })
        }

        animationSet.start()
    }

    private fun startCardTiltAnimation3() {
        val card = binding.cardViewGeneral
        val shine = binding.shineOverlay

        val density = resources.displayMetrics.density
        card.cameraDistance = 8000 * density
        card.pivotY = card.height.toFloat()
        card.pivotX = card.width / 2f

        // Reinicio de estado
        shine.alpha = 1f
        shine.translationX = 0f
        shine.clipBounds = Rect(0, 0, 0, shine.height)

        // Rotar hacia atrás
        val tiltForward = ObjectAnimator.ofFloat(card, "rotationX", 0f, 25f).apply {
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 100
        }

        // Animación de barrido de brillo
        val shineSweep = ValueAnimator.ofInt(0, shine.width).apply {
            duration = 400
            startDelay = 300
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                val width = animator.animatedValue as Int
                shine.clipBounds = Rect(0, 0, width, shine.height)
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    shine.clipBounds = Rect(0, 0, 0, shine.height)
                }
            })
        }

        // Rotar de vuelta a posición original
        val tiltBack = ObjectAnimator.ofFloat(card, "rotationX", 25f, 0f).apply {
            duration = 400
            startDelay = 700
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Animaciones en conjunto
        val animationSet = AnimatorSet().apply {
            playTogether(tiltForward, shineSweep)
            play(tiltBack).after(shineSweep)

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    card.postDelayed({ startCardTiltAnimation2() }, 1000)
                }
            })
        }

        // Esperar layout para que shine.width esté disponible
        shine.post {
            animationSet.start()
        }
    }



//    private fun startCardTiltAnimation() {
//        val card = binding.creditCard
//        card.cameraDistance = 8000 * resources.displayMetrics.density
//
//        // Delay inicial de 100ms
//        var startDelay = 100L
//        val holdDuration = 500L
//        val tiltDuration = 400L
//
//        val tiltForward = ObjectAnimator.ofFloat(card, "rotationX", 0f, 20f).apply {
//            duration = tiltDuration
//            interpolator = AccelerateDecelerateInterpolator()
//            startDelay = startDelay
//        }
//
//        val hold = ObjectAnimator.ofFloat(card, "rotationX", 20f, 20f).apply {
//            duration = holdDuration
//        }
//
//        val tiltBack = ObjectAnimator.ofFloat(card, "rotationX", 20f, 0f).apply {
//            duration = tiltDuration
//            interpolator = AccelerateDecelerateInterpolator()
//        }
//
//        val fullSequence = AnimatorSet().apply {
//            playSequentially(tiltForward, hold, tiltBack)
//            addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator) {
//                    start() // Loop infinito
//                }
//            })
//        }
//
//        fullSequence.start()
//    }



//    private fun start3DPerspectiveLoop() {
//        // Aumentamos la distancia de cámara para profundidad más notoria
//        binding.creditCard.cameraDistance = 8000 * resources.displayMetrics.density
//
//        val tiltBack = ObjectAnimator.ofFloat(binding.creditCard, "rotationX", 0f, 15f)
//        val glareIn = ObjectAnimator.ofFloat(binding.glareView, "alpha", 0f, 0.5f)
//
//        val forward = AnimatorSet().apply {
//            duration = 400
//            playTogether(tiltBack, glareIn)
//        }
//
//        val tiltReset = ObjectAnimator.ofFloat(binding.creditCard, "rotationX", 15f, 0f)
//        val glareOut = ObjectAnimator.ofFloat(binding.glareView, "alpha", 0.5f, 0f)
//
//        val backward = AnimatorSet().apply {
//            duration = 400
//            playTogether(tiltReset, glareOut)
//        }
//
//        val loop = AnimatorSet().apply {
//            playSequentially(forward, backward)
//            addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
//                    start() // Loop infinito
//                }
//            })
//        }
//
//        loop.start()
//    }


}