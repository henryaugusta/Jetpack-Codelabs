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
import com.feylabs.firrieflix.data.source.remote.responses.ShowResponse
import com.feylabs.firrieflix.databinding.FragmentShowBinding
import com.feylabs.firrieflix.util.BaseFragment
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity.Companion.MOVIE_ID
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.data.ResponseHelper


class ShowFragment : BaseFragment() {

    lateinit var vbind: FragmentShowBinding
    lateinit var showAdapter: ShowAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vbind =
            FragmentShowBinding.bind(inflater.inflate(R.layout.fragment_show, container, false))
        return vbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingScreen()


        showAdapter = ShowAdapter()
        showAdapter.setMyMovieInterface(object : ShowAdapter.MovieInterface {


            override fun onclick(model: ShowResponse.Result) {
                val intent = Intent(requireContext(), DetailMovieActivity::class.java)
                intent.apply {
                    putExtra(MOVIE_ID, model.id.toString())
                    putExtra(
                        DetailMovieActivity.TYPE,
                        DetailMovieViewModel.MovType.SHOW.toString()
                    )
                }
                startActivity(intent)
            }

            override fun share(model: ShowResponse.Result) {
                val shareText = ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setText(
                        """
                        #Firrieflix
                        Watch this cool ${model.original_name} Film at Firriflix,
                        directed by ${model.id} at ${model.first_air_date}
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

        vbind.rvFilm.apply {
            adapter = showAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        val showViewModel = ViewModelProvider(
            requireActivity(),
            firrieflixVMFactory
        ).get(MovieViewModel::class.java)
        showViewModel.getShow()
        showAdapter.notifyDataSetChanged()

        showViewModel.getShow().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResponseHelper.Success -> {
                    hideLoadingScreen()
                    Log.d("show_fragment", "success")
                    Log.d("show_fragment", "length ${it.data?.size}")
                    it.data?.toMutableList()?.let { it1 -> showAdapter.setData(it1) }
                    showAdapter.notifyDataSetChanged()
                }
                is ResponseHelper.Error -> {
                    hideLoadingScreen()
                    Log.d("show_fragment", "error get film -> ${it.message.toString()}")
                }
                is ResponseHelper.Loading -> {
                    Log.d("show_fragment", "loading film")
                }
                else -> {
                    // Do Nothing, because all response wrapped in ResponseHelper, so this line will not reached
                }
            }
        })
    }

    fun hideLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            mroot.makeViewGone()
            labelLoading.text = message
        }
    }

    fun showLoadingScreen(message: String = getString(R.string.loading)) {
        vbind.includeLoading.apply {
            mroot.makeViewVisible()
            labelLoading.text = message
        }
    }


}