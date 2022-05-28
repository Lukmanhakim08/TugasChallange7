package com.chapter7.tugaschallange7.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.ItemFilmAdapterBinding
import com.chapter7.tugaschallange7.model.FavoriteFilm

class AdapterFavoriteFilm(private val onClick: (FavoriteFilm) -> Unit) : RecyclerView.Adapter<AdapterFavoriteFilm.ViewHolder>(){

    private var listFilmFavorite: List<FavoriteFilm>? = null
    fun setDataFilmFavorite(list: List<FavoriteFilm>){
        this.listFilmFavorite = list
    }

    inner class ViewHolder(val binding: ItemFilmAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFavoriteFilm.ViewHolder {
        val binding = ItemFilmAdapterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listFilmFavorite!![position]){
                binding.textTitle.text = "$judulRomaji" + "$judulInggris ($judulOriginal)"
                binding.textProduser.text = producer
                binding.textTglfilm.text = releaseDate
                binding.textDirector.text = director
                Glide.with(binding.imgFilm.context)
                    .load(image)
                    .error(R.drawable.ic_launcher_background)
                    .override(100, 150)
                    .into(binding.imgFilm)
            }
        }
        holder.binding.adapterFilm.setOnClickListener {
            onClick(listFilmFavorite!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (listFilmFavorite.isNullOrEmpty()){
            0
        } else{
            listFilmFavorite!!.size
        }
    }
}