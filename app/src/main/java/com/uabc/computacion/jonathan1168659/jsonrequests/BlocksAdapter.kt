package com.uabc.computacion.jonathan1168659.jsonrequests

import android.content.Context
import android.view.*
import androidx.lifecycle.MutableLiveData
import android.widget.*
import androidx.recyclerview.widget.*
import com.uabc.computacion.jonathan1168659.jsonrequests.databinding.ListElementBinding

class BlocksAdapter (
    var itemList: ArrayList<Block>,
    private val context: Context
) : RecyclerView.Adapter<BlocksAdapter.ViewHolder>(), Filterable
{
    val onItemClickListener = MutableLiveData<Block>()
    private val itemListFull by lazy { ArrayList(itemList) }

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
            binding.recipeIcon.visibility = if (item.hasRecipe) View.VISIBLE else View.INVISIBLE
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

    override fun getFilter() = blocksFilter

    private val blocksFilter = object : Filter()
    {
        override fun performFiltering(constraint: CharSequence?): FilterResults
        {
            val filteredList = ArrayList<Block>()

            if (constraint == null || constraint.isBlank())
            {
                filteredList.addAll(itemListFull)
            }
            else
            {
                val filterPattern = constraint.toString().lowercase().trim()

                itemListFull.forEach { block ->
                    if (block.name?.lowercase()?.contains(filterPattern) == true)
                        filteredList.add(block)
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults)
        {
            itemList.clear()
            itemList.addAll(results.values as List<Block>)
            notifyDataSetChanged()
        }
    }
}