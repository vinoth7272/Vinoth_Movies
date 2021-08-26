package com.example.movierating.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.BuildConfig
import com.example.movierating.R
import com.example.movierating.data.model.MoviePage
import com.example.movierating.data.model.Movies
import com.example.movierating.data.network.NetworkUtils.isNetworkAvailable
import com.example.movierating.data.network.Resource
import com.example.movierating.data.network.Status
import com.example.movierating.databinding.ActivityMainBinding
import com.example.movierating.util.Constant.API_KEY
import com.example.movierating.util.Constant.MOVIE
import com.example.movierating.util.ListItemClickListener
import com.example.movierating.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class MovieListActivity :
    AppCompatActivity(),
    DIAware,
    ListItemClickListener,
    View.OnClickListener {
    private var moviesList: List<Movies> = mutableListOf()
    override val di: DI by closestDI()
    private val movieViewModel: MovieViewModel by instance()
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initialApiCall()
        initAdapter()
        initObserver()
    }

    /**
     * method helps to set initial key word and make a initial API call with that keyword
     */
    private fun initialApiCall() {
        binding.txtSearchView.setText(getString(R.string.default_keyword))
        val keyWord = binding.txtSearchView.text.toString()
        movieViewModel.fetchMovies(BuildConfig.API_KEY, keyWord)
    }

    private fun initViews() {
        binding.btnClose.setOnClickListener(this)
        binding.btnGo.setOnClickListener(this)
    }

    /**
     * used to initialize the movies adapter,layout manager and set data to the adapter
     */
    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        moviesAdapter = MoviesAdapter()
        binding.recyclerView.adapter = moviesAdapter
        moviesAdapter.setClickListener(this)
        moviesAdapter.setData(mutableListOf())
    }

    /**
     * Method used to observe the live data object from view model class
     */
    private fun initObserver() {
        movieViewModel.getMovies().observe(this, this::movieListResponse)
    }

    /**
     * method used to process the movies API response live data
     * @param moviesResource which has 3 status
     * @{Status.SUCCESS} - Used to get the movie list API data and set it to adapter
     * @{Status.ERROR} - Used to set the API error response
     * @{Status.LOADING} - Used to show progress bar while fetching data from API
     */
    private fun movieListResponse(moviesResource: Resource<MoviePage>) {
        when (moviesResource.status) {
            Status.SUCCESS -> {
                CoroutineScope(Dispatchers.Default).launch {
                    moviesResource.data?.let {
                        it.movies?.let { movies ->
                            moviesList = movies
                            Log.d(
                                MovieViewModel::class.java.simpleName,
                                "movieListResponse -> ${moviesList.size}"
                            )
                            withContext(Dispatchers.Main) {
                                updateUI(moviesList.isNotEmpty())
                            }
                            moviesAdapter.setData(moviesList)
                        }
                    }
                }
            }
            Status.ERROR -> {
                updateUI(moviesAdapter.itemCount > 0)
            }
            Status.LOADING -> {
                binding.progressCircular.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.txtNoResult.visibility = View.GONE
            }
        }
    }

    private fun updateUI(hasItems: Boolean) {
        binding.progressCircular.visibility = View.GONE
        if (hasItems) {
            binding.txtNoResult.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.txtNoResult.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    /**
     * list item click listener
     * @param view list item view
     * @param position item position
     */
    override fun onListItemClickListener(view: View, position: Int) {
        val detailActivity = Intent(this, MovieDetailActivity::class.java)
        detailActivity.putExtra(MOVIE, moviesList[position])
        startActivity(detailActivity)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnClose -> {
                binding.txtSearchView.setText("")
            }
            R.id.btnGo -> {
                if (!this.isNetworkAvailable()) {
                    Snackbar.make(
                        binding.parentLayout,
                        getString(R.string.network_error),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    return
                }
                if (binding.txtSearchView.text.toString().isEmpty()) {
                    Snackbar.make(
                        binding.parentLayout,
                        getString(R.string.keyword_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }
                moviesAdapter.setData(mutableListOf())
                moviesAdapter.notifyDataSetChanged()
                val keyWord = binding.txtSearchView.text.toString()
                movieViewModel.fetchMovies(API_KEY, keyWord)
            }
        }
    }
}
