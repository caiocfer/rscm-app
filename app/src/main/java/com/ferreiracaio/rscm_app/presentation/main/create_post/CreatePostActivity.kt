package com.ferreiracaio.rscm_app.presentation.main.create_post

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.ferreiracaio.rscm_app.R
import com.ferreiracaio.rscm_app.constants.Constants
import com.ferreiracaio.rscm_app.databinding.ActivityCreatePostBinding
import com.ferreiracaio.rscm_app.models.CreatePostRequest
import java.util.*


class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var viewModel: CreatePostViewModel

    private lateinit var musicLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureAmplify()
        viewModel = ViewModelProvider(this).get(CreatePostViewModel::class.java)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.uploadMusicButton.setOnClickListener {
            getMusicLauncher.launch("audio/*")

        }

        binding.createPostButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val musicName = binding.musicTitleEditText.text.toString()
            Log.d("TAG", "onCreate: $musicName")
            val musicUri = viewModel.fileLiveData.value!!
            Log.d("Music", "onCreate: $musicUri")
            uploadMusic(musicUri)
            if (checkFields(title,content,musicName)){
                val bucket = Constants.BUCKET_LINK
                val bucketLink = "$bucket${musicLink}"
                val post = CreatePostRequest(title,content, musicName, bucketLink)
                viewModel.createPost(this,post)
                finish()
            }else{
                Toast.makeText(this,resources.getString(R.string.empty_fields),Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun checkFields(title: String, content:String, musicName:String):Boolean{

        if (title.isNotEmpty() && content.isNotEmpty() && musicName.isNotEmpty()){
            return true
        }
        return false
    }

    private val getMusicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        uri?.let {

            if(uri.pathSegments[1].substringAfterLast('/').startsWith("msf:")){
                val filePathColumn = arrayOf(MediaStore.Audio.Media.DATA)
                val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                Log.d("TAG", "music : $picturePath ")
            }
            val filePathColumn = arrayOf(MediaStore.Audio.Media.DISPLAY_NAME)
            val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)


            Log.d("TAG", "music path: ${cursor.getBlob(0)}")

            Log.d("TAG", "music path: $picturePath ")

            viewModel.fileLiveData.value = uri
            UploadState.musicSelected(uri)

            val fileExtension = uri.pathSegments[1].substringAfterLast('.')
            val fileLinkName = "${UUID.randomUUID()}.$fileExtension"
            musicLink = fileLinkName

            binding.uploadMusicButton.setText(resources.getString(R.string.music_selected)).toString()
        }
    }

    private fun uploadMusic(musicUri: Uri){

        val stream = contentResolver.openInputStream(musicUri)!!
        Amplify.Storage.uploadInputStream(
            musicLink,
            stream,
            {UploadState.Finished},
            {error -> Log.e("Amplify failed", "uploadMusic: $error")}
        )
    }

    private fun configureAmplify(){
        try{
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)
        }catch (error: AmplifyException){
            Log.e("CreatePostActivity", "configureAmplify: Failed $error")
        }
    }
}

sealed class UploadState{
    class musicSelected(val musicUri: Uri): UploadState()
    object Finished: UploadState()
}
