package app.cekongkir.ui.trackingresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.databinding.FragmentTrackingResultBinding
import app.cekongkir.network.Resource
import timber.log.Timber

class TrackingResultFragment : Fragment() {

    private lateinit var binding: FragmentTrackingResultBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(TrackingViewModel::class.java)
    }

    private val waybill by lazy { requireArguments().getString("waybill") }
    private val courier by lazy { requireArguments().getString("courier") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackingResultBinding.bind(inflater.inflate(R.layout.fragment_tracking_result, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchWaybill(waybill.toString(), courier.toString())
    }

    private fun setUpObserver(){
        viewModel.waybillResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Error ->{
                    Timber.d("waybillResponse: Error")
                }
                is Resource.Success->{
                    Timber.d("waybillResponse: Success")
                    Timber.d("waybillResponse: ${it.data?.rajaongkir?.result}")
                }
                is Resource.Loading->{
                    Timber.d("waybillResponse: Loading")
                }
            }
        })
    }

}