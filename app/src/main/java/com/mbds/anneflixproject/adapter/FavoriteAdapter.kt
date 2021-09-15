package com.mbds.anneflixproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.mbds.anneflixproject.R
import com.mbds.anneflixproject.models.Favorite
import com.mbds.anneflixproject.models.Movie

class FavoriteAdapter (
    private var items: List<Favorite>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteHolder(view)
    }
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(items: List<Favorite>) {
        this.items = items
        notifyDataSetChanged()
    }
    /*
    fun appendItems(items : List<Favorite>) {
        this.items.addAll(items)
        notifyItemRangeInserted(
            this.items.size,
            items.size -1
        )
    }*/

    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_favorite_poster)

        fun bind(item: Favorite) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${item.posterPath}")
                .transform(CenterCrop())
                .into(poster)

        }
    }
}