package com.uabc.computacion.jonathan1168659.jsonrequests

import com.google.gson.annotations.SerializedName


data class Block (

    @SerializedName("type"      ) var type     : Int?    = null,
    @SerializedName("meta"      ) var meta     : Int?    = null,
    @SerializedName("name"      ) var name     : String? = null,
    @SerializedName("text_type" ) var textType : String? = null

)
{
    var hasRecipe = false
    val recipe : String by lazy {
        "craft_${
            name?.filterNot { c -> c.isWhitespace() }
                ?.replace("'", "_")
                ?.lowercase()
        }" }

    override fun toString(): String
    {
        return "{ $type:$meta '$name' (minecraft:$textType) }"
    }

    fun getId() = "_${type}_${meta}"
}

