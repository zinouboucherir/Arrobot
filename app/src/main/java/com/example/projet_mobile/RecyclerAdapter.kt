package com.example.projet_mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ItemLayoutBinding

class RecycleAdapter(val plante: MutableList<Plante>): RecyclerView.Adapter<RecycleAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayoutBinding
            .inflate( LayoutInflater.from(parent.getContext()), parent, false )
        return VH(binding)
    }

    override fun onBindViewHolder(holder:VH , position: Int) {
        holder.binding.nom1.text=plante[position].nom1
        holder.binding.nom2.text=plante [position].nom2
    }
    override fun getItemCount(): Int =plante.size

    class VH(val binding:ItemLayoutBinding) : RecyclerView.ViewHolder( binding.root )


}