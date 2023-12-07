package com.example.slotmachinegal.gamification


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.CountDownTimer
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentQrPayGamificationBinding
import com.example.slotmachinegal.databinding.ItemGamificationCardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QrPayGamificationFragment : BaseFragment<FragmentQrPayGamificationBinding>(
    FragmentQrPayGamificationBinding::inflate) {

    private lateinit var fronAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet

    var prizeAmount: String? = null

    var isfront = true

    private val dataset = arrayOf(
        ItemModel("Blue", R.drawable.new_blue_game, 1),
        ItemModel("Green", R.drawable.new_green_game, 2),
        ItemModel("Orange", R.drawable.new_orange_game, 3),
        ItemModel("Violet", R.drawable.new_purple_game, 4),
        ItemModel("Violet", R.drawable.new_green_game, 5)
    )

    override fun initialize() {
        // Agrega el ItemDecoration personalizado


        setAnimation()
        setList()
        makeSlideClick()




    }
    private fun setAnimation() {
        fronAnim = AnimatorInflater.loadAnimator(context,
            R.anim.rotation_front
        ) as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(context,
            R.anim.rotate_back
        ) as AnimatorSet
           }

    @SuppressLint("ObjectAnimatorBinding", "SuspiciousIndentation")
    private fun makeSlideClick() {
        binding.gamificationButtonPlay.setOnClickListener {

            val itemsList = binding.gamificationRvMain.adapter!!.itemCount
            binding.gamificationRvMain.smoothScrollToPosition(itemsList)
            AppConfig.miConstanteGlobal = 80.0f
            startSlowdownScrolling(itemsList)
            binding.gamificationRvMain.postDelayed({
                setTiming(itemsList)
            }, 1000)
        }
    }

    private fun setTiming(newPosition: Int) {
        val totalTimeInMillis: Long = 3020
        val interval: Long = 1000// Intervalo de actualización en milisegundos

        val countDownTimer = object : CountDownTimer(totalTimeInMillis, interval) {
            override fun onTick(millisUntilFinished: Long) {
                AppConfig.miConstanteGlobal += 80.0f
                startSlowdownScrolling(newPosition)
            }

            override fun onFinish() {
                val layoutManager = binding.gamificationRvMain.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val middlePosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2 % dataset.size
                val middlePositionInDataset = (middlePosition + dataset.size) % dataset.size

                // Calcula el offset para centrar la tarjeta en la pantalla
                val offset = (binding.gamificationRvMain.height - 150) / 2 // Ajusta según sea necesario

                binding.gamificationRvMain.apply {
                    layoutManager.scrollToPositionWithOffset(middlePositionInDataset, offset)
                }

                CoroutineScope(Dispatchers.Main).launch {
                    delay(500L)
                    makeAnimation(middlePositionInDataset)
                }
            }
        }
        countDownTimer.start()
    }

    private fun makeAnimation(currentItemModel: Int) {

        val recyclerView = binding.gamificationRvMain
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val centerIndex = layoutManager.findFirstCompletelyVisibleItemPosition() +
                (layoutManager.findLastCompletelyVisibleItemPosition() - layoutManager.findFirstCompletelyVisibleItemPosition()) / 2

        val dataIndex = currentItemModel % dataset.size
        if (dataIndex >= 0 && dataIndex < dataset.size) {

            val binding  = ItemGamificationCardBinding.bind(layoutManager.findViewByPosition(centerIndex)!!)
            val scale = requireContext().resources.displayMetrics.density;
            binding.cardGamingFront.cameraDistance = 8000 * scale
            binding.cardGamingBack.cameraDistance = 8000 * scale
            binding.txtMessage.text = "Ganaste" + prizeAmount

            binding.cardGamingBack.alpha = 1F
            binding.imgWinnerLogo.setImageResource(R.drawable.gamification_icon_winner)
            fronAnim.setTarget(binding.cardGamingFront)
            fronAnim.start()
            isfront = false

            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                delay(2000)
                //showDialog(infoGaming!!.isWinner)
                binding.cardGamingFront.alpha = 1F
                backAnim.setTarget(binding.cardGamingFront)
                backAnim.start()
            }
        }
    }


    private fun startSlowdownScrolling(newPosition: Int) {
        val layoutManager = binding.gamificationRvMain.layoutManager as CenterScrollLayoutManager?
        val targetPosition: Int = newPosition
        val scroller = SlowSmoothScroller(binding.gamificationRvMain.context)
        scroller.targetPosition = targetPosition
        layoutManager!!.startSmoothScroll(scroller)
    }

    class SlowSmoothScroller(context: Context?) : LinearSmoothScroller(context) {
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return AppConfig.miConstanteGlobal / displayMetrics.densityDpi
        }
        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            val directionVector = super.computeScrollVectorForPosition(targetPosition)
            if (directionVector != null) {
                directionVector.y = -1.0f
            }
            return directionVector
        }
    }

    private fun setList() {
        dataset.shuffle()
        val customAdapter = QRGamificationAdapter(dataset, binding)
        binding.gamificationRvMain.setHasFixedSize(true)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(null)
        binding.gamificationRvMain.apply {
            adapter = customAdapter
            layoutManager =
                CenterScrollLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            (layoutManager as CenterScrollLayoutManager).scrollToPositionWithOffset(30,40)
        }


        //binding.gamificationRvMain.addItemDecoration(CenterItemDecoration(binding.text))
    }

    class CenterScrollLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean): LinearLayoutManager(context, orientation, reverseLayout) {
        val context = context
        override fun scrollToPosition(position: Int) {
            if (context !=null){
                val height = context.resources.displayMetrics.heightPixels
                val offset = height/2
                super.scrollToPositionWithOffset(position, offset)
            }
        }
    }
    class AppConfig {
        companion object {
            var miConstanteGlobal: Float = 130.0f
        }
    }
    data class ItemModel(val name: String, val imageResource: Int,val id_card:Int)

}