package com.example.dispositivosmoviles.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.convenciones.Datos
import com.example.dispositivosmoviles.databinding.ListadoBinding
import com.squareup.picasso.Picasso

class DatosAdapter(private val items: List<Datos>):
    RecyclerView.Adapter<DatosAdapter.DatosViewHolder>(){



    class DatosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: ListadoBinding= ListadoBinding.bind(view)

        fun render(item: Datos) {
            Picasso.get().load(item.imagen).into(binding.imageView)

            binding.textView.text =item.texto
            binding.textView2.text=item.fecha

        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DatosAdapter.DatosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DatosViewHolder(
            inflater.inflate(
                R.layout.listado,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DatosAdapter.DatosViewHolder, position: Int) {
        holder.render(items[position])
    }

    override fun getItemCount(): Int = items.size

}
