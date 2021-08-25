package com.example.movierating.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.movierating.R
import com.example.movierating.data.model.Movies
import com.example.movierating.databinding.ActiviytMovieDetailsBinding
import com.example.movierating.util.AppUtils
import com.example.movierating.util.Constant
import com.example.movierating.util.Constant.IMAGE_BASE_URL
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener {
    private var movie: Movies? = null

    private lateinit var binding: ActiviytMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActiviytMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movie = intent.getParcelableExtra(Constant.MOVIE)
        initView()
        updateUI()
    }

    /**
     * used to update UI with the movie data
     */
    private fun updateUI() {
        movie?.let {
            it.releaseDate?.let { date -> binding.txtYear.text = AppUtils.convertDateFormat(date) }
            binding.txtTitle.text = it.originalTitle
            binding.txtVoteCount.text = it.voteCount.toString()
            binding.txtRating.text = it.voteAverage.toString()
            binding.txtOverview.text = it.overview.toString()
            if (it.originalLanguage == "en")
                binding.txtLanguage.text = getString(R.string.english)

            // Using picasso to load images from URL
            Picasso.get().load(IMAGE_BASE_URL + it.posterPath)
                .placeholder(R.drawable.ic_baseline_movie_24).fit()
                .into(binding.imgMovie)
        }
    }

    private fun initView() {
        binding.imgBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgBack -> finish()
        }
    }
}
