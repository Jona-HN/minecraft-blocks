package com.uabc.computacion.jonathan1168659.jsonrequests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.*
import com.google.gson.Gson
import com.uabc.computacion.jonathan1168659.jsonrequests.databinding.ActivityMainBinding
import org.json.JSONException

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)

        binding.newJoke.setOnClickListener {
            jsonParse()
        }
    }

    private fun jsonParse()
    {
        val objetos = ArrayList<Block>()
        val url = "https://minecraft-ids.grahamedgecombe.com/items.json"
        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                try
                {
                    for (i in 0 until response.length())
                    {
                        val objeto = response.get(i)
                        Log.i("test", "$objeto")

                    }
                }
                catch (e: Exception) {e.printStackTrace() }
            },
            { error -> error.printStackTrace()}
        )

        requestQueue?.add(request)
    }
}