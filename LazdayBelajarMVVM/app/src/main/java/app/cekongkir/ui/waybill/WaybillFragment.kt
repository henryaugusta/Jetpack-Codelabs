package app.cekongkir.ui.waybill

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.cekongkir.R
import app.cekongkir.databinding.FragmentWaybillBinding
import app.cekongkir.ui.tracking.TrackingActivity

class WaybillFragment : Fragment() {

    lateinit var binding : FragmentWaybillBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentWaybillBinding.bind(inflater.inflate(R.layout.fragment_waybill, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
    }

    fun setUpListener(){
        binding.editWaybill.setOnClickListener {
            startActivity(Intent(requireContext(),TrackingActivity::class.java))
        }
    }
}