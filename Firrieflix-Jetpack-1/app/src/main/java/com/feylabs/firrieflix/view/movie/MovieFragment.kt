package com.feylabs.firrieflix.view.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.adapter.MovieAdapter
import com.feylabs.firrieflix.databinding.FragmentMovieBinding
import com.feylabs.firrieflix.model.MovieModel
import com.feylabs.firrieflix.view.detail.DetailMovieActivity
import com.feylabs.firrieflix.viewmodel.MovieViewModel


class MovieFragment : Fragment() {

    lateinit var vbind : FragmentMovieBinding
    lateinit var movieAdapter: MovieAdapter

    companion object{
        const val MOVIE_ID = "MOVIE_ID"
    }

    val movieViewModel by lazy { ViewModelProvider(requireActivity()).get(MovieViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vbind = FragmentMovieBinding.bind(inflater.inflate(R.layout.fragment_movie, container, false))
        // Inflate the layout for this fragment
        return vbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter()

        vbind.rvFilm.apply {
            adapter=movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        movieAdapter.setData(movieViewModel.getMovie())
        movieAdapter.notifyDataSetChanged()

        movieAdapter.setMyMovieInterface(object : MovieAdapter.MovieInterface{
            override fun onclick(model:MovieModel) {
                startActivity(Intent(requireContext(),DetailMovieActivity::class.java).putExtra(MOVIE_ID,model.id))
            }

            override fun share(model: MovieModel) {
                val shareText = ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setText("""
                        #Firrieflix
                        Watch this cool ${model.title} Film at Firriflix,
                        directed by ${model.director} at ${model.releaseDate}
                        short story line : 
                        ${model.description}
                    """.trimIndent())
                    .intent
                if (shareText.resolveActivity(activity?.packageManager!!) != null) {
                    startActivity(shareText)
                }
            }

        })


    }


}