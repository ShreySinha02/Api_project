package com.example.api_call

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {
    var currentImageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LoadMeme()
    }
     private fun LoadMeme(){
         progessbar.visibility=View.VISIBLE
         // Instantiate the RequestQueue
         val queue = Volley.newRequestQueue(this)
         val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.

         val jsonObjectRequest = JsonObjectRequest(
             Request.Method.GET, url,null,
             { response ->
                currentImageurl=response.getString("url")
                 Glide.with(this).load(currentImageurl).listener(object :RequestListener<Drawable>{
                     override fun onLoadFailed(
                         e: GlideException?,
                         model: Any?,
                         target: Target<Drawable>?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progessbar.visibility=View.GONE
                         return false
                     }

                     override fun onResourceReady(
                         resource: Drawable?,
                         model: Any?,
                         target: Target<Drawable>?,
                         dataSource: DataSource?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progessbar.visibility=View.GONE
                         return false
                     }
                 }).into(memeImage)

             },
             {
                 Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()

             })

// Add the request to the RequestQueue.
         queue.add(jsonObjectRequest)
     }
    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"chckout this meme $currentImageurl")
        intent.type="text/plain"
        val chooser=Intent.createChooser(intent,"Share meme...")
        startActivity(chooser)

    }
    fun nextMeme(view: View) {

            LoadMeme()
    }
}