package com.feylabs.firrieflix.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.data.source.remote.responses.MovieResponse
import com.feylabs.firrieflix.databinding.FragmentMovieBinding
import com.feylabs.firrieflix.util.BaseFragment
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity.Companion.MOVIE_ID
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity.Companion.TYPE
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.data.ResponseHelper


class MovieFragment : BaseFragment(){
    lateinit var movieAdapter: MovieAdapter

    private var _binding : FragmentMovieBinding? = null
    private val vbind get() = _binding as FragmentMovieBinding

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.bind(inflater.inflate(R.layout.fragment_movie, container, false))
        return vbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingScreen()

        val movieViewModel = ViewModelProvider(requireActivity(), firrieflixVMFactory).get(MovieViewModel::class.java)
        movieAdapter = MovieAdapter()


        vbind.rvFilm.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        movieViewModel.getMovie().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResponseHelper.Success -> {
                    hideLoadingScreen()
                    Log.d("movie_fragment", "success")
                    Log.d("movie_fragment", "length ${it.data?.size}")
                    it.data?.let { it1 -> movieAdapter.setData(it1.toMutableList()) }
                    movieAdapter.notifyDataSetChanged()
                }
                is ResponseHelper.Error -> {
                    showLoadingScreen(it.message.toString())
                    Log.d("movie_fragment", "error get film -> ${it.message.toString()}")
                }
                is ResponseHelper.Loading -> {
                    showLoadingScreen()
                    Log.d("movie_fragment", "loading film")
                }
                else -> {
                    // Do Nothing, because all response wrapped in ResponseHelper, so this line will not reached
                }
            }
        })


        movieAdapter.setMyMovieInterface(object : MovieAdapter.MovieInterface {

            override fun onclick(model: MovieResponse.Result) {
                val intent = Intent(requireContext(), DetailMovieActivity::class.java)
                intent.apply {
                    putExtra(MOVIE_ID, model.id.toString())
                    putExtra(TYPE, DetailMovieViewModel.MovType.MOVIE.toString())
                }
                startActivity(intent)

            }

            override fun share(model: MovieResponse.Result) {
                val shareText = ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setText(
                        """
                        #Firrieflix
                        Watch this cool ${model.title} Film at Firriflix,
                        directed by ${model.id} at ${model.release_date}
                        short story line : 
                        ${model.overview}
                    """.trimIndent()
                    )
                    .intent
                if (shareText.resolveActivity(activity?.packageManager!!) != null) {
                    startActivity(shareText)
                }
            }

        })
    }


    private fun hideLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            mroot.makeViewGone()
            labelLoading.text=message
        }
    }

    private fun showLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            mroot.makeViewVisible()
            labelLoading.text=message
        }
    }

}