package com.mbds.anneflixproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.mbds.anneflixproject.R
import com.mbds.anneflixproject.models.Movie

class ListMoviesAdapter (
        private var movies: MutableList<Movie>,
        private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<ListMoviesAdapter.MovieViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
            return MovieViewHolder(view)
        }

        override fun getItemCount(): Int = movies.size

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            holder.bind(movies[position])
        }

        /* // V3.1
        fun updateMovies(movies: List<Movie>) {
            this.movies = movies
            notifyDataSetChanged()
        }*/

        // V3.2
        fun appendMovies(movies : List<Movie>) {
            this.movies.addAll(movies)
            notifyItemRangeInserted(
                this.movies.size,
                movies.size -1
            )
        }

        inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

            //warning : if SVG w500 doesn'rt work -> so, choose "original"
            //other size : w185, w342, w780, ... (conf. https://developers.themoviedb.org/3/getting-started/images)
            fun bind(movie: Movie) {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .transform(CenterCrop())
                    .into(poster)

                //we call onMovieClick when a movie is clicked
                itemView.setOnClickListener { onMovieClick.invoke(movie) }

            }
        }

}