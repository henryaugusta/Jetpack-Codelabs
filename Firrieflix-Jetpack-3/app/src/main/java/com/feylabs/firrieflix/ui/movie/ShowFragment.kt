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
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.remote.responses.ShowResponse
import com.feylabs.firrieflix.databinding.FragmentShowBinding
import com.feylabs.firrieflix.util.BaseFragment
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity.Companion.MOVIE_ID
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.data.ResponseHelper
import com.feylabs.firrieflix.data.source.local.LocalDataSource
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity
import com.feylabs.firrieflix.data.source.local.room.FavoriteDao
import com.feylabs.firrieflix.data.source.local.room.FavoriteDatabase
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.viewmodel.ViewModelFactory


class ShowFragment : BaseFragment() {

    lateinit var vbind: FragmentShowBinding
    lateinit var showAdapter: ShowPagedListAdapter


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
        showLoadingScreen(getString(R.string.loading))


        val factory = ViewModelFactory(
            FirrieflixRepository(
                RemoteDataSource(),
                LocalDataSource(FavoriteDatabase(requireContext()).favDao())
            )
        )

        val showViewModel = ViewModelProvider(
            requireActivity(),
            factory
        ).get(MovieViewModel::class.java)

        vbind.includeLoading.apply {
            srl.setOnRefreshListener {
                srl.isRefreshing = false
                showViewModel.getShow()
            }
        }

        showAdapter = ShowPagedListAdapter()
        showAdapter.setInterface(object : ShowPagedListAdapter.ShowInterface {

            override fun onclick(model: ShowLocalEntity) {
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

            override fun share(model: ShowLocalEntity) {
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

        showViewModel.getShow().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResponseHelper.Success -> {
                    hideLoadingScreen()
                }
                is ResponseHelper.Loading -> {
                    showLoadingScreen(getString(R.string.loading))
                }
                else -> {
                } //Do Nothing because already wrapped in ResponseHelper
            }
        })
        showAdapter.notifyDataSetChanged()

        showViewModel.getLocalShows().observe(viewLifecycleOwner, Observer {
            if (it.size != 0) {
                hideLoadingScreen(getString(R.string.loading))
                showAdapter.submitList(it)
                showAdapter.notifyDataSetChanged()
            } else {
                Log.d("api_show_stat" , showViewModel.getShow().value.toString())
                if (showViewModel.getShow().value is ResponseHelper.Error){
                    showLoadingScreen(getString(R.string.waiting_inet))
                }else{
                    showLoadingScreen(getString(R.string.no_data_yet))
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