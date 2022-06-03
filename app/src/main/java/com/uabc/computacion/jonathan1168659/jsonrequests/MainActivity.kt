package com.uabc.computacion.jonathan1168659.jsonrequests

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
    private lateinit var blocksAdapter: BlocksAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)

        blocksAdapter = BlocksAdapter(blocks, this)
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

        blocksAdapter.onItemClickListener.observe(this) { block ->
            showRecipe(block)
        }
    }

    private fun showRecipe(block: Block)
    {
        val recipeImgRes = resources.getIdentifier(block.recipe, "drawable", packageName)
        Log.i("test", "${block.recipe} = $recipeImgRes")

        if (recipeImgRes != 0)
        {
            val intent = Intent(this, BlockRecipeActivity::class.java)
            intent.putExtra("blockName", block.name)
            intent.putExtra("recipeImgRes", recipeImgRes)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(this, "Este bloque no tienen receta", Toast.LENGTH_LONG).show()
        }
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

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean
            {
                blocksAdapter.filter.filter(newText)
                return false
            }
        })

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