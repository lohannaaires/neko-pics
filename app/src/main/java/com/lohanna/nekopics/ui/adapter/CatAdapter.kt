package com.lohanna.nekopics.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lohanna.nekopics.databinding.GridItemBinding
import com.lohanna.nekopics.model.CatModel
import com.lohanna.nekopics.utils.loadImageFromGlide
import javax.inject.Inject

class CatAdapter @Inject constructor() : RecyclerView.Adapter<CatAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<CatModel>() {
        override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {

            return oldItem == newItem

        }

        override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat = differ.currentList[position]
        holder.binding.apply {
            item.loadImageFromGlide(cat.url)
        }

        holder.itemView.setOnClickListener {
            setCatClickListener?.let {
                it(cat)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var setCatClickListener : ((cat: CatModel)->Unit)? = null

    fun onCatClicked(listener:(CatModel)->Unit){
        setCatClickListener = listener
    }

}