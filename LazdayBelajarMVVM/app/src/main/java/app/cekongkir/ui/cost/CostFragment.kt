package app.cekongkir.ui.cost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.cekongkir.R
import app.cekongkir.databinding.FragmentCostBinding
import app.cekongkir.network.Resource
import app.cekongkir.ui.city.CityActivity
import app.cekongkir.ui.city.CityFragment
import app.cekongkir.ui.city.CityViewModel
import app.cekongkir.utils.showToast
import app.cekongkir.utils.viewHide
import app.cekongkir.utils.viewShow
import timber.log.Timber

class CostFragment : Fragment() {

    lateinit var vbind: FragmentCostBinding

    private var originID = ""
    private var destinationID = ""

    val viewModel by lazy { ViewModelProvider(requireActivity()).get(CostViewModel::class.java) }
    private lateinit var costAdapter: CostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        vbind = FragmentCostBinding.inflate(inflater, container, false)
        return vbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vbind.progressCost.visibility=View.GONE
        setUpListener()
        setUpObserver()

        costAdapter=CostAdapter()

        vbind.listCost.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = costAdapter
        }
        viewModel.getPreference()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getPreference()
    }
    override fun onStart() {
        super.onStart()
        viewModel.getPreference()
    }




    private fun setUpObserver() {
        viewModel.preference.observe(viewLifecycleOwner, Observer { prefList ->
            prefList.forEach {
                when (it.type) {
                    "origin" -> {
                        originID = it.id
                        vbind.editOrigin.setText(it.name)
                    }
                    "destination" -> {
                        destinationID = it.id
                        vbind.editDestination.setText(it.name)
                    }
                }
            }
        })

        viewModel.costResponse.observe( viewLifecycleOwner, Observer{
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    Timber.d("resultCost : ${it.data.toString()}")
                    costAdapter.setData( it.data!!.rajaongkir.results )
                    costAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    Timber.d("resultCost : ${it.message}")
                }
            }
        })
    }

    private fun setUpListener() {
        vbind.buttonCost.setOnClickListener {
            if (originID.isEmpty()) {
                requireActivity().showToast("Lengkapi Semua Kolom")
            } else {
                requireActivity().showToast("Loading")
                viewModel.fetchCost(
                        origin = originID,
                        originType = "subdistrict",
                        destination = destinationID,
                        destinationType = "subdistrict",
                        weight = "1000",
                        courier = "sicepat:jnt:pos:wahana:jne",
                )
            }

        }


        vbind.editOrigin.setOnClickListener {
            startActivity(Intent(context, CityActivity::class.java)
                    .putExtra("intent_type", "origin")
            )
        }

        vbind.editDestination.setOnClickListener {
            startActivity(Intent(context, CityActivity::class.java)
                    .putExtra("intent_type", "destination")
            )
        }
    }


    private fun loadingCost(loading: Boolean) {
        if (loading) vbind.progressCost.visibility = View.VISIBLE
        else vbind.progressCost.visibility = View.GONE

    }
}