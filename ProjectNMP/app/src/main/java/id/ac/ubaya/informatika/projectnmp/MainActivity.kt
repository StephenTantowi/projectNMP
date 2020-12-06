package id.ac.ubaya.informatika.projectnmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    var fragments:ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)


        val hView = navView.getHeaderView(0)
        val textViewName = hView.findViewById(R.id.txtNamaUser) as TextView
        val textViewEmail = hView.findViewById(R.id.txtEmailUser) as TextView
        textViewEmail.text = Global.users[0].email
        textViewName.text = Global.users[0].nama


        setSupportActionBar(toolbar)
        supportActionBar?.title = "            NMP Online Shop"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        var drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name,
                R.string.app_name)

        drawerToggle.isDrawerIndicatorEnabled = true

        drawerToggle.syncState()


        fragments.add(HomeFragment())
        fragments.add(CartFragment())
        fragments.add(OrderHistoryFragment())
        fragments.add(ProfileFragment())
        viewPager.adapter = Adapter(this, fragments)

        navView.setNavigationItemSelectedListener {


            when(it.itemId) {
                R.id.itemHome -> viewPager.currentItem = 0
                R.id.itemCart -> viewPager.currentItem = 1
                R.id.itemHistory -> viewPager.currentItem = 2
                R.id.itemProfile -> viewPager.currentItem = 3
                R.id.itemSignOut -> Logout()

            }
            drawerLayout.closeDrawer(GravityCompat.START)

            true

        }



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

    fun Logout()
    {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        Global.users.clear()
    }
}