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
    private var fnClick: (MarvelChars) -> Unit,
    private var fnSave: (MarvelChars) -> Boolean

) : RecyclerView.Adapter<MarvelAdapters.MarvelViewHolder>() {
    var items: List<MarvelChars> = listOf()

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharterBinding = MarvelCharterBinding.bind(view)

        fun render(item: MarvelChars, fnClick: (MarvelChars) -> Unit,    fnSave: (MarvelChars) -> Boolean
        ) {
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
//                Snackbar.make(
//                    binding.imgMarvel,
//                    item.name,
//                    Snackbar.LENGTH_SHORT
//                ).show()
            }
            binding.imageViewLike.setOnClickListener {
                var checkInsert:Boolean=false
                checkInsert=fnSave(item)
                if(checkInsert){
                    Snackbar.make(
                        binding.imageMarvel,
                        "Se agrego a favoritos",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }else{
                    Snackbar.make(
                        binding.imageMarvel,
                        "No se puedo agregar o Ya esta agregado",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
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