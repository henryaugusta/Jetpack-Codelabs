package app.cekongkir.ui.subdistrict

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.cekongkir.databinding.FragmentSubdistrictBinding
import app.cekongkir.network.Resource
import app.cekongkir.network.response.SubdistrictResponse
import app.cekongkir.ui.city.CityViewModel
import app.cekongkir.utils.showToast
import timber.log.Timber

class SubdistrictFragment : Fragment() {

    lateinit var viewbind : FragmentSubdistrictBinding

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(CityViewModel::class.java) }

    private val cityName by lazy { requireArguments().getString("city_name") }
    private val cityID by lazy { requireArguments().getString("city_id") }
    private val type by lazy { requireActivity().intent.getStringExtra("intent_type") }

    lateinit var adapterSubdistrict: SubdistrictAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewbind = FragmentSubdistrictBinding.inflate(inflater,container,false)
        return viewbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpListener()
        setUpAdapter()
        setUpObserver()
        setUpRecyclerView()
        viewModel.fetchSubdistrict(cityID.toString())
        Timber.d("Nama Kota : $cityName")
    }

    fun setUpRecyclerView(){
        viewbind.rvListSubdistrict.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(requireContext())
            adapter = adapterSubdistrict
        }
    }

    private fun setUpAdapter(){
        adapterSubdistrict = SubdistrictAdapter()
        adapterSubdistrict.setListener(object : SubdistrictAdapter.OnAdapterListener{
            override fun onClick(result: SubdistrictResponse.Rajaongkir.Result) {
                viewModel.savePreferences(
                        type=type.toString(),
                        id= result.subdistrict_id,
                        name= "$cityName - ${result.subdistrict_name}",
                )
                requireActivity().showToast("Hey")
                Timber.d("subdistrictFragment: -> Savepref  = $type , $id , $$cityName - ${result.subdistrict_name}")
                requireActivity().finish()
            }

        })
    }

    private fun setUpListener(){
        viewbind.refreshSubdistrict.setOnRefreshListener {
            viewModel.fetchSubdistrict(cityID.toString())
        }
    }

    private fun setUpView(){
        viewModel.titleBar.postValue("Pilih Kecamatan")
    }

    private fun setUpObserver(){
        viewModel.subDistrictResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Error ->{
                    viewbind.refreshSubdistrict.isRefreshing=false
                    Timber.d("subdistrictFragment: Error")
                }
                is Resource.Success->{
                    viewbind.refreshSubdistrict.isRefreshing=false
                    Timber.d("subdistrictFragment: Success")
                    it.data?.rajaongkir?.results?.toMutableList()?.let { it1 -> adapterSubdistrict.setSubdistrictData(it1) }
                    adapterSubdistrict.notifyDataSetChanged()
                }
                is Resource.Loading->{
                    viewbind.refreshSubdistrict.isRefreshing=true
                    Timber.d("subdistrictFragment: Loading")
                }
            }
        })
    }
}