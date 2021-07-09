package com.feylabs.firrieflix.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.local.LocalDataSource
import com.feylabs.firrieflix.data.source.local.room.FavoriteDatabase
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.databinding.FragmentFavoritePageBinding
import com.feylabs.firrieflix.ui.detail.DetailMovieActivity
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.util.BaseFragment
import com.feylabs.firrieflix.viewmodel.ViewModelFactory


class FavoritePageFragment(private val type: Int) : BaseFragment() {

    private var _binding: FragmentFavoritePageBinding? = null
    private val vbind get() = _binding as FragmentFavoritePageBinding

    val factory by lazy {
        ViewModelFactory(
            FirrieflixRepository(
                RemoteDataSource(),
                LocalDataSource(FavoriteDatabase(requireContext()).favDao())
            )
        )
    }

    val favoriteViewModel by lazy {
        ViewModelProvider(requireActivity(), factory).get(
            FavoriteViewModel::class.java
        )
    }

    val favoriteAdapter by lazy { FavoritePagedListAdapter(requireContext()) }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        Log.d("fstate", "onstart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("fstate", "resumed")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite_page, container, false)
        _binding = FragmentFavoritePageBinding.bind(view)
        return vbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vbind.rvFav.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        favoriteAdapter.setInterface(object : FavoritePagedListAdapter.FavoriteInterface {
            override fun onClick(model: FavoriteEntity) {
                model.name?.showLongToast()
                Log.d("send_model", model.toString())
                val intent = Intent(requireContext(), DetailMovieActivity::class.java)
                intent.apply {
                    putExtra(DetailMovieActivity.MOVIE_ID, model.movId.toString())
                    if (model.favType == 1) {
                        putExtra(
                            DetailMovieActivity.TYPE,
                            DetailMovieViewModel.MovType.MOVIE.toString()
                        )
                    } else {
                        putExtra(
                            DetailMovieActivity.TYPE,
                            DetailMovieViewModel.MovType.SHOW.toString()
                        )
                    }
                }
                startActivity(intent)
            }

            override fun onDelete(model: FavoriteEntity) {
                favoriteViewModel.deleteFavorite(model.movId.toString())
                favoriteAdapter.notifyDataSetChanged()
                showSweetAlert(
                    getString(R.string.success),
                    getString(R.string.success_delete) + model.name + getString(R.string.from_fav),
                    R.color.alert_green
                )
            }
        })

        if (type == 1) {
            observeMovie()
        } else {
            observeShows()
        }

    }

    private fun observeMovie() {
        Log.d("observe_show", "observe_show")
        favoriteViewModel.getAllFavMovies().observe(viewLifecycleOwner, Observer {
            vbind.includeLoading.apply {
                Log.d("observe_show", "no_data_pak")
                if (it.size != 0) {
                    Log.d("observe_show", it.size.toString())
                    favoriteAdapter.submitList(it)
                    favoriteAdapter.notifyDataSetChanged()
                    labelLoading.text = requireContext().getString(R.string.loading)
                    root.visibility = View.GONE
                } else {
                    Log.d("observe_show", "no_data_pak")
                    labelLoading.text = requireContext().getString(R.string.no_data_yet)
                    root.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeShows() {
        Log.d("observe_show", "observe_show")
        favoriteViewModel.getAllFavShow().observe(viewLifecycleOwner, Observer {
            vbind.includeLoading.apply {
                Log.d("observe_show", "no_data_pak")
                if (it.size != 0) {
                    Log.d("observe_show", it.size.toString())
                    favoriteAdapter.submitList(it)
                    favoriteAdapter.notifyDataSetChanged()
                    labelLoading.text = requireContext().getString(R.string.loading)
                    root.visibility = View.GONE
                } else {
                    Log.d("observe_show", "no_data_pak")
                    labelLoading.text = requireContext().getString(R.string.no_data_yet)
                    root.visibility = View.VISIBLE
                }
            }
        })
    }


}