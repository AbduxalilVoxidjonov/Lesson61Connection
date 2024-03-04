package com.example.lesson62_

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.lesson62_.databinding.ActivityMainBinding
import com.example.lesson62_.model.Models
import com.example.lesson62_.utils.NetworkHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var requestQueue: RequestQueue
    private val url = "https://storage.kun.uz/source/10/dmaLXPORTwe7bpVVyJ7y9pjPc-JKNAT6.jpg"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        requestQueue = Volley.newRequestQueue(this)

        binding.apply {
            val imageRequest = ImageRequest(
                url, { response ->
                    if (response != null) {
                        image.setImageBitmap(response)
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                { error ->
                    Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            )
            requestQueue.add(imageRequest)
            val jsonObject = JsonObjectRequest(
                Request.Method.GET, "https://jsonplaceholder.typicode.com/users/1", null,
                { response ->
                    if (response != null) {
                        Log.d("MainActivity", "onRespons: $response")
                    }
                },
                { error ->
                    if (error != null) {
                        Log.d("MainActivity", "onError: ${error.message}")
                    }
                }
            )
//            requestQueue.add(jsonObject)

            button.setOnClickListener {
                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, "https://jsonplaceholder.typicode.com/users", null,
                    { response ->
                        if (response != null) {
                            val gson = Gson()
                            val type = object : TypeToken<List<Models>>() {}.type
                            val list = gson.fromJson<List<Models>>(
                                response.toString(),
                                Array<Models>::class.java
                            )
                            Log.d("MainActivity", "onRespons: $list")

                        }
                    },
                    { error ->
                        if (error != null) {
                            Log.d("MainActivity", "onError: ${error.message}")
                        }
                    }
                )
                requestQueue.add(jsonArrayRequest)

            }


        }
    }
}