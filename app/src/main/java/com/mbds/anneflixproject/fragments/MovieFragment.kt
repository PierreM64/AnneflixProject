package com.mbds.anneflixproject.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mbds.anneflixproject.NavigationListener
import com.mbds.anneflixproject.R
import com.mbds.anneflixproject.database.AppDatabase
import com.mbds.anneflixproject.models.FavoriteMoviesDatabase
import com.mbds.anneflixproject.models.Movie
import com.mbds.anneflixproject.models.MovieTrailers
import com.mbds.anneflixproject.repository.MoviesRepository

class MovieFragment (movie : Movie) : Fragment() {

    //Movie elements
    private var mMovie : Movie = movie
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    //MovieTrailers elements
    private lateinit var trailerName : TextView
    private lateinit var trailerURL : TextView

    private lateinit var favoriteButtonMovie : FloatingActionButton
    private var movieId = 0L
    private var movieBackdrop = ""
    private var moviePoster = ""
    private var movieTitle = ""
    private var movieRating = 0f
    private var movieReleaseDate = ""
    private var movieOverview = ""

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view = inflater.inflate(R.layout.movie_fragment, container, false)
        //change toolbar title by title fragment
        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.detail_movie)
        }

        //movie element
        backdrop = view.findViewById(R.id.movie_backdrop)
        poster = view.findViewById(R.id.movie_poster)
        title = view.findViewById(R.id.movie_title)
        rating = view.findViewById(R.id.movie_rating)
        releaseDate = view.findViewById(R.id.movie_release_date)
        overview = view.findViewById(R.id.movie_overview)

        //movieTrailers element
        trailerName = view.findViewById(R.id.trailer_name)
        trailerURL = view.findViewById(R.id.trailer_url)

        favoriteButtonMovie = view.findViewById(R.id.moviefragment_fav_btn)

        //---
        movieId = mMovie.id
        title.text = mMovie.title
        rating.rating = mMovie.rating/2
        releaseDate.text = mMovie.releaseDate
        overview.text = mMovie.overview

        //background image
        val backdropPath = mMovie.backdropPath
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w1280$backdropPath")
            .transform(CenterCrop())
            .into(backdrop)

        //poster image
        val posterPath = mMovie.posterPath
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342$posterPath")
            .transform(CenterCrop())
            .into(poster)



        /** http://api.themoviedb.org/3/movie/${mMovie.id}/videos?api_key=$api_key
         * --->
         *    ->
         *   @GET("movie/${mMovie.id}/videos")
                fun getTrailerMovie(
                    @Query("apiKey") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
                ): Call<MovieTrailersResponse>
         *
         * https://www.youtube.com/watch?v=$url_key
         * */

        getTrailersMovie()

        val movie = getMovie(movieId)
        // Gestion modification du bouton après ajout
        if (movie == null) {
            favoriteButtonMovie.setImageResource(R.drawable.ic_favorite_empty)
        } else {
            favoriteButtonMovie.setImageResource(R.drawable.ic_favorite_nav)
        }

        //----
        favoriteButtonMovie.setOnClickListener {
            if (getMovie(movieId) == null) {
                //mMovie.isFavorite = true
                insertMovieToFavoriteMovies(mMovie)
                favoriteButtonMovie.setImageResource(R.drawable.ic_favorite_nav)
                Toast.makeText(activity, R.string.added_to_favorites_movies, Toast.LENGTH_SHORT).show()
            } else {
                //mMovie.isFavorite = false
                deleteMovieToFavoriteMovies(movieId)
                favoriteButtonMovie.setImageResource(R.drawable.ic_favorite_empty)
                Toast.makeText(activity, R.string.removed_from_favorites_movies, Toast.LENGTH_SHORT).show()
            }
        }

        /*
        favoriteButtonMovie.setOnClickListener {
            if (!mMovie.isFavorite) {
                mMovie.isFavorite = true
                insertMovieToFavoriteMovies(mMovie)
                favoriteButtonMovie.setImageResource(R.drawable.ic_favorite_nav)
                Toast.makeText(activity, R.string.added_to_favorites, Toast.LENGTH_SHORT).show()
            } else {
                mMovie.isFavorite = false
                deleteMovieToFavoriteMovies(mMovie.id)
                favoriteButtonMovie.setImageResource(R.drawable.ic_favorite_empty)
                Toast.makeText(activity, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show()
            }
        }*/

        return view
    }

    //on recupere les trailers du films --> Test initial avec id=120
    private fun getTrailersMovie() {
        MoviesRepository.getTrailersMovie(
            onSuccess = ::onTrailersMovieFetched,
            onError = ::onError
        )
    }
    private fun onTrailersMovieFetched(movieTrailers: List<MovieTrailers>) {
        Log.d("MovieFragment", "Movies: $movieTrailers")

        val url_key : String = movieTrailers[0].key
        //Url YOUTUBE du Trailer
        trailerName.text = movieTrailers[0].name + " : "
        trailerURL.text = "https://www.youtube.com/watch?v=$url_key"
    }
    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

            /**  FAVORITE methodes **/

    //Database
    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "mymovies.db"
        ).allowMainThreadQueries().build()
    }
    private fun getMovie(id: Long): FavoriteMoviesDatabase? {
        return db.movieDao().findById(id)
    }
    // Add a movie to FavoriteMoviesDatabase
    private fun insertMovieToFavoriteMovies(movie: Movie) {

        val movieId = movie.id
        val title = movie.title
        val overview = movie.overview
        val posterPath = movie.posterPath
        val backdropPath = movie.backdropPath
        val rating = movie.rating
        val releaseDate = movie.releaseDate

        val favoriteMovie = FavoriteMoviesDatabase(
            id = movieId,
            title = title,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            rating = rating,
            releaseDate = releaseDate
        )
        db.movieDao().insert(favoriteMovie)
    }
    // Delete a movie to FavoriteMoviesDatabase
    private fun deleteMovieToFavoriteMovies(movieId : Long) {
        db.movieDao().delete(movieId)
    }

}