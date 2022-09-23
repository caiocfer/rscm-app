package com.ferreiracaio.rscm_app.presentation.main.adapter

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.ferreiracaio.rscm_app.R
import com.ferreiracaio.rscm_app.databinding.PostAdapterBinding
import com.ferreiracaio.rscm_app.models.PostResponse
import kotlinx.coroutines.*
import java.io.IOException


class PostAdapter(
    private val posts: List<PostResponse>,
    private var mediaPlayer: MediaPlayer
):RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: PostAdapterBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        with(holder){
            with(posts[position]){
                binding.textAuthorUsername.text = "@${this.author_username}"
                binding.textPostTitle.text = this.title
                binding.textContent.text = this.content
                binding.textMusicTitle.text = this.musicTitle
                val musicLink = this.musicLink
                binding.musicSeekBar.progress = 0
                binding.musicSeekBar.isEnabled = false
                binding.musicSeekBar.max=0

                binding.playButton.setOnClickListener {
                    startStopAudio(
                        mediaPlayer.isPlaying,
                        binding.musicSeekBar,
                        binding.playButton,
                        musicLink)
                }

                binding.musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        if(p2){
                            mediaPlayer.seekTo(p1)
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }
                })

                CoroutineScope(Dispatchers.IO).launch {
                    while (true) {
                        while (mediaPlayer.isPlaying) {
                            Log.d("TAG", "onBindViewHolder: ${binding.musicSeekBar.progress}")
                            delay(1000)
                            binding.musicSeekBar.progress = mediaPlayer.currentPosition
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount() = posts.size

    private fun playMusic(musicLink:String, seekBar: SeekBar, button:ImageButton){

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )

        try{
            mediaPlayer.setDataSource(musicLink)
            mediaPlayer.prepareAsync()
        }catch (e: IOException){
            e.printStackTrace()
        }


        mediaPlayer.setOnPreparedListener { player ->
            player.start()
            seekBar.max = player.duration
        }


        mediaPlayer.setOnCompletionListener {
            startStopAudio(true,seekBar,button,musicLink)
        }

    }

    private fun startStopAudio(isPlaying: Boolean,seekBar: SeekBar,button:ImageButton,musicLink: String){
        if(isPlaying){
            seekBar.isEnabled = false
            mediaPlayer.pause()
            mediaPlayer.reset()
            button.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
            seekBar.progress = 0
            seekBar.max=0
        }else{
            stopAudio()
            seekBar.isEnabled = true
            playMusic(musicLink, seekBar, button)
            button.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
        }
    }

    private fun stopAudio(){
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
            mediaPlayer.reset()
            mediaPlayer.release()
        }

    }

    fun clearMediaPlayer() {
        mediaPlayer.stop()
    }

}