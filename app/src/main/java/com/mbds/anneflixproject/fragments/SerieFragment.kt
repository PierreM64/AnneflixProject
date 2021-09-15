package com.mbds.anneflixproject.fragments

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
import com.mbds.anneflixproject.adapter.ListSeriesAdapter
import com.mbds.anneflixproject.models.Serie
import com.mbds.anneflixproject.repository.SeriesRepository

class SerieFragment : Fragment() {

    //Inst ->
    private lateinit var popularSeriesRecyclerView : RecyclerView
    private lateinit var popularSeriesAdapter : ListSeriesAdapter
    private lateinit var popularSeriesLayoutMng : LinearLayoutManager

    private lateinit var topRatedSeriesRecyclerView : RecyclerView
    private lateinit var topRatedSeriesAdapter : ListSeriesAdapter
    private lateinit var topRatedSeriesLayoutMng : LinearLayoutManager

    private lateinit var onAirSeriesRecyclerView : RecyclerView
    private lateinit var onAirSeriesAdapter : ListSeriesAdapter
    private lateinit var onAirSeriesLayoutMng : LinearLayoutManager

    private var popularSeriesPage = 1
    private var topRatedSeriesPage = 1
    private var onAirSeriesPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.serie_menu_fragment, container, false)
        //change toolbar title by title fragment
        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.series)
        }

        //--- Popular Series
        popularSeriesRecyclerView = view.findViewById(R.id.popular_series_recyclerview)
        popularSeriesLayoutMng = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )
        popularSeriesRecyclerView.layoutManager = popularSeriesLayoutMng
        //popularSeriesAdapter = ListSeriesAdapter(mutableListOf())
        popularSeriesAdapter = ListSeriesAdapter(mutableListOf()) { serie -> showSerieFragment(serie) }
        popularSeriesRecyclerView.adapter = popularSeriesAdapter

        //--- Top Rated Series
        topRatedSeriesRecyclerView = view.findViewById(R.id.top_rated_series_recyclerview)
        topRatedSeriesLayoutMng = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )
        topRatedSeriesRecyclerView.layoutManager = topRatedSeriesLayoutMng
        //topRatedSeriesAdapter = ListSeriesAdapter(mutableListOf())
        topRatedSeriesAdapter = ListSeriesAdapter(mutableListOf()) { serie -> showSerieFragment(serie) }
        topRatedSeriesRecyclerView.adapter = topRatedSeriesAdapter

        //--- On The Air Series
        onAirSeriesRecyclerView = view.findViewById(R.id.on_air_series_recyclerview)
        onAirSeriesLayoutMng = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )
        onAirSeriesRecyclerView.layoutManager = onAirSeriesLayoutMng
        //onAirSeriesAdapter = ListSeriesAdapter(mutableListOf())
        onAirSeriesAdapter = ListSeriesAdapter(mutableListOf()) { serie -> showSerieFragment(serie) }
        onAirSeriesRecyclerView.adapter = onAirSeriesAdapter

        getPopularSeries()
        getTopRatedSeries()
        getOnAirSeries()

        return view
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    // --- Popular Series Methodes
    private fun onPopularSeriesFetched(series: List<Serie>) {
        //on recupere les series populaires
        popularSeriesAdapter.appendSeries(series)
        weldPopularSeriesOnScrollListener()
    }
    private fun getPopularSeries() {
        SeriesRepository.getPopularSeries(
            popularSeriesPage,
            onSuccess = ::onPopularSeriesFetched,
            onError = ::onError
        )
    }
    //permet de récuperer et de souder au RecyclerV des series supplémentaires de cette categorie
    private fun weldPopularSeriesOnScrollListener() {
        popularSeriesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //renvoie la position
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //nombre d'element visible à l'ecran
                val visibleItemCount = popularSeriesLayoutMng.childCount
                //position du premier element visible sur la gauche
                val firstVisibleItem = popularSeriesLayoutMng.findFirstVisibleItemPosition()
                //nombre d'element total dans le recyclerview
                val totalItemCount = popularSeriesLayoutMng.itemCount

                //Arrivee au 3/4 des items du recyclerV. -> on stoppe le listener
                //et on recupère de nouvelles series de cette categorie pour les soudé à la suite des films precedents
                if (firstVisibleItem + visibleItemCount >= totalItemCount *3/4) {
                    popularSeriesRecyclerView.removeOnScrollListener(this)
                    popularSeriesPage++
                    getPopularSeries()
                }
            }
        })
    }

    //-- Top Rated Series Methodes
    private fun onTopRatedSeriesFetched(series : List<Serie>) {
        topRatedSeriesAdapter.appendSeries(series)
        weldTopRatedSeriesOnScrollListener()
    }
    private fun weldTopRatedSeriesOnScrollListener() {
        topRatedSeriesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = topRatedSeriesLayoutMng.childCount
                val firstVisibleItem = topRatedSeriesLayoutMng.findFirstVisibleItemPosition()
                val totalItemCount = topRatedSeriesLayoutMng.itemCount

                if (firstVisibleItem + visibleItemCount >= totalItemCount* 3/4) {
                    topRatedSeriesRecyclerView.removeOnScrollListener(this)
                    topRatedSeriesPage++
                    getTopRatedSeries()
                }
            }
        })
    }
    private fun getTopRatedSeries() {
        SeriesRepository.getTopRatedSeries(
            topRatedSeriesPage,
            onSuccess = ::onTopRatedSeriesFetched,
            onError = ::onError
        )
    }

    //--- On The Air Series Methodes
    private fun onOnAirSeriesFetched(series : List<Serie>) {
        onAirSeriesAdapter.appendSeries(series)
        weldOnAirSeriesOnScrollListener()
    }
    private fun weldOnAirSeriesOnScrollListener() {
        onAirSeriesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = onAirSeriesLayoutMng.childCount
                val firstVisibleItem = onAirSeriesLayoutMng.findFirstVisibleItemPosition()
                val totalItemCount = onAirSeriesLayoutMng.itemCount

                if (firstVisibleItem + visibleItemCount >= totalItemCount* 3/4) {
                    onAirSeriesRecyclerView.removeOnScrollListener(this)
                    onAirSeriesPage++
                    getOnAirSeries()
                }
            }
        })
    }
    private fun getOnAirSeries() {
        SeriesRepository.getOnAirSeries(
            onAirSeriesPage,
            onSuccess = ::onOnAirSeriesFetched,
            onError = ::onError
        )
    }


    //---
    private fun showSerieFragment(serie: Serie) {
        parentFragmentManager.beginTransaction().apply{
            replace(R.id.fragment_container, DetailSerieFragment(serie))
            addToBackStack(null)
        }.commit()
    }
}