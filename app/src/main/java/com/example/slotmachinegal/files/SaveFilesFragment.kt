package com.example.slotmachinegal.files

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.slotmachinegal.BaseFragment
import com.example.slotmachinegal.R
import com.example.slotmachinegal.databinding.FragmentSaveFilesBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

class SaveFilesFragment : BaseFragment<FragmentSaveFilesBinding>(FragmentSaveFilesBinding::inflate) {

    private lateinit var textDetailEncoded: String
    private lateinit var textDetail: String

    override fun initialize() {

        textDetailEncoded = getString(R.string.text_detail_encoded)
        textDetail = String(Base64.decode(textDetailEncoded, Base64.DEFAULT))

        binding.txtTermsAndCondition.text = textDetail

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnDownloadTerms.setOnClickListener {
            savePDFTyC()
//            if (hasPermission(requireContext(), PERMISSIONS)){
//                savePDFTyC()
//            } else {
//                registerPermission.launch(PERMISSIONS)
//            }
            //DownloadLocalFilesManager.downloadBase64File(requireActivity(), textDetailEncoded, NOMBRE_PDF)
        }
    }

    private fun savePDFTyC() {
               val file =
            File(
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}$NOMBRE_PDF"
            )


        FileOutputStream(file, false).apply {
            val decodedBytes = Base64.decode(textDetailEncoded, Base64.DEFAULT)
            Log.e("MArshal", "savePDFTyC: $decodedBytes", )
            write(decodedBytes)
            flush()
            close()
        }

        val uri = FileProvider.getUriForFile(
            requireContext(),
            "com.example.slotmachinegal.provider",
            file
        )

        Intent().apply {
            setDataAndType(uri, PDF)
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(this)
        }
        //startActivity(Intent.createChooser(shareIntent, "Compartir PDF"))
    }

    private fun hasPermission(context: Context, permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private val registerPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                savePDFTyC()
            } else {
                Toast.makeText(activity, "Sin permisos para almacenar", Toast.LENGTH_SHORT).show()
            }
        }

    companion object {
        var PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        private var NOMBRE_PDF = "/${"TesterJ"}${System.currentTimeMillis()}${".pdf"}"
        private const val PDF = "application/pdf"
    }

}