
package app.cekongkir.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.databinding.ActivityHomeBinding
import app.cekongkir.ui.cost.CostViewModel
import app.cekongkir.ui.cost.CostViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity() , KodeinAware{

    override val kodein by kodein()

    private val binding by lazy{ ActivityHomeBinding.inflate(layoutInflater)}


    val costViewModelFactory : CostViewModelFactory by instance()
    lateinit var costViewModel : CostViewModel

    val tablayout by lazy { binding.tabLayout }
    val viewpager by lazy { binding.viewPager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpTab()

        setUpViewModel()

    }

    private fun setUpViewModel() {
        costViewModel = ViewModelProvider(this,costViewModelFactory).get(CostViewModel::class.java)
    }

    private fun setUpTab() {
        val tabTitles = arrayOf("Cek Ongkir", "Cek Resi")
        val tabAdapter = HomeTabAdapter(supportFragmentManager, lifecycle)

        viewpager.adapter = tabAdapter
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }

}