package com.chapter7.tugaschallange7.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.FragmentDetailFilmBinding
import com.chapter7.tugaschallange7.model.DataFilmResponseItem
import com.chapter7.tugaschallange7.model.FavoriteFilm
import com.chapter7.tugaschallange7.viewmodel.ViewModelFilmFavorite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFilmFragment : Fragment(R.layout.fragment_detail_film) {
    private var detailFilmFragmentBinding: FragmentDetailFilmBinding? = null
    private lateinit var viewModelFilmFavorite: ViewModelFilmFavorite

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailFilmBinding.bind(view)
        detailFilmFragmentBinding = binding

        getAllDetail()
    }


    private fun getAllDetail() {
        viewModelFilmFavorite = ViewModelProvider(this).get(ViewModelFilmFavorite::class.java)
        if (requireArguments().containsKey("FAVORITEFILMDATA")) {
            val detail = arguments?.getParcelable<FavoriteFilm>("FAVORITEFILMDATA")
            detailFilmFragmentBinding!!.detailDeskripsi.text = detail!!.description
            detailFilmFragmentBinding!!.detailDirector.text = detail.director
            detailFilmFragmentBinding!!.detailProducer.text = detail.producer
            detailFilmFragmentBinding!!.detailJudulAsli.text = detail.judulOriginal
            detailFilmFragmentBinding!!.detailJudulInggris.text = detail.judulInggris
            detailFilmFragmentBinding!!.detailJudulRomaji.text = detail.judulRomaji
            detailFilmFragmentBinding!!.detailRating.text = (detail.rating!!.toInt() / 10.0).toString()
            detailFilmFragmentBinding!!.detailTanggalRilis.text = detail.releaseDate
            Glide.with(detailFilmFragmentBinding!!.detailImage.context)
                .load(detail.image)
                .error(R.drawable.ic_launcher_background)
                .into(detailFilmFragmentBinding!!.detailImage)

            detailFilmFragmentBinding!!.detailAddOrRemoveFavorite.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("HAPUS FILM DARI LIST FAVORIT")
                    .setMessage("Anda yakin ingin menghapus film ini dari list favorit?")
                    .setNegativeButton("TIDAK") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("YA") { _: DialogInterface, _: Int ->
                        viewModelFilmFavorite.deleteFilmFavorite(detail.id!!)
                        Toast.makeText(
                            requireContext(),
                            "Data berhasil dihapus dari list favorit",
                            Toast.LENGTH_LONG
                        ).show()
                    }.show()
            }

            detailFilmFragmentBinding!!.detailShare.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey Check out this great film: ${detail.judulInggris}!"
                )
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share To:"))
            }


        } else if (requireArguments().containsKey("FILMDATA")) {
            val detail = arguments?.getParcelable<DataFilmResponseItem>("FILMDATA")

            detailFilmFragmentBinding!!.detailDeskripsi.text = detail!!.description
            detailFilmFragmentBinding!!.detailDirector.text = detail.director
            detailFilmFragmentBinding!!.detailProducer.text = detail.producer
            detailFilmFragmentBinding!!.detailJudulAsli.text = detail.original_title
            detailFilmFragmentBinding!!.detailJudulInggris.text = detail.title
            detailFilmFragmentBinding!!.detailJudulRomaji.text = detail.original_title_romanised
            detailFilmFragmentBinding!!.detailRating.text = (detail.rt_score.toInt() / 10.0).toString()
            detailFilmFragmentBinding!!.detailTanggalRilis.text = detail.release_date
            Glide.with(detailFilmFragmentBinding!!.detailImage.context)
                .load(detail.image)
                .error(R.drawable.ic_launcher_background)
                .into(detailFilmFragmentBinding!!.detailImage)

            detailFilmFragmentBinding!!.detailAddOrRemoveFavorite.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Tambah ke favorit")
                    .setMessage("Anda yakin ingin menambahkan film ke list favorit?")
                    .setNegativeButton("TIDAK") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("YA") { _: DialogInterface, _: Int ->
                        Toast.makeText(
                            requireContext(),
                            "Data berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModelFilmFavorite.insertNewFilmFavorite(
                            FavoriteFilm(
                                null,
                                detail.title,
                                detail.original_title,
                                detail.original_title_romanised,
                                detail.director,
                                detail.producer,
                                detail.release_date,
                                detail.rt_score,
                                detail.description,
                                detail.image,
                                detail.id
                            )
                        )
                    }.show()
            }
            detailFilmFragmentBinding!!.detailShare.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey Check out this great film: ${detail.title}!"
                )
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share To:"))
            }
        }
    }
}