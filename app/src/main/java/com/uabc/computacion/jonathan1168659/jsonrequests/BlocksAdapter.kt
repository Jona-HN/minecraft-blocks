package com.uabc.computacion.jonathan1168659.jsonrequests

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.*
import com.uabc.computacion.jonathan1168659.jsonrequests.databinding.ListElementBinding

class BlocksAdapter (
    var itemList: List<Block>,
    private val context: Context
) : RecyclerView.Adapter<BlocksAdapter.ViewHolder>()
{
    val onItemClickListener = MutableLiveData<Block>()

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder
    {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemView.setOnClickListener { onItemClickListener.value = itemList[position] }
        holder.bindData(itemList[position], context)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val binding = ListElementBinding.bind(itemView)

        fun bindData(item: Block, context: Context)
        {
            val resId = context.resources.getIdentifier(item.getId(), "drawable", context.packageName)
            binding.blockIcon.setImageResource(resId)
            binding.blockName.text = item.name
            binding.blockId.text = context.resources.getString(R.string.block_id, item.type, item.meta)
        }

        companion object
        {
            fun create(parent: ViewGroup): ViewHolder
            {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_element, parent, false)

                return ViewHolder(view)
            }
        }
    }
}