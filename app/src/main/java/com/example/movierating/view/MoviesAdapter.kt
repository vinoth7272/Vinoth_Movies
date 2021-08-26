package com.example.movierating.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.model.Movies
import com.example.movierating.databinding.ListItemMovieBinding
import com.example.movierating.util.AppUtils
import com.example.movierating.util.ListItemClickListener

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private lateinit var listItemClickListener: ListItemClickListener
    private var moviesList = listOf<Movies>()

    /**
     * recycler view list item click callback initialization
     */
    fun setClickListener(clickListener: ListItemClickListener) {
        listItemClickListener = clickListener
    }

    /**
     * method helps to set data to adapter
     */
    fun setData(recordFileList: List<Movies>) {
        this.moviesList = recordFileList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Log.d(MoviesAdapter::class.java.simpleName, "MovieViewHolder position-> $position")
        holder.bindData(moviesList[position], position, listItemClickListener)
    }

    override fun getItemCount() = moviesList.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemMovieBinding.bind(itemView)

        fun bindData(
            movie: Movies,
            position: Int,
            listItemClickListener: ListItemClickListener
        ) {
            Log.d(MoviesAdapter::class.java.simpleName, "MovieViewHolder-> ${movie.title}")
            binding.txtTitle.text = movie.title
            movie.releaseDate?.let { date ->
                binding.txtDate.text = AppUtils.convertDateFormat(date)
            }
            binding.txtRating.text = movie.voteAverage.toString()
            itemView.setOnClickListener {
                listItemClickListener.onListItemClickListener(it, position)
            }
        }
    }
}
