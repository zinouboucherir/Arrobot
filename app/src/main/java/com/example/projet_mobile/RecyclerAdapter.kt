package com.example.projet_mobile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ItemLayoutBinding

class RecycleAdapter(val context: Context,val plantes: MutableList<Plante>): RecyclerView.Adapter<RecycleAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayoutBinding
            .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.nom.text=plantes[position].nom1
        holder.binding.nom2.text=plantes[position].nom2
        holder.binding.imageView4.setImageURI(Uri.parse(plantes[position].image))
       if(plantes[position].nbrFrequence==0)
       {
           holder.binding.frequence.setTextColor(Color.parseColor("#FF716E"))
           holder.binding.frequence.text="* Aucune fréqunece pour cette plante !"
       }
        else
       {
           holder.binding.frequence.setTextColor(Color.parseColor("#4D82EA"))
           holder.binding.frequence.text="* il y a ${plantes[position].nbrFrequence} fréquence pour cette plante"
       }
        holder.binding.addFreq.setOnClickListener {
            val intent = Intent(context, AddFrequenceActivity::class.java)
            intent.putExtra("plante",plantes[position])
            context.startActivity(intent)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("plante",plantes[position])
            context.startActivity(intent)
        }
        if(!plantes[position].arrose)
        {
            holder.binding.cardview.setCardBackgroundColor(Color.parseColor("#FF0000"))
        }

    }
    override fun getItemCount(): Int =plantes.size

    class VH(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)


}