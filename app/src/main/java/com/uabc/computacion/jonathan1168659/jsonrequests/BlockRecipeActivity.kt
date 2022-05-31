package com.uabc.computacion.jonathan1168659.jsonrequests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uabc.computacion.jonathan1168659.jsonrequests.databinding.ActivityBlockRecipeBinding

class BlockRecipeActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityBlockRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        renderView()
    }

    private fun renderView()
    {
        val blockName = intent.getStringExtra("blockName")
        val recipeImgRes = intent.getIntExtra("recipeImgRes", 0)

        binding.blockName.text = blockName
        binding.blockRecipe.setImageResource(recipeImgRes)
    }
}