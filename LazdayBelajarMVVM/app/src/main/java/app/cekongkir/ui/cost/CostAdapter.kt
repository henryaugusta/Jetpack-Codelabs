package app.cekongkir.ui.cost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterCostBinding
import app.cekongkir.network.response.CostResponse

class CostAdapter(

) : RecyclerView.Adapter<CostAdapter.ViewHolder>() {

    var results: ArrayList<CostResponse.Rajaongkir.Results> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            AdapterCostBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        var finalResult = ""

        var count = 1;
        result.costs.forEach {

            var cost = ""
            it.cost.forEach { tes ->
                cost += "${tes.value}\nEstimasi : ${tes.etd}\n"
            }

            finalResult += "${it.service} $cost" +
                    "${it.description}\n"
            count++
        }

        holder.binding.textCode.text = result.code
        holder.binding.textName.text = result.name.toString().toUpperCase()
        holder.binding.textName.text = finalResult
//        holder.binding.listService.apply {
//            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
//            adapter = ServiceAdapter( result.costs )
//        }
    }

    class ViewHolder(val binding: AdapterCostBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<CostResponse.Rajaongkir.Results>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }
}