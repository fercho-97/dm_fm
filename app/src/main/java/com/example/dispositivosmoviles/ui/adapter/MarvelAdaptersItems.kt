package com.example.dispositivosmoviles.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.MarvelCharterBinding
import com.squareup.picasso.Picasso

class MarvelAdaptersItems(
    private var fnClick: (MarvelChars) -> Unit,
    private var fnSave: (MarvelChars) -> Boolean

) : RecyclerView.Adapter<MarvelAdaptersItems.MarvelViewHolder>() {
    var items: List<MarvelChars> = listOf()

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharterBinding = MarvelCharterBinding.bind(view)

        fun render(item: MarvelChars, fnClick: (MarvelChars) -> Unit,    fnSave: (MarvelChars) -> Boolean
        ) {
            binding.name.text = item.name
            binding.comic.text = item.comic
            Picasso.get().load(item.image).into(binding.imageMarvel)
            itemView.setOnClickListener {
                fnClick(item)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdaptersItems.MarvelViewHolder {
        //   TODO("Not yet implemented")


        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_item, parent, false))
    }

    override fun onBindViewHolder(holder: MarvelAdaptersItems.MarvelViewHolder, position: Int) {
        // TODO("Not yet implemented")

        holder.render(items[position], fnClick, fnSave)
    }

    override fun getItemCount(): Int = items.size
    // TODO("Not yet implemented")

    fun updateListItems(newItems: List<MarvelChars>) {

        this.items = this.items.plus(newItems)
        notifyDataSetChanged()

    }

    fun replaceListItems(newItems: List<MarvelChars>) {
        this.items = newItems
        notifyDataSetChanged()

    }
    fun replaceListAdapter(newItems: List<MarvelChars>) {
        this.items = newItems
        notifyDataSetChanged()

    }

}