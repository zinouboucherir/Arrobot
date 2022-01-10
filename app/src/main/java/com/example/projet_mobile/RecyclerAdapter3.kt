package com.example.projet_mobile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ItemLayout3Binding
import com.example.projet_mobile.databinding.ItemLayoutBinding

class RecycleAdapter3(val context: Context, val frequences: MutableList<Frequence>): RecyclerView.Adapter<RecycleAdapter3.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayout3Binding
            .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.par.text=frequences[position].NbrFois.toString()
        holder.binding.chaque.text=frequences[position].Par.toString()
        holder.binding.moisDebut.text=frequences[position].MoisDebut.toString()
        holder.binding.moisFin.text=frequences[position].MoisFin.toString()

        holder.binding.modifier.setOnClickListener {
            val intent = Intent(context, ModifierFrequencecActivity::class.java)
            intent.putExtra("frequence",frequences[position])
            context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int =frequences.size

    class VH(val binding: ItemLayout3Binding) : RecyclerView.ViewHolder(binding.root)


}


