package com.example.projet_mobile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ItemLayout2Binding

class RecyclerAdapter2(val context: Context,val plantes: MutableList<FullInfo>): RecyclerView.Adapter<RecyclerAdapter2.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):VH {
        val binding = ItemLayout2Binding
            .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder:VH, position: Int) {
        holder.binding.nom.text=plantes[position].nom1
        holder.binding.nom2.text=plantes[position].nom2
        holder.binding.imageView4.setImageURI(Uri.parse(plantes[position].image))
        holder.binding.detail.setOnClickListener {
            val intent = Intent(context, ConsignesActivity::class.java)
            intent.putExtra("plantefullInfo",plantes[position])
            context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int =plantes.size

    class VH(val binding: ItemLayout2Binding) : RecyclerView.ViewHolder(binding.root)



}