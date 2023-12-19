package com.example.slotmachinegal.solution_2

import android.animation.ValueAnimator
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentSolucion2Binding


class Solucion2 : BaseFragment<FragmentSolucion2Binding>(FragmentSolucion2Binding::inflate), Animation.AnimationListener {

    private val handler = Handler()
    private lateinit var animation1: Animation
    private lateinit var animation2: Animation
    private var isFrontOfCardShowing = true

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0
    private var rate = 1.5f


    private var time = 100
    private var duration = 1000

    private lateinit var countDownTimer: CountDownTimer

    override fun initialize() {
        test3()
    }

    //region test3
    private fun test3() {

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.sound_gamification)


        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build())
            .build()

        val resourceId = resources.getIdentifier("sound_gamification", "raw", requireContext().packageName)
        soundId = soundPool.load(requireContext(), resourceId, 1) ?: 0


        binding.btnPlayGamification.setOnClickListener {

            playSoundSlotMachine(1000, 100)

            val slideAnimation = createAnimation()

            shuffleArrayImages()

            binding.card01.startAnimation(slideAnimation)
            binding.card0.startAnimation(slideAnimation)
            binding.card1.startAnimation(slideAnimation)
            binding.card2.startAnimation(slideAnimation)
            binding.card3.startAnimation(slideAnimation)
            binding.card4.startAnimation(slideAnimation)
            binding.card5.startAnimation(slideAnimation)
            binding.card6.startAnimation(slideAnimation)
            binding.card7.startAnimation(slideAnimation)
            binding.card8.startAnimation(slideAnimation)
            binding.card9.startAnimation(slideAnimation)
            binding.card10.startAnimation(slideAnimation)

            handler.postDelayed({
                slideAnimation.cancel()
                makeAnimationBounce()
                soundPool.release()
                countDownTimer.cancel()

            }, 5000)

        }
    }

    private fun playSoundSlotMachine(dur: Long, interval: Long) {
        countDownTimer = object : CountDownTimer(dur, interval) {
            override fun onTick(millisUntilFinished: Long) {
                soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
            }

            override fun onFinish() {
                countDownTimer.cancel()
                time += 430
                //duration = 1000
                Log.e("Marshal", "onFinish: $time", )
                playSoundSlotMachine(1000,time.toLong())
            }
        }
        countDownTimer.start()
    }

    private  fun makeAnimation() {
        animation1 = AnimationUtils.loadAnimation(context, R.anim.to_middle)
        animation2 = AnimationUtils.loadAnimation(context, R.anim.from_middle)
        animation2.setAnimationListener(this)
        animation1.setAnimationListener(this)
        binding.card5.startAnimation(animation1)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        soundPool.release()
    }

    private fun createAnimation(): TranslateAnimation {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val screenHeightReducida = (resources.displayMetrics.heightPixels * 0.82).toFloat()
        val screenHeightReducida2 =
            ((resources.displayMetrics.heightPixels * 0.90) - resources.displayMetrics.density * 145).toFloat()

        Log.e("Marshal", "screenHeight: $screenHeight")
        Log.e("Marshal", "screenHeightReducida: $screenHeightReducida")
        Log.e("Marshal", "screenHeightReducida2: $screenHeightReducida2")

        val slideAnimation = TranslateAnimation(0f, 0f, 0f, screenHeightReducida)
        slideAnimation.duration = (78.12).toLong() // duracion en milisegundos
        slideAnimation.repeatCount = Animation.INFINITE
        slideAnimation.repeatMode = Animation.RESTART
        slideAnimation.interpolator = LinearInterpolator()

        slideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                try {
                    val card = animation?.fillAfter as ImageView
                    card.translationY = 0f
                } catch (e: Exception){

                }
            }

            override fun onAnimationRepeat(animation: Animation?) {
                slideAnimation.duration = slideAnimation.duration * 2
            }
        })

        return slideAnimation
    }
    //endregion

    private fun makeAnimationBounce() {
        val binding = binding.layoutCards

        val originalY = binding.y

        // Crear un animador de valores para simular el rebote
        val bounceAnimator = ValueAnimator.ofFloat(-50f, 50f, -50f, 0f)
        bounceAnimator.duration = 500 // Duración total de la animación en milisegundos
        bounceAnimator.repeatCount = ValueAnimator.INFINITE // Repetir dos veces (un rebote corto)
        bounceAnimator.repeatMode = ValueAnimator.REVERSE // Invertir la animación en cada repetición

        // Actualizar la posición de la ImageView durante la animación
        bounceAnimator.addUpdateListener { animator ->
            val value = animator.animatedValue as Float
            binding.translationY = originalY + value
        }

        // Iniciar la animación cuando la vista esté lista
        binding.post {
            bounceAnimator.start()

            // Detener la animación después de un breve tiempo
            Handler(Looper.getMainLooper()).postDelayed({
                bounceAnimator.end()
                makeAnimation()
            }, 500) // Detener después de X milisegundos

        }
    }

    override fun onAnimationStart(p0: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        if (animation === animation1) {
            // check whether the front of the card is showing
            if (isFrontOfCardShowing) {
                binding.imgPrize.setImageResource(R.drawable.winner)
                binding.txtAmount.visibility= View.VISIBLE
            } else {
            }
            // stop the animation of the ImageView
            binding.card5.clearAnimation()
            binding.card5.animation = animation2

      //      binding.card5.startAnimation(animation2)
        } else {

        }
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }

    private fun shuffleArrayImages(){
        val arrayImages = listOf(R.drawable.new_blue_game, R.drawable.new_orange_game, R.drawable.new_green_game, R.drawable.new_purple_game)
        val arrayImagesRandom = arrayImages.shuffled()

        with(binding){
            card01.setImageResource(arrayImagesRandom[0])
            card0.setImageResource(arrayImagesRandom[1])
            card1.setImageResource(arrayImagesRandom[2])
            card2.setImageResource(arrayImagesRandom[3])
            card3.setImageResource(arrayImagesRandom[0])
            card4.setImageResource(arrayImagesRandom[1])
            imgPrize.setImageResource(arrayImagesRandom[2])
            card6.setImageResource(arrayImagesRandom[3])
            card7.setImageResource(arrayImagesRandom[0])
            card8.setImageResource(arrayImagesRandom[1])
            card9.setImageResource(arrayImagesRandom[2])
            card10.setImageResource(arrayImagesRandom[3])
        }
    }

}