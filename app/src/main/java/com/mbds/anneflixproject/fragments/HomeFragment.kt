package com.mbds.anneflixproject.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbds.anneflixproject.NavigationListener
import com.mbds.anneflixproject.R
import com.mbds.anneflixproject.adapter.ListMoviesAdapter
import com.mbds.anneflixproject.models.Movie
import com.mbds.anneflixproject.repository.MoviesRepository

class HomeFragment : Fragment() {

    //Inst ->
    private lateinit var popularMoviesRecyclerView : RecyclerView
    private lateinit var popularMoviesAdapter : ListMoviesAdapter
    private lateinit var popularMoviesLayoutMng : LinearLayoutManager

    private lateinit var topRatedMoviesRecyclerView : RecyclerView
    private lateinit var topRatedMoviesAdapter : ListMoviesAdapter
    private lateinit var topRatedMoviesLayoutMng : LinearLayoutManager

    private lateinit var nowPlayingMoviesRecyclerView : RecyclerView
    private lateinit var nowPlayingMoviesAdapter : ListMoviesAdapter
    private lateinit var nowPlayingMoviesLayoutMng : LinearLayoutManager

    private lateinit var upcomingMoviesRecyclerView : RecyclerView
    private lateinit var upcomingMoviesAdapter : ListMoviesAdapter
    private lateinit var upcomingMoviesLayoutMng : LinearLayoutManager

    private var popularMoviesPage = 1
    private var topRatedMoviesPage = 1
    private var nowPlayingMoviesPage = 1
    private var upcomingMoviesPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        //change toolbar title by title fragment
        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.home)
        }

        //--- Popular Movies
        popularMoviesRecyclerView = view.findViewById(R.id.popular_movies_recyclerview)
        popularMoviesLayoutMng = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )
        popularMoviesRecyclerView.layoutManager = popularMoviesLayoutMng
        //popularMoviesAdapter = ListMoviesAdapter(mutableListOf())
        popularMoviesAdapter = ListMoviesAdapter(mutableListOf()) { movie -> showMovieFragment(movie) }
        popularMoviesRecyclerView.adapter = popularMoviesAdapter

        //--- Top Rated Movies
        topRatedMoviesRecyclerView = view.findViewById(R.id.top_rated_movies_recyclerview)
        topRatedMoviesLayoutMng = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )
        topRatedMoviesRecyclerView.layoutManager = topRatedMoviesLayoutMng
        //topRatedMoviesAdapter = ListMoviesAdapter(mutableListOf())
        topRatedMoviesAdapter = ListMoviesAdapter(mutableListOf()) { movie -> showMovieFragment(movie) }
        topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter

        //--- Now Playing Movies
        nowPlayingMoviesRecyclerView = view.findViewById(R.id.now_playing_movies_recyclerview)
        nowPlayingMoviesLayoutMng = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )
        nowPlayingMoviesRecyclerView.layoutManager = nowPlayingMoviesLayoutMng
        //nowPlayingMoviesAdapter = ListMoviesAdapter(mutableListOf())
        nowPlayingMoviesAdapter = ListMoviesAdapter(mutableListOf()) { movie -> showMovieFragment(movie) }
        nowPlayingMoviesRecyclerView.adapter = nowPlayingMoviesAdapter

        //--- Upcoming Movies
        upcomingMoviesRecyclerView = view.findViewById(R.id.upcoming_movies_recyclerview)
        upcomingMoviesLayoutMng = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )
        upcomingMoviesRecyclerView.layoutManager = upcomingMoviesLayoutMng
        //upcomingMoviesAdapter = ListMoviesAdapter(mutableListOf())
        upcomingMoviesAdapter = ListMoviesAdapter(mutableListOf()) { movie -> showMovieFragment(movie) }
        upcomingMoviesRecyclerView.adapter = upcomingMoviesAdapter

        getPopularMovies()
        getTopRatedMovies()
        getNowPlayingMovies()
        getUpcomingMovies()

        return view
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    // --- Popular Movies Methodes
    private fun onPopularMoviesFetched(movies: List<Movie>) {
        //on recupere les films populaires
        popularMoviesAdapter.appendMovies(movies)
        weldPopularMoviesOnScrollListener()
    }
    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }
    //permet de récuperer et de souder au RecyclerV des films supplémentaires de cette categorie
    private fun weldPopularMoviesOnScrollListener() {
        popularMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //renvoie la position
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //nombre d'element visible à l'ecran
                val visibleItemCount = popularMoviesLayoutMng.childCount
                //position du premier element visible sur la gauche
                val firstVisibleItem = popularMoviesLayoutMng.findFirstVisibleItemPosition()
                //nombre d'element total dans le recyclerview
                val totalItemCount = popularMoviesLayoutMng.itemCount

                //Arrivee au 3/4 des items du recyclerV. -> on stoppe le listener
                //et on recupère de nouveaux films de cette categorie pour les soudé à la suite des films precedents
                if (firstVisibleItem + visibleItemCount >= totalItemCount *3/4) {
                    popularMoviesRecyclerView.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    //-- Top Rated Movies Methodes
    private fun onTopRatedMoviesFetched(movies : List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)
        weldTopRatedMoviesOnScrollListener()
    }
    private fun weldTopRatedMoviesOnScrollListener() {
        topRatedMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = topRatedMoviesLayoutMng.childCount
                val firstVisibleItem = topRatedMoviesLayoutMng.findFirstVisibleItemPosition()
                val totalItemCount = topRatedMoviesLayoutMng.itemCount

                if (firstVisibleItem + visibleItemCount >= totalItemCount* 3/4) {
                    topRatedMoviesRecyclerView.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }
    private fun getTopRatedMovies() {
        MoviesRepository.getTopRatedMovies(
            topRatedMoviesPage,
            onSuccess = ::onTopRatedMoviesFetched,
            onError = ::onError
        )
    }


    //--- Now Playing Movies Methodes
    private fun onNowPlayingMoviesFetched(movies : List<Movie>) {
        nowPlayingMoviesAdapter.appendMovies(movies)
        weldNowPlayingMoviesOnScrollListener()
    }
    private fun weldNowPlayingMoviesOnScrollListener() {
        nowPlayingMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = nowPlayingMoviesLayoutMng.childCount
                val firstVisibleItem = nowPlayingMoviesLayoutMng.findFirstVisibleItemPosition()
                val totalItemCount = nowPlayingMoviesLayoutMng.itemCount

                if (firstVisibleItem + visibleItemCount >= totalItemCount* 3/4) {
                    nowPlayingMoviesRecyclerView.removeOnScrollListener(this)
                    nowPlayingMoviesPage++
                    getNowPlayingMovies()
                }
            }
        })
    }
    private fun getNowPlayingMovies() {
        MoviesRepository.getNowPlayingMovies(
            nowPlayingMoviesPage,
            onSuccess = ::onNowPlayingMoviesFetched,
            onError = ::onError
        )
    }

    //--- Upcoming Movies Methode
    private fun onUpcomingMoviesFetched(movies : List<Movie>) {
        upcomingMoviesAdapter.appendMovies(movies)
        weldUpcomingMoviesOnScrollListener()
    }
    private fun weldUpcomingMoviesOnScrollListener() {
        upcomingMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = upcomingMoviesLayoutMng.childCount
                val firstVisibleItem = upcomingMoviesLayoutMng.findFirstVisibleItemPosition()
                val totalItemCount = upcomingMoviesLayoutMng.itemCount

                if (firstVisibleItem + visibleItemCount >= totalItemCount* 3/4) {
                    upcomingMoviesRecyclerView.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }
    private fun getUpcomingMovies() {
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            onSuccess = ::onUpcomingMoviesFetched,
            onError = ::onError
        )
    }

    //---
    private fun showMovieFragment(movie: Movie) {
        parentFragmentManager.beginTransaction().apply{
            replace(R.id.fragment_container, MovieFragment(movie))
            addToBackStack(null)
        }.commit()

    }
}