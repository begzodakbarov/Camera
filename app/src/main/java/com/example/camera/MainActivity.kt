package com.example.camera

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.camera.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var photoUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    binding.myImageView.setOnClickListener {
        val file = createImageFile()
        photoUri = FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID,
            file
        )
        getTakeImageContent.launch(photoUri)
        }
    }
    private val getTakeImageContent =
        registerForActivityResult(ActivityResultContracts.TakeVideo()){
        binding.myImageView.setVideoURI(photoUri)
            binding.myImageView.start()
            val inputStream= contentResolver?.openInputStream(photoUri)
            val file = File(filesDir,"video.mp4")
            val fileOutPutStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutPutStream)
            inputStream?.close()
            fileOutPutStream.close()
            val absolutePath = file.absolutePath
            Toast.makeText(this, "$absolutePath", Toast.LENGTH_SHORT).show()
        }

    var currentImagePath = ""
    private fun createImageFile(): File {
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val externalFilesDir= getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        println("createImageFile ishlayapti")
        return File.createTempFile("JPEG_$format",".mp4", externalFilesDir).apply {
            currentImagePath = absolutePath
        }
    }
}