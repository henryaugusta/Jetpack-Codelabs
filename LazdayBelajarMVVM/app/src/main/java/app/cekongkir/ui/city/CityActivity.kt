package app.cekongkir.ui.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.database.preferences.CekOngkirPreference
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.Resource
import org.kodein.di.Instance
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class CityActivity : AppCompatActivity() , KodeinAware{

    override val kodein by kodein()

    lateinit var  viewModel : CityViewModel
    val viewModelFactory: CityViewModelFactory by instance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        setUpViewModel()

        setUpObserver()



    }

    private fun setUpObserver() {
        viewModel.titleBar.observe(this, Observer {
            supportActionBar?.title=it
        })

        viewModel.cityResponse.observe(this, Observer {
            when(it){
                is Resource.Error ->{
                    Timber.d("rajaOngkir: Is Error")
                    Toast.makeText(this,"Error Pak",Toast.LENGTH_LONG).show()
                }
                is Resource.Success->{
                    Timber.d("rajaOngkir : ${it.data}")
                }
                is Resource.Loading->{
                    Timber.d("rajaOngkir: Is Loading")
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpViewModel(){
        viewModel = ViewModelProvider(this,viewModelFactory).get(CityViewModel::class.java)
    }


}