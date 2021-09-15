package com.mbds.anneflixproject.fragments

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mbds.anneflixproject.NavigationListener
import com.mbds.anneflixproject.R
import com.mbds.anneflixproject.database.AppDatabase
import com.mbds.anneflixproject.models.*
import com.mbds.anneflixproject.repository.MoviesRepository
import com.mbds.anneflixproject.repository.SeriesRepository

class DetailSerieFragment (serie : Serie): Fragment() {

    //Serie elements
    private var mSerie : Serie = serie
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    //MovieTrailers elements
    private lateinit var trailerName : TextView
    private lateinit var trailerURL : TextView

    private lateinit var favoriteButtonSerie : FloatingActionButton
    private var serieId = 0L
    private var serieBackdrop = ""
    private var seriePoster = ""
    private var serieTitle = ""
    private var serieRating = 0f
    private var serieReleaseDate = ""
    private var serieOverview = ""

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view = inflater.inflate(R.layout.serie_detail_fragment, container, false)
        //change toolbar title by title fragment
        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.detail_serie)
        }

        //serie element
        backdrop = view.findViewById(R.id.serie_backdrop)
        poster = view.findViewById(R.id.serie_poster)
        title = view.findViewById(R.id.serie_title)
        rating = view.findViewById(R.id.serie_rating)
        releaseDate = view.findViewById(R.id.serie_release_date)
        overview = view.findViewById(R.id.serie_overview)

        //movieTrailers element
        trailerName = view.findViewById(R.id.trailer_name_serie)
        trailerURL = view.findViewById(R.id.trailer_url_serie)

        favoriteButtonSerie = view.findViewById(R.id.seriefragment_fav_btn)

        serieId = mSerie.id
        title.text = mSerie.name
        rating.rating = mSerie.rating/2
        releaseDate.text = mSerie.firstAirDate
        overview.text = mSerie.overview

        //background image
        val backdropPath = mSerie.backdropPath
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w1280$backdropPath")
            .transform(CenterCrop())
            .into(backdrop)

        //poster image
        val posterPath = mSerie.posterPath
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342$posterPath")
            .transform(CenterCrop())
            .into(poster)


        /** ATTENTION --> tous les series n'ont pas forcément de key disponible
         *                      pour obtenir une bande annonce !!!
         *              --> Seul trouvé par des test random sur POSTMAN : id = 500
         *
         * http://api.themoviedb.org/3/tv/${mMovie.id}/videos?api_key=$api_key
         * --->
         *    ->
         *   @GET("tv/${mSerie.id}/videos")
        fun getTrailerSerie(
        @Query("apiKey") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
        ): Call<SerieTrailersResponse>
         *
         * https://www.youtube.com/watch?v=$url_key
         * */

        getTrailersSerie()

        val serie = getSerie(serieId)
        // Gestion modification du bouton après ajout
        if (serie == null) {
            favoriteButtonSerie.setImageResource(R.drawable.ic_favorite_empty)
        } else {
            favoriteButtonSerie.setImageResource(R.drawable.ic_favorite_nav)
        }

        //----
        favoriteButtonSerie.setOnClickListener {
            if (getSerie(serieId) == null) {
                //mSerie.isFavorite = true
                insertSerieToFavoriteSeries(mSerie)
                favoriteButtonSerie.setImageResource(R.drawable.ic_favorite_nav)
                Toast.makeText(activity, R.string.added_to_favorites_series, Toast.LENGTH_SHORT).show()
            } else {
                //mSerie.isFavorite = false
                deleteSerieToFavoriteSeries(serieId)
                favoriteButtonSerie.setImageResource(R.drawable.ic_favorite_empty)
                Toast.makeText(activity, R.string.removed_from_favorites_series, Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    //on recupere les trailers de la serie --> Test initial avec id=500
    private fun getTrailersSerie() {
        SeriesRepository.getTrailersSerie(
            onSuccess = ::onTrailersSerieFetched,
            onError = ::onError
        )
    }
    private fun onTrailersSerieFetched(serieTrailers: List<SerieTrailers>) {
        Log.d("SerieFragment", "Series: $serieTrailers")

        val url_key : String = serieTrailers[0].key
        //Url YOUTUBE du Trailer
        trailerName.text = serieTrailers[0].name + " : "
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
            "myseries.db"
        ).allowMainThreadQueries().build()
    }
    private fun getSerie(id: Long): FavoriteSeriesDatabase? {
        return db.serieDao().findById(id)
    }
    // Add a serie to FavoriteSeriesDatabase
    private fun insertSerieToFavoriteSeries(serie: Serie) {

        val movieId = serie.id
        val name = serie.name
        val overview = serie.overview
        val posterPath = serie.posterPath
        val backdropPath = serie.backdropPath
        val rating = serie.rating
        val firstAirDate = serie.firstAirDate

        val favoriteSerie = FavoriteSeriesDatabase(
            id = movieId,
            name = name,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            rating = rating,
            firstAirDate = firstAirDate
        )
        db.serieDao().insert(favoriteSerie)
    }
    // Delete a serie to FavoriteSeriesDatabase
    private fun deleteSerieToFavoriteSeries(serieId : Long) {
        db.serieDao().delete(serieId)
    }
}