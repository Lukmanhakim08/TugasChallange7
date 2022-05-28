package com.chapter7.tugaschallange7.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.ItemFilmAdapterBinding
import com.chapter7.tugaschallange7.model.DataFilmResponseItem

class AdapterFilm(private val onClik: (DataFilmResponseItem) -> Unit) : RecyclerView.Adapter<AdapterFilm.ViewHolder>() {

    private var listFilm: List<DataFilmResponseItem>? = null
    fun setDataFilm(list: List<DataFilmResponseItem>){
        this.listFilm = list
    }

    inner class ViewHolder(val binding: ItemFilmAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFilm.ViewHolder {
        val binding = ItemFilmAdapterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listFilm!![position]){
                binding.textTitle.text = "$original_title_romanised" + "$title ($original_title"
                binding.textProduser.text = producer
                binding.textTglfilm.text = release_date
                binding.textDirector.text = director
                Glide.with(binding.imgFilm.context)
                    .load(image)
                    .error(R.drawable.ic_launcher_background)
                    .override(100, 150)
                    .into(binding.imgFilm)
            }
        }
        holder.binding.adapterFilm.setOnClickListener {
            onClik(listFilm!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (listFilm.isNullOrEmpty()){
            0
        } else{
            listFilm!!.size
        }
    }
}