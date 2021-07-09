package app.cekongkir.ui.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.R
import app.cekongkir.databinding.AdapterCityBinding
import app.cekongkir.network.response.CityResponse
import timber.log.Timber

class CityAdapter() : RecyclerView.Adapter<CityAdapter.CityViewHolder>() , Filterable {

    val cities: ArrayList<CityResponse.Rajaongkir.Result> = arrayListOf()
    var citiesFilter: ArrayList<CityResponse.Rajaongkir.Result> = arrayListOf()

    lateinit var cityListener : CityAdapterListener
    fun setData(data : ArrayList<CityResponse.Rajaongkir.Result> ){
        cities.clear()
        cities.addAll(data)
        citiesFilter.clear()
        citiesFilter.addAll(data)
        notifyDataSetChanged()
    }

    fun setListener(cityListener : CityAdapterListener){
        this.cityListener = cityListener
    }


    inner class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vbind = AdapterCityBinding.bind(view)
        fun bind(model : CityResponse.Rajaongkir.Result){
            vbind.textName.text = model.city_name
            vbind.container.setOnClickListener {
                cityListener.onclick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_city,parent,false)
        return CityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return citiesFilter.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(citiesFilter[position])
    }

    interface CityAdapterListener{
        fun onclick(model: CityResponse.Rajaongkir.Result)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Timber.d("charSearch: $charSearch")
                if (charSearch.isEmpty()) {
                    citiesFilter = cities
                } else {
                    val citiesFiltered = ArrayList<CityResponse.Rajaongkir.Result>()
                    for (city in cities) {
                        if (city.city_name.toLowerCase().contains(charSearch.toLowerCase())) {
                            citiesFiltered.add(city)
                        }
                    }
                    citiesFilter = citiesFiltered
                }
                val citiesFilteredResult = FilterResults()
                citiesFilteredResult.values = citiesFilter
                return citiesFilteredResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                citiesFilter = results?.values as ArrayList<CityResponse.Rajaongkir.Result>
                notifyDataSetChanged()
            }

        }

    }


}