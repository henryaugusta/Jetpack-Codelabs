package app.cekongkir.ui.subdistrict

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterSubdistrictBinding
import app.cekongkir.network.response.CityResponse
import app.cekongkir.network.response.SubdistrictResponse
import timber.log.Timber

class SubdistrictAdapter() : RecyclerView.Adapter<SubdistrictAdapter.ViewHolder>() {

    var subdistricts = arrayListOf<SubdistrictResponse.Rajaongkir.Result>()
    lateinit var subdistrictListener: OnAdapterListener

    fun setSubdistrictData(dataDistrict  : MutableList<SubdistrictResponse.Rajaongkir.Result>){
        subdistricts.clear()
        this.subdistricts.addAll(dataDistrict)
        notifyDataSetChanged()
    }

    fun setListener(listenerSet : OnAdapterListener){
        this.subdistrictListener = listenerSet
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            AdapterSubdistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subdistric = subdistricts[position]
        holder.binding.textName.text = subdistric.subdistrict_name
        holder.binding.container.setOnClickListener {
            subdistrictListener.onClick(subdistric)
        }
    }

    override fun getItemCount() = subdistricts.size


    class ViewHolder(val binding: AdapterSubdistrictBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: SubdistrictResponse.Rajaongkir.Result)
    }

}