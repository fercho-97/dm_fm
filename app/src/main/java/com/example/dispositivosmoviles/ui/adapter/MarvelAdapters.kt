package com.example.dispositivosmoviles.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.MarvelCharterBinding
import com.squareup.picasso.Picasso

class MarvelAdapters(
    private var fnClick: (MarvelChars) -> Unit

) : RecyclerView.Adapter<MarvelAdapters.MarvelViewHolder>() {
    var items: List<MarvelChars> = listOf()

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharterBinding = MarvelCharterBinding.bind(view)

        fun render(item: MarvelChars, fnClick: (MarvelChars) -> Unit) {
            binding.name.text = item.name
            binding.comic.text = item.comic
            Picasso.get().load(item.image).into(binding.imageMarvel)

            /*
            binding.imageMarvel.setOnClickListener{
                Snackbar.make(binding.imageMarvel,
                item.name,
                Snackbar.LENGTH_SHORT)

             */
            /*
                        binding.imageMarvel.setOnClickListener{
                            fnClick(item)
             */
            itemView.setOnClickListener {
                fnClick(item)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapters.MarvelViewHolder {
        //   TODO("Not yet implemented")


        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_charter, parent, false))
    }

    override fun onBindViewHolder(holder: MarvelAdapters.MarvelViewHolder, position: Int) {
        // TODO("Not yet implemented")

        holder.render(items[position], fnClick)
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