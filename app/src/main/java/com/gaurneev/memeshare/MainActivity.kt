package com.gaurneev.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gaurneev.memeshare.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    var currentImageUrl: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        loadMeme()


        mainBinding.NextBtn.setOnClickListener { NextMeme() }
        mainBinding.ShareBtn.setOnClickListener { ShareMeme() }
    }

    private fun loadMeme(){
        progressBar.visibility = View.VISIBLE
        retrofitInstance.apiInterface.getMeme().enqueue(object : Callback<Data?> {
            override fun onResponse(
                call: Call<Data?>,
                response: retrofit2.Response<Data?>
            ) {
                currentImageUrl=response.body()?.url
                Glide.with(this@MainActivity).load(currentImageUrl).listener(object :
                    RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(mainBinding.imageView)
            }

            override fun onFailure(call: Call<Data?>, t: Throwable) {
                Log.d("neevgaur","Loading memes failure",t)
            }
        })
    }

    private fun ShareMeme(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this cool meme I got from reddit $currentImageUrl")
        val chooser= Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }

    private fun NextMeme(){
        loadMeme()
    }
}