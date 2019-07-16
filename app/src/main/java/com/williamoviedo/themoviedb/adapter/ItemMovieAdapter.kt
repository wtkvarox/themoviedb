package com.williamoviedo.themoviedb.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.williamoviedo.themoviedb.R
import com.williamoviedo.themoviedb.domain.ItemsMovie
import com.williamoviedo.themoviedb.util.Constant
import io.realm.RealmList
import kotlinx.android.synthetic.main.item_movie.view.*

class ItemMovieAdapter(private val itemsMovies: RealmList<ItemsMovie>) :
    RecyclerView.Adapter<ItemMovieAdapter.ViewHolder>() {

    var onItemClick: ((ItemsMovie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(itemsMovies[position])
    }

    override fun getItemCount(): Int {
        return itemsMovies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(itemsMovie: ItemsMovie?) {
            itemView.txt_name_item.text = itemsMovie!!.title
            itemView.txt_date_item.text = itemsMovie.releaseDate
            itemView.rating_movie.rating = (itemsMovie.voteAverage / 2)

            // Cargar imagenes con Picasso
            Picasso.get().load(Constant.BASE_URL_IMAGES + itemsMovie.backdropPath).into(itemView.img_item)

            // Guardar id para hacer consulta en el detalle
            itemView.tag = itemsMovie.id
            adapterPosition
        }

        init {
            itemView.setOnClickListener { onItemClick?.invoke(itemsMovies[adapterPosition]!!) }
        }
    }
}