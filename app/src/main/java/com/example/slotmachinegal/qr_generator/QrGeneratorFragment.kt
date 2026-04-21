package com.example.slotmachinegal.qr_generator

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentQrGeneratorBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class QrGeneratorFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentQrGeneratorBinding

    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Lanza la Activity de carga y cierra el BottomSheet
        startActivity(Intent(requireContext(), QrTravelActivity::class.java))
        dismiss()
        return FrameLayout(requireContext()) // dummy view
    }

    override fun onStart() {
        super.onStart()

        val toolbar = dialog?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.GONE

        dialog?.window?.setDimAmount(0f)

        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val bottomSheetBehavior = BottomSheetBehavior.from(it)
        }
    }

    fun agregarPaddingBlancoAlLogo(logo: Bitmap, padding: Int): Bitmap {
        val sizeWithPadding = logo.width + padding * 2
        val output = Bitmap.createBitmap(sizeWithPadding, sizeWithPadding, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawColor(Color.WHITE) // Fondo blanco

        // Dibuja el logo centrado
        canvas.drawBitmap(logo, padding.toFloat(), padding.toFloat(), null)

        return output
    }

//    fun generarQrConLogo(base64Content: String, size: Int, logoBitmap: Bitmap): Bitmap {
//        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java).apply {
//            put(EncodeHintType.CHARACTER_SET, "UTF-8")
//            put(EncodeHintType.MARGIN, 1)
//            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H) // importante para permitir logo
//        }
//
//        val bitMatrix = MultiFormatWriter().encode(
//            base64Content,
//            BarcodeFormat.QR_CODE,
//            size,
//            size,
//            hints
//        )
//
//        val qrBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
//        for (x in 0 until size) {
//            for (y in 0 until size) {
//                qrBitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
//            }
//        }
//
//        // Escalamos el logo a un tamaño pequeño (por ejemplo, 20% del QR)
//        val overlaySize = (size * 0.2).toInt()
//        val scaledLogo = Bitmap.createScaledBitmap(logoBitmap, overlaySize, overlaySize, true)
//
//        // Dibujamos el logo centrado sobre el QR
//        val combinedBitmap = Bitmap.createBitmap(size, size, qrBitmap.config)
//        val canvas = Canvas(combinedBitmap)
//        canvas.drawBitmap(qrBitmap, 0f, 0f, null)
//        val centerX = (size - overlaySize) / 2f
//        val centerY = (size - overlaySize) / 2f
//        canvas.drawBitmap(scaledLogo, centerX, centerY, null)
//
//        return combinedBitmap
//    }



    private fun View.fadeIn(duration: Long = 200) {
        alpha = 0f
        visibility = View.VISIBLE
        animate().alpha(1f).setDuration(duration).start()
    }

    private fun View.fadeOut(duration: Long = 300) {
        animate().alpha(0f).setDuration(duration).withEndAction {
            visibility = View.GONE
        }.start()
    }


    //Funciona perfecto, pero pedi a diseño un log con padding
//    fun generarQrConLogoConPadding(
//        base64Content: String,
//        size: Int,
//        logoBitmap: Bitmap,
//        padding: Int = 16
//    ): Bitmap {
//        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java).apply {
//            put(EncodeHintType.CHARACTER_SET, "UTF-8")
//            put(EncodeHintType.MARGIN, 1)
//            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
//        }
//
//        val bitMatrix = MultiFormatWriter().encode(
//            base64Content,
//            BarcodeFormat.QR_CODE,
//            size,
//            size,
//            hints
//        )
//
//        val qrBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
//        for (x in 0 until size) {
//            for (y in 0 until size) {
//                qrBitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
//            }
//        }
//
//        // Redimensionar logo
//        val targetLogoSize = (size * 0.20).toInt()
//        val scaledLogo = Bitmap.createScaledBitmap(logoBitmap, targetLogoSize, targetLogoSize, true)
//
//        // Agregar padding blanco
//        val logoConPadding = agregarPaddingCircularBlancoAlLogo(scaledLogo, padding)
//
//        // Dibujar QR + logo con padding
//        val finalBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(finalBitmap)
//        canvas.drawBitmap(qrBitmap, 0f, 0f, null)
//
//        val logoX = (size - logoConPadding.width) / 2f
//        val logoY = (size - logoConPadding.height) / 2f
//        canvas.drawBitmap(logoConPadding, logoX, logoY, null)
//
//        return finalBitmap
//    }

    fun agregarPaddingCircularBlancoAlLogo(logo: Bitmap, padding: Int): Bitmap {
        val diameter = logo.width + padding * 2
        val output = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }

        // Dibuja el fondo blanco circular
        canvas.drawCircle(
            diameter / 2f,
            diameter / 2f,
            diameter / 2f,
            paint
        )

        // Dibuja el logo encima, centrado
        val left = padding.toFloat()
        val top = padding.toFloat()
        canvas.drawBitmap(logo, left, top, null)

        return output
    }




}