package com.uabc.computacion.jonathan1168659.jsonrequests

import com.google.gson.annotations.SerializedName


data class Block (

    @SerializedName("type"      ) var type     : Int?    = null,
    @SerializedName("meta"      ) var meta     : Int?    = null,
    @SerializedName("name"      ) var name     : String? = null,
    @SerializedName("text_type" ) var textType : String? = null

)
{
    override fun toString(): String
    {
        return "{ $type:$meta '$name' (minecraft:$textType) }"
    }
}

