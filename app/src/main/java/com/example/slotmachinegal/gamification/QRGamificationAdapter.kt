package com.example.slotmachinegal.gamification

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentQrPayGamificationBinding
import com.example.slotmachinegal.databinding.ItemGamificationCardBinding

class QRGamificationAdapter(
    private val dataSet: Array<QrPayGamificationFragment.ItemModel>,
    private val _binding: FragmentQrPayGamificationBinding
) : RecyclerView.Adapter<QRGamificationAdapter.MyViewHolder>() {
    private var selectedItemPosition = -1
    var binding : ItemGamificationCardBinding? = null

    inner class MyViewHolder( val binding: ItemGamificationCardBinding) : RecyclerView.ViewHolder(binding.root) {
        private val myConstraintLayout: ConstraintLayout =
            binding.backgroundItemGamification
            val text :TextView = binding.txtCardGame
            val cardView :CardView = binding.cvItemGamification



        fun bind(holder: Int, position: QrPayGamificationFragment.ItemModel) {
            myConstraintLayout.setBackgroundResource(position.imageResource)
        }
        fun centerImageView() {
            myConstraintLayout.setBackgroundResource(R.drawable.gamification_card_green)

            // Obtener las coordenadas y dimensiones del View en el elemento actual
            val viewGameCoordinates = IntArray(2)
            myConstraintLayout.getLocationOnScreen(viewGameCoordinates)

            val viewGameWidth = myConstraintLayout.width
            val viewGameHeight = myConstraintLayout.height

            // Obtener las dimensiones del ImageView
            val violetImageViewWidth = binding.cvItemGamification.width
            val violetImageViewHeight = binding.cvItemGamification.height

            // Calcular las coordenadas X e Y para centrar el ImageView sobre el componente View
            val newX = viewGameCoordinates[0] + viewGameWidth / 2 - violetImageViewWidth / 2
            val newY = viewGameCoordinates[1] + viewGameHeight / 2 - violetImageViewHeight / 2

            // Establecer las coordenadas del ImageView
            binding.cvItemGamification.x = newX.toFloat()
            binding.cvItemGamification.y = newY.toFloat()
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
         binding = ItemGamificationCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding!!)
    }


    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val realPosition = position % dataSet.size
        val item = dataSet[realPosition]
      //  holder.centerImageView()
        holder.bind(realPosition, item)
    }

}


