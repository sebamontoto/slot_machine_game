package com.example.slotmachinegal.solution_2

import android.animation.ObjectAnimator
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentSolucion2Binding


class Solucion2 : BaseFragment<FragmentSolucion2Binding>(FragmentSolucion2Binding::inflate) {

    private lateinit var cards: MutableList<ImageView>
    private val handler = Handler()
    var velocity: Long = 1000L

    override fun initialize() {
        //test1()
        //test2()
        test3()
        //test4()
    }

    //region test1
    private fun test1(){
        val slideDownAnimation: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)

        val slideUpAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)


        // Asignar la animación a la ImageView
        slideDownAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Reiniciar la animación al finalizar
                binding.card1.startAnimation(slideDownAnimation)
                binding.card2.startAnimation(slideDownAnimation)
                binding.card3.startAnimation(slideDownAnimation)
                //binding.imageViewDownThree.startAnimation(slideDownAnimation)
                binding.card4.startAnimation(slideDownAnimation)
                binding.card5.startAnimation(slideDownAnimation)
                //binding.imageViewUpThree.startAnimation(slideDownAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        // Reiniciar la animación al finalizar
        binding.card1.startAnimation(slideDownAnimation)
        binding.card2.startAnimation(slideDownAnimation)
        binding.card3.startAnimation(slideDownAnimation)
        //binding.imageViewDownThree.startAnimation(slideDownAnimation)
        binding.card4.startAnimation(slideDownAnimation)
        binding.card5.startAnimation(slideDownAnimation)
        //binding.imageViewUpThree.startAnimation(slideDownAnimation)
    }
    //endregion

    //region test2
    private fun test2() {
        val slideDownAnimation1 = createAnimation2(R.anim.slide_down_1)

        // Inicia la animación hacia abajo para la primera tarjeta
        binding.card1.startAnimation(slideDownAnimation1)
        binding.card2.startAnimation(slideDownAnimation1)
        binding.card3.startAnimation(slideDownAnimation1)
        binding.card4.startAnimation(slideDownAnimation1)
        binding.card5.startAnimation(slideDownAnimation1)
    }

    private fun createAnimation2(animationResource: Int): Animation {
        val animation = AnimationUtils.loadAnimation(requireContext(), animationResource)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // No es necesario implementar este método
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Al finalizar la animación, mueve la tarjeta nuevamente a la parte superior
                val card = animation?.fillAfter as ImageView
                card.translationY = 0f
                card.startAnimation(animation)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // No es necesario implementar este método
            }
        })

        return animation
    }
    //endregion

    //region test3
    private fun test3() {

        val slideAnimation = createAnimation()

        binding.btnPlayGamification.setOnClickListener{
            binding.card1.startAnimation(slideAnimation)
            binding.card2.startAnimation(slideAnimation)
            binding.card3.startAnimation(slideAnimation)
            binding.card4.startAnimation(slideAnimation)
            binding.card5.startAnimation(slideAnimation)
            binding.card6.startAnimation(slideAnimation)
            binding.card7.startAnimation(slideAnimation)
            binding.card8.startAnimation(slideAnimation)
            binding.card9.startAnimation(slideAnimation)

            handler.postDelayed({
                slideAnimation.cancel()
                binding.card5.setImageResource(R.drawable.new_blue_game)

                //makeAnimationBounce()

            }, 4000)
        }
    }

    private fun createAnimation(): TranslateAnimation {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val screenHeightReducida = (resources.displayMetrics.heightPixels * 0.75).toFloat()

        Log.d("Test3", "screenHeight: $screenHeight")
        val slideAnimation = TranslateAnimation(0f, 0f, 0f, screenHeightReducida)
        slideAnimation.duration = 500 // Ajusta la velocidad/duración según sea necesario
        slideAnimation.repeatCount = Animation.INFINITE
        slideAnimation.repeatMode = Animation.RESTART

        slideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // No es necesario implementar este método
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Al finalizar la animación, vuelve a colocar la tarjeta arriba
                try {
                    val card = animation?.fillAfter as ImageView
                    card.translationY = 0f
                } catch (e: Exception){

                }

            }

            override fun onAnimationRepeat(animation: Animation?) {
                // No es necesario implementar este método
            }
        })

        return slideAnimation
    }
    //endregion

    //region test4
    private fun test4() {

        val slideAnimation = createAnimation4()

        binding.btnSpin.setOnClickListener{
            binding.card1.startAnimation(slideAnimation)
            binding.card2.startAnimation(slideAnimation)
            binding.card3.startAnimation(slideAnimation)
            binding.card4.startAnimation(slideAnimation)
            binding.card5.startAnimation(slideAnimation)
            binding.card6.startAnimation(slideAnimation)
            binding.card7.startAnimation(slideAnimation)
            binding.card8.startAnimation(slideAnimation)
            binding.card9.startAnimation(slideAnimation)
        }

        handler.postDelayed({
            slideAnimation.cancel()
        }, 4000)
    }

    private fun createAnimation4(): TranslateAnimation {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val initialHeight = screenHeight * 0.75f
        val slideAnimation = TranslateAnimation(0f, 0f, 0f, initialHeight)

        // Ajusta la duración total a 4 segundos
        val durationFactor = 500 / initialHeight
        slideAnimation.duration = velocity

        velocity += 100

        slideAnimation.repeatCount = Animation.INFINITE
        slideAnimation.repeatMode = Animation.RESTART

        slideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // No es necesario implementar este método
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Al finalizar la animación, vuelve a colocar la tarjeta arriba
                try {
                    val card = animation?.fillAfter as ImageView
                    card.translationY = 0f
                } catch (e: Exception){

                }

            }

            override fun onAnimationRepeat(animation: Animation?) {
                // No es necesario implementar este método
            }
        })

        return slideAnimation
    }
    //endregion

    //region test5
    private fun test5(){
        cards = mutableListOf(binding.card1, binding.card2, binding.card3, binding.card4, binding.card5)
        // Repite este bloque para las demás tarjetas

        // Inicia la animación para cada tarjeta
        binding.card1.startAnimation(createAnimation5())
        binding.card2.startAnimation(createAnimation5())
        binding.card3.startAnimation(createAnimation5())
        binding.card4.startAnimation(createAnimation5())
        binding.card5.startAnimation(createAnimation5())
    }

    // Crea una animación de desplazamiento vertical para cada tarjeta
    private fun createAnimation5(): TranslateAnimation {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val slideAnimation = TranslateAnimation(0f, 0f, 0f, screenHeight)
        slideAnimation.duration = 5000 // Ajusta la duración según sea necesario
        slideAnimation.repeatCount = Animation.INFINITE
        slideAnimation.repeatMode = Animation.RESTART

        // Asigna el listener para el enlazamiento continuo
        slideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // No es necesario implementar este método
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Al finalizar la animación, ajusta las posiciones de las tarjetas
                val lastCard = cards.removeAt(cards.size - 1)
                cards.add(0, lastCard)
                cards.forEachIndexed { index, card ->
                    card.x = 0f
                    card.y = (index * card.height).toFloat()
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // No es necesario implementar este método
            }
        })

        return slideAnimation
    }

    //endregion

//    @SuppressLint("ResourceType")
//    private fun makeAnimation() {
//
//        val frontAnim = AnimatorInflater.loadAnimator(context, rotation_front) as AnimatorSet
//        val backAnim = AnimatorInflater.loadAnimator(context, rotate_back) as AnimatorSet
//
//        val binding = ItemGamificationCardBinding.bind()
//        val scale = requireContext().resources.displayMetrics.density;
//        binding.cardGamingFront.cameraDistance = 8000 * scale
//        binding.cardGamingBack.cameraDistance = 8000 * scale
//        binding.txtMessage.text = "Ganaste" + "$500"
//
//        binding.cardGamingBack.alpha = 1F
//        binding.imgWinnerLogo.setImageResource(R.drawable.gamification_icon_winner)
//        frontAnim.setTarget(binding.cardGamingFront)
//        frontAnim.start()
//        isfront = false
//
//        val scope = CoroutineScope(Dispatchers.Main)
//        scope.launch{
//            delay(2000)
//            //showDialog(infoGaming!!.isWinner)
//            binding.cardGamingFront.alpha = 1F
//            backAnim.setTarget(binding.cardGamingFront)
//            backAnim.start()
//        }
//    }

    private fun makeAnimationBounce(){
        //val originalY = binding.card5.y

//        // Crear un animador de posición en el eje Y
//        val bounceAnimator = ObjectAnimator.ofFloat(binding.card5, "y", originalY, originalY - 100f, originalY)
//        bounceAnimator.duration = 3000
//        bounceAnimator.start()

        val originalY = binding.card5.y

        // Configurar la posición original de la ImageView
        binding.card5.translationY = originalY

        // Crear una animación de rebote en el eje Y
        val bounceAnimation = SpringAnimation(binding.card5, DynamicAnimation.TRANSLATION_Y)
        val springForce = SpringForce()
        springForce.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        springForce.stiffness = SpringForce.STIFFNESS_LOW
        bounceAnimation.spring = springForce

        binding.card5.post {
            // Configurar el desplazamiento total de 50px (25px arriba y 25px abajo)
            val displacement = 50f
            val duration = 3000L // Duración total de la animación en milisegundos (3 segundos)

            // Configurar animación hacia arriba
            bounceAnimation.animateToFinalPosition(-displacement)

            // Configurar animación hacia abajo después de un retraso
            bounceAnimation.addEndListener { _, _, _, _ ->
                binding.card5.postDelayed({
                    bounceAnimation.animateToFinalPosition(displacement)
                }, 1000) // Agregar un retraso de 1 segundo (1000 milisegundos)
            }

            // Iniciar la animación
            bounceAnimation.start()
        }

    }

}