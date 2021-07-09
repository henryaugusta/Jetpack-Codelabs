package com.feylabs.firrieflix.view.show

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
import com.feylabs.firrieflix.adapter.ShowAdapter
import com.feylabs.firrieflix.databinding.FragmentShowBinding
import com.feylabs.firrieflix.model.MovieModel
import com.feylabs.firrieflix.view.detail.DetailMovieActivity
import com.feylabs.firrieflix.view.movie.MovieFragment
import com.feylabs.firrieflix.viewmodel.ShowViewModel


class ShowFragment : Fragment() {

    lateinit var vbind: FragmentShowBinding
    lateinit var showAdapter: MovieAdapter

    val showViewModel by lazy { ViewModelProvider(requireActivity()).get(ShowViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vbind =
            FragmentShowBinding.bind(inflater.inflate(R.layout.fragment_show, container, false))
        // Inflate the layout for this fragment
        return vbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAdapter = MovieAdapter()

        showAdapter.setMyMovieInterface(object : MovieAdapter.MovieInterface{
            override fun onclick(model: MovieModel) {
                startActivity(
                    Intent(requireContext(), DetailMovieActivity::class.java).putExtra(
                        MovieFragment.MOVIE_ID,model.id))
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

        vbind.rvFilm.apply {
            adapter = showAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        showAdapter.setData(showViewModel.getShow())
        showAdapter.notifyDataSetChanged()


    }


}