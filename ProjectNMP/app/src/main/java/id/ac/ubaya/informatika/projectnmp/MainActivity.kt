package id.ac.ubaya.informatika.projectnmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var fragments:ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(HomeFragment())
        fragments.add(CartFragment())
        fragments.add(OrderHistoryFragment())
        fragments.add(ProfileFragment())
        viewPager.adapter = Adapter(this, fragments)

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> bottomNav.selectedItemId = R.id.itemHome
                    1 -> bottomNav.selectedItemId = R.id.itemCart
                    2 -> bottomNav.selectedItemId = R.id.itemHistory
                    3 -> bottomNav.selectedItemId = R.id.itemProfile
                }

            }
        })

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.itemHome -> viewPager.currentItem = 0
                R.id.itemCart -> viewPager.currentItem = 1
                R.id.itemHistory -> viewPager.currentItem = 2
                R.id.itemProfile -> viewPager.currentItem = 3
            }
            true
        }
    }
}