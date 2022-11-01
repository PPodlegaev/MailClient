package com.example.mail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mail.databinding.ActivityMainBinding
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val pickFromGallery:Int = 101
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Переход на страницу настроек
        binding.PageSettings.setOnClickListener{
            val intent = Intent(this, SettingsPage::class.java)
            startActivity(intent)

        }

        //Выбор картинки
        binding.btAttachment.setOnClickListener{
            openFolder()
        }

        //Переход в почтовый клиент и закрепление сообщения и вложения
        binding.sendBtn.setOnClickListener{
            val email = binding.mailAddress.text.toString()
            val subject = binding.Subject.text.toString()
            val message = binding.Message.text.toString()

            val address = email.split(",".toRegex()).toTypedArray()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL,address)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
                putExtra(Intent.EXTRA_STREAM, uri)
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun openFolder() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("return-data", true)
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), pickFromGallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickFromGallery && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.data!!
                binding.File.text = uri.lastPathSegment
                binding.File.visibility = View.VISIBLE
            }

        }
    }

}