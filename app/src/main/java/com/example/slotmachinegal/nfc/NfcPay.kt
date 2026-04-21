package com.example.slotmachinegal.nfc

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.PathInterpolator
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieDrawable
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentNfcPayBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NfcPay : BaseFragment<FragmentNfcPayBinding>(FragmentNfcPayBinding::inflate) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transition = TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
            addTransition(ChangeImageTransform())
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
        }

        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }


    override fun initialize() {

        binding.cardViewGeneral.transitionName = "shared_card_transition"


        viewModel.cardModel.observe(viewLifecycleOwner){
            binding.txtCardType.text = it.title
            binding.txtCardNumber.text = it.number
        }

        val cardnumber = "12345678901234"
        val cardnumber2 = "1234"

        Log.e("Marshal", "initialize: ${cardnumber.takeLast(4)}", )
        Log.e("Marshal", "initialize2: ${cardnumber2.takeLast(4)}", )

        binding.cardViewGeneral.post {
            val card = binding.containerGeneral
            card.cameraDistance = 8000 * resources.displayMetrics.density

            // Pivot en la base
            card.pivotY = card.height.toFloat()
            card.pivotX = card.width / 2f

            viewLifecycleOwner.lifecycleScope.launch {
//                delay(1500)
                startCardTiltAnimation4()
                setLottiePending()
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            delay(10000)
            setLottieSuccess()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            delay(12000)
            slideCardOutUp()
            //fadeOutLottie()
            binding.containerLottieText.fadeOut(400)
        }


    }


    private fun fadeOutLottie() {
        val containerLottieText = binding.containerLottieText

        val fadeOutShine = ObjectAnimator.ofFloat(containerLottieText, "alpha", 1f, 0f).apply {
            duration = 400
        }

        fadeOutShine.start()
    }

    private fun setLottieSuccess() {
        val lottieView = binding.animatedImageLector
        val text = binding.textLector

        //fadeTextChange(text, "Lectura correcta")
        //Con funcion de extension
        text.fadeText("Lectura correcta", 150)

        //text.text = "Lectura correcta"

        lottieView.setAnimation(R.raw.nfc_success_lottie)

        lottieView.repeatCount = 0
        lottieView.repeatMode = LottieDrawable.RESTART

        lottieView.visibility = View.VISIBLE
        lottieView.playAnimation()
    }

    private fun setLottieError() {
        val lottieView = binding.animatedImageLector

        lottieView.setAnimation(R.raw.nfc_error_lottie)

        lottieView.repeatCount = 0
        lottieView.repeatMode = LottieDrawable.RESTART

        lottieView.visibility = View.VISIBLE
        lottieView.playAnimation()
    }

    private fun setLottiePending() {
        val lottieView = binding.animatedImageLector

        lottieView.setAnimation(R.raw.nfc_pending_lottie)

        lottieView.repeatCount = LottieDrawable.INFINITE
        lottieView.repeatMode = LottieDrawable.RESTART

        lottieView.visibility = View.VISIBLE
        lottieView.playAnimation()
    }

    private fun startCardTiltAnimation4() {
        val tiltAngleCard = 15f // Angulo inclinación tarjeta
        val durationTilt = 1000L //duración de la inclinación eje x (milisegundos)
        val durationShine = 1000L //duración del reflejo en forma de triangulo (milisegundos)
        val delayShineOut = 1000L //duración del delay del reflejo en forma de triangulo (milisegundos)

        val card = binding.cardViewGeneral
        val shine = binding.reflectionOverlay

        val density = resources.displayMetrics.density
        card.cameraDistance = 8000 * density
        card.pivotY = card.height.toFloat()
        card.pivotX = card.width / 2f

        // Posición inicial fuera de la tarjeta
        //shine.alpha = 0f //comienza invisible
        shine.alpha = 1f //comienza visible
        shine.translationX = -card.width.toFloat()
        shine.translationY = -card.height.toFloat()

        val tiltBack = ObjectAnimator.ofFloat(card, "rotationX", 0f, tiltAngleCard).apply {
            duration = durationTilt
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 150
        }

        val tiltForward = ObjectAnimator.ofFloat(card, "rotationX", tiltAngleCard, 0f).apply {
            duration = durationTilt
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = durationTilt
        }

//        val fadeInShine = ObjectAnimator.ofFloat(shine, "alpha", 0f, 1f).apply {
//            duration = 300
//            startDelay = 300
//        }
//
//        val fadeOutShine = ObjectAnimator.ofFloat(shine, "alpha", 1f, 0f).apply {
//            duration = 400
//            startDelay = 500
//        }

        // Movimiento del reflejo de arriba a la izquierda hacia abajo a la derecha
        val moveShineIn = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(shine, "translationX", -card.width * 0.5f, card.width * -0.05f),
                ObjectAnimator.ofFloat(shine, "translationY", -card.height * 0.5f, card.height * -0.05f)
            )
            duration = durationShine
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 0 //delay cuando comienza el reflejo
        }

        // Movimiento del reflejo de arriba a la izquierda hacia abajo a la derecha
        val moveShineOut = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(shine, "translationX", card.width * -0.05f, -card.width * 0.5f),
                ObjectAnimator.ofFloat(shine, "translationY", card.height * -0.05f, -card.height * 0.5f)
            )
            duration = durationShine
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = delayShineOut //delay cuando comienza el reflejo
        }

        val animationSet = AnimatorSet().apply {
            // con FadeInShine y FadeOutShine
//            play(tiltBack)
//            playTogether(fadeInShine,moveShineIn)
//            play(tiltForward).after(moveShineIn)
//            play(fadeOutShine).after(moveShineIn)
//            playTogether(fadeOutShine,moveShineOut)

            play(tiltBack)
            play(moveShineIn)
            play(tiltForward).after(moveShineIn)
            play(moveShineOut).after(moveShineIn)


            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    card.postDelayed({ startCardTiltAnimation4() }, 1000)
                }
            })
        }

        animationSet.start()
    }

    private fun slideCardOutUp() {
        val card = binding.cardViewGeneral

        val animateUp = ObjectAnimator.ofFloat(
            card,"translationY",
            card.translationY, -card.height.toFloat() - card.y).apply {
            duration = 400
            interpolator = PathInterpolator(0.95f, 0f, 0.25f, 1f) //acelearcion segun figma (ease-in-out-symetric)
        }

        animateUp.start()
    }

    private fun fadeTextChange(textView: TextView, newText: String) {
        val fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f).apply {
            duration = 100
        }

        val fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f).apply {
            duration = 100
        }

        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                textView.text = newText
                fadeIn.start()
            }
        })

        fadeOut.start()
    }


    private fun startCardTiltAnimationReflejoFade() {
        val card = binding.containerGeneral
        val shine = binding.shineOverlay

        shine.visibility = View.VISIBLE

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
                    card.postDelayed({ startCardTiltAnimationReflejoFade() }, 1000)
                }
            })
        }

        animationSet.start()
    }


}