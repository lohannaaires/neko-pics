package com.lohanna.nekopics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lohanna.nekopics.databinding.ItemGridBinding
import com.lohanna.nekopics.model.ResponseModel

class GridAdapter: RecyclerView.Adapter<MainViewHolder>() {
    private var cats = mutableListOf<ResponseModel>()

    fun setCatsList(cats: List<ResponseModel>) {
        this.cats = cats.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemGridBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val cat = cats[position]

        Glide.with(holder.itemView.context)
            .load(cat.url)
            .into(holder.binding.itemGrid)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Cute cat", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return cats.size
    }
}

class MainViewHolder(val binding: ItemGridBinding) : RecyclerView.ViewHolder(binding.root)