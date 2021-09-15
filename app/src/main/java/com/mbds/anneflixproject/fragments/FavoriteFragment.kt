package com.mbds.anneflixproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.mbds.anneflixproject.NavigationListener
import com.mbds.anneflixproject.R
import com.mbds.anneflixproject.adapter.FavoriteAdapter
import com.mbds.anneflixproject.database.AppDatabase
import com.mbds.anneflixproject.databinding.FavoriteFragmentBinding
import com.mbds.anneflixproject.models.*

/*
TOD: Rename parameter arguments, choose names that match*/
/* the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class FavoriteFragment : Fragment() {
    /* TOD: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null*/

    lateinit var binding : FavoriteFragmentBinding

    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter

    private lateinit var filter: Spinner

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            requireContext(),//activity!!.applicationContext
            AppDatabase::class.java,
            "mymovies.db"
        ).allowMainThreadQueries().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.favorite_fragment, container, false)
        //change toolbar title by title fragment
        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.favorite)
        }

        /*
        binding = FavoriteFragmentBinding.inflate(inflater)
        return binding.root
        */

        favoriteRecyclerView = view.findViewById(R.id.favorite_recyclerview)
        favoriteRecyclerView.layoutManager = GridLayoutManager(context, 3)
        favoriteAdapter = FavoriteAdapter(listOf())
        favoriteRecyclerView.adapter = favoriteAdapter

        filter = view.findViewById(R.id.favorite_filter)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.favorite_filter,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            filter.adapter = adapter
        }

        getFavorite()

        return view

    }
    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            when (filter.selectedItemPosition) {
                0 -> getFavorite()
                1 -> getMovies()
                2 -> getSeries()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> getFavorite()
                    1 -> getMovies()
                    2 -> getSeries()
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        when (filter.selectedItemPosition) {
            0 -> getFavorite()
            1 -> getMovies()
            2 -> getSeries()
        }
    }

    //Get Movies & Serie for spinner/filter selection
    private fun getFavorite() {
        val movies = db.movieDao().getAll()
        val series = db.serieDao().getAll()
        val favorite = mutableListOf<Favorite>()
        favorite.addAll(
            movies.map { movie ->
                Favorite(
                    movie.id,
                    movie.title,
                    movie.overview,
                    movie.posterPath,
                    movie.backdropPath,
                    movie.rating,
                    movie.releaseDate,
                    FavoriteType.MovieType
                )
            }
        )
        favorite.addAll(
            series.map { serie ->
                Favorite(
                    serie.id,
                    serie.name,
                    serie.overview,
                    serie.posterPath,
                    serie.backdropPath,
                    serie.rating,
                    serie.firstAirDate,
                    FavoriteType.SerieType
                )
            }
        )
        favoriteAdapter.updateItems(favorite)
    }
    //Get Movies only for spinner/filter selection
    private fun getMovies() {
        val movies = db.movieDao().getAll()
        val favorite = movies.map { movie ->
            Favorite(
                movie.id,
                movie.title,
                movie.overview,
                movie.posterPath,
                movie.backdropPath,
                movie.rating,
                movie.releaseDate,
                FavoriteType.MovieType
            )
        }
        favoriteAdapter.updateItems(favorite)
    }
    //Get Series only for spinner/filter selection
    private fun getSeries() {
        val series = db.serieDao().getAll()
        val favorite = series.map { serie ->
            Favorite(
                serie.id,
                serie.name,
                serie.overview,
                serie.posterPath,
                serie.backdropPath,
                serie.rating,
                serie.firstAirDate,
                FavoriteType.SerieType
            )
        }
        favoriteAdapter.updateItems(favorite)
    }

    companion object {
        /*
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */

        /* TOD: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }*/
    }
}