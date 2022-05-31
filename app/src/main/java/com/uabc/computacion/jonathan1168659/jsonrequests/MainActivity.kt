package com.uabc.computacion.jonathan1168659.jsonrequests

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.android.volley.*
import com.android.volley.toolbox.*
import com.google.gson.Gson
import com.uabc.computacion.jonathan1168659.jsonrequests.databinding.ActivityMainBinding
import org.json.JSONException

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private var requestQueue: RequestQueue? = null
    private var blocks = ArrayList<Block>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)

        val blocksAdapter = BlocksAdapter(blocks, this)
        binding.listRecyclerView.setHasFixedSize(true)
        // Linear Layout por defecto
        binding.listRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.newJoke.setOnClickListener {
            getResponse(object : VolleyCallback
            {
                override fun onSuccessResponse(blocksFetched: ArrayList<Block>)
                {
                    blocks = blocksFetched
                    blocks.forEach{ block ->
                        Log.i("test", "$block")
                    }
                    blocksAdapter.itemList = blocks
                    binding.listRecyclerView.adapter = blocksAdapter
                }
            })
        }

        setSupportActionBar(binding.toolbar)
    }

    private fun getResponse(callback: VolleyCallback)
    {
        val url = "https://minecraft-ids.grahamedgecombe.com/items.json"
        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                try
                {
                    val gson = Gson()
                    val blocks = ArrayList<Block>()

                    for (i in 0 until response.length())
                    {
                        try
                        {
                            val block = gson.fromJson(response.getJSONObject(i).toString(), Block::class.java)
                            Log.i("test", "$block")
                            blocks.add(block)
                        }
                        catch (e: JSONException)
                        {
                            Log.i("warning", "Ocurrió un error al parsear los datos")
                        }
                    }

                    callback.onSuccessResponse(blocks)
                }
                catch (e: Exception) { e.printStackTrace() }
            },
            { error -> error.printStackTrace()}
        )

        requestQueue?.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {

        val newLayout: RecyclerView.LayoutManager = when (item.itemId)
        {
            R.id.option_linearLayout -> LinearLayoutManager(this)
            R.id.option_gridLayout -> GridLayoutManager(this, 2)
            else -> LinearLayoutManager(this)
        }

        binding.listRecyclerView.layoutManager = newLayout

        return super.onOptionsItemSelected(item)
    }
}