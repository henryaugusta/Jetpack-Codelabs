package com.feylabs.firrieflix.ui.movie

import android.content.ActivityNotFoundException
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
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.remote.responses.MovieResponse
import com.feylabs.firrieflix.databinding.FragmentMovieBinding
import com.feylabs.firrieflix.util.BaseFragment
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity.Companion.MOVIE_ID
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity.Companion.TYPE
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.data.ResponseHelper
import com.feylabs.firrieflix.data.source.local.LocalDataSource
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.room.FavoriteDatabase
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.viewmodel.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import java.lang.Error


class MovieFragment : BaseFragment() {
    lateinit var movieAdapter: MoviePagedListAdapter

    private var _binding: FragmentMovieBinding? = null
    private val vbind get() = _binding as FragmentMovieBinding

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentMovieBinding.bind(inflater.inflate(R.layout.fragment_movie, container, false))
        return vbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingScreen(getString(R.string.loading))
        val factory = ViewModelFactory(
            FirrieflixRepository(
                RemoteDataSource(),
                LocalDataSource(FavoriteDatabase(requireContext()).favDao())
            )
        )

        val movieViewModel = ViewModelProvider(
            requireActivity(),
            factory
        ).get(MovieViewModel::class.java)
        movieAdapter = MoviePagedListAdapter()

        vbind.includeLoading.apply {
            srl.setOnRefreshListener {
                srl.isRefreshing = false
                movieViewModel.getMovie()
            }
        }

        vbind.rvFilm.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        movieViewModel.getLocalMovies().observe(viewLifecycleOwner, Observer {
            if (it.size != 0) {
                hideLoadingScreen(getString(R.string.loading))
                movieAdapter.submitList(it)
                movieAdapter.notifyDataSetChanged()
            } else {
                if (movieViewModel.getMovie().value is ResponseHelper.Error){
                    showLoadingScreen(getString(R.string.waiting_inet))
                }else{
                    showLoadingScreen(getString(R.string.no_data_yet))
                }
            }
        })


        movieAdapter.setInterface(object : MoviePagedListAdapter.MovieInterface {
            override fun onclick(model: MovieLocalEntity) {
                Log.d("selected_model", model.toString())
                val intent = Intent(requireContext(), DetailMovieActivity::class.java)
                intent.apply {
                    putExtra(MOVIE_ID, model.id.toString())
                    putExtra(TYPE, DetailMovieViewModel.MovType.MOVIE.toString())
                }
                startActivity(intent)
            }

            override fun share(model: MovieLocalEntity) {
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
                try {
                    startActivity(shareText)
                } catch (ex: ActivityNotFoundException) {
                    getString(R.string.no_app_found).showLongToast()
                }
            }

        })
    }


    private fun hideLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            this.root.makeViewGone()
            labelLoading.text = message
        }
    }

    private fun showLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            this.root.makeViewVisible()
            labelLoading.text = message
        }
    }




}