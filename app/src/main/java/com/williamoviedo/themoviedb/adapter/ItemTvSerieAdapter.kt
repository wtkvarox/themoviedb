package com.williamoviedo.themoviedb.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.williamoviedo.themoviedb.R
import com.williamoviedo.themoviedb.domain.ItemTvSerie
import com.williamoviedo.themoviedb.util.Constant
import io.realm.RealmList
import kotlinx.android.synthetic.main.item_movie.view.*

class ItemTvSerieAdapter(private val items: RealmList<ItemTvSerie>) :
    RecyclerView.Adapter<ItemTvSerieAdapter.ViewHolder>() {

    var onItemClick: ((ItemTvSerie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ItemTvSerie?) {
            itemView.txt_name_item.text = item!!.name
            itemView.txt_date_item.text = item.firstAirDate
            itemView.rating_movie.rating = (item.voteAverage / 2)

            // Cargar imagenes con Picasso
            Picasso.get().load(Constant.BASE_URL_IMAGES + item.backdropPath).into(itemView.img_item)
        }

        init {
            itemView.setOnClickListener { onItemClick?.invoke(items[adapterPosition]!!) }
        }
    }
}