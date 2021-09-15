package com.mbds.anneflixproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.mbds.anneflixproject.R
import com.mbds.anneflixproject.models.Serie

class ListSeriesAdapter (
    private var series: MutableList<Serie>,
    private val onSerieClick: (serie: Serie) -> Unit
) : RecyclerView.Adapter<ListSeriesAdapter.SerieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_serie, parent, false)
        return SerieViewHolder(view)
    }

    override fun getItemCount(): Int = series.size

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        holder.bind(series[position])
    }

    fun appendSeries(series : List<Serie>) {
        this.series.addAll(series)
        notifyItemRangeInserted(
            this.series.size,
            series.size -1
        )
        notifyDataSetChanged()
    }

    inner class SerieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_serie_poster)

        //warning : if SVG w500 doesn'rt work -> so, choose "original"
        //other size : w185, w342, w780, ... (conf. https://developers.themoviedb.org/3/getting-started/images)
        fun bind(serie: Serie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500${serie.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            //we call onSerieClick when a serie is clicked
            itemView.setOnClickListener { onSerieClick.invoke(serie) }
        }
    }

}