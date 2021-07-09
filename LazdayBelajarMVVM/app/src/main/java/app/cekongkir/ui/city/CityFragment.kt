package app.cekongkir.ui.city

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import app.cekongkir.R
import app.cekongkir.databinding.FragmentCityBinding
import app.cekongkir.network.Resource
import app.cekongkir.network.response.CityResponse
import app.cekongkir.utils.showToast
import timber.log.Timber

class CityFragment : Fragment() {

    lateinit var viewBinding: FragmentCityBinding
    lateinit var adapter: CityAdapter

    val viewModel by lazy { ViewModelProvider(requireActivity()).get(CityViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewBinding = FragmentCityBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpView()
        setUpAdapter()
        setUpRecyclerView()
        setUpObserver()


        viewModel.titleBar.postValue("Pilih Kota")
        viewModel.fetchData()

    }

    private fun setUpView() {

        viewBinding.editSearch.doAfterTextChanged {
            adapter.filter.filter(it.toString())
        }

        viewBinding.refreshCity.setOnRefreshListener {
            viewModel.fetchData()
        }

        viewBinding.container.setOnClickListener {

        }
    }

    private fun setUpObserver() {
        viewModel.cityResponse.observe(requireActivity(), Observer {
            when(it){
                is Resource.Error ->{
                    viewBinding.refreshCity.isRefreshing=false
                    Timber.d("cityFragment: Error")
                }
                is Resource.Success->{
                    viewBinding.refreshCity.isRefreshing=false
                    Timber.d("cityFragment: Success")
                    it.data?.rajaongkir?.results?.let { it1 -> adapter.setData(it1) }
                    adapter.notifyDataSetChanged()
                    CityFragment().showToast("Panjang Data : ${adapter.itemCount}")
                }
                is Resource.Loading->{
                    viewBinding.refreshCity.isRefreshing=true
                    Timber.d("cityFragment: Loading")
                }
            }
        })
    }

    private fun setUpAdapter() {
        adapter = CityAdapter()
        adapter.setListener(object :CityAdapter.CityAdapterListener{
            override fun onclick(model: CityResponse.Rajaongkir.Result) {

                findNavController().navigate(
                        R.id.action_cityFragment_to_subdistrictFragment,
                        bundleOf("city_id" to model.city_id,"city_name" to model.city_name )
                )

            }
        })
    }

    private fun setUpRecyclerView() {
        viewBinding.listCity.setHasFixedSize(true)
        viewBinding.listCity.layoutManager=LinearLayoutManager(requireContext())
        viewBinding.listCity.adapter = adapter
    }


}