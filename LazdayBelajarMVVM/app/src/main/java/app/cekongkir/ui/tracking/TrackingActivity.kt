package app.cekongkir.ui.tracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.databinding.ActivityTrackingBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TrackingActivity : AppCompatActivity(),KodeinAware{


    override val kodein by kodein()
    val binding by lazy { ActivityTrackingBinding.inflate(layoutInflater) }

    val trackingViewModelFactory : TrackingViewModelFactory by instance()
    lateinit var viewModel : TrackingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
        setUpView()
        setUpViewModel()
    }

    fun setUpView(){
        supportActionBar?.title="Lacak No Resi"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun setUpViewModel(){
        viewModel = ViewModelProvider(this,trackingViewModelFactory).get(TrackingViewModel::class.java)
    }
}