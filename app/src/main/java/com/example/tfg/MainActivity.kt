package com.example.tfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.databinding.ActivityMainBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private var bottom_navigation_view: BottomNavigationView? = null
    private var drawer_layout: DrawerLayout? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var nav_view: NavigationView
    var currentFilter: String = ""
    private lateinit var taskViewModel: TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val perfilFragment= supportFragmentManager.findFragmentByTag("ProfileFragment")


        taskViewModel.string.observe(this){
            val txtName =perfilFragment?.view?.findViewById<TextView>(R.id.name)
            txtName?.text = String.format("Perfil Name: %s", it)
        }


        val buttonExit: ImageButton = findViewById(R.id.imgbtnSalir)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        nav_view = findViewById<NavigationView>(R.id.nav_view)
        drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0)
        drawer_layout?.addDrawerListener(toggle)
        toggle.syncState()

        toolbar.setNavigationOnClickListener {
            drawer_layout?.openDrawer(GravityCompat.START)
        }

        actionBarDrawerToggle = toggle

        nav_view.setNavigationItemSelectedListener(this)

        buttonExit.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, LoginFragment()).commit()
        }

        bottom_navigation_view = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottom_navigation_view?.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    goToFragment(HomeFragment())
                    true
                }
                R.id.action_cart -> {
                    goToFragment(CartFragment())
                    true
                }
                R.id.action_profile -> {
                    goToFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        bottom_navigation_view?.selectedItemId = R.id.action_home
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, LoginFragment(),"InicioFragment").commit()
    }



    fun getDrawerToggle(): ActionBarDrawerToggle? {
        return actionBarDrawerToggle
    }


    fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val HomeFragment = supportFragmentManager.findFragmentByTag("HomeFragment") as HomeFragment?
        when (item.itemId) {
            R.id.filt_todo -> {
                currentFilter = ""
                HomeFragment?.applyFilter(currentFilter)
                Toast.makeText(this, "All components", Toast.LENGTH_SHORT).show()
            }
            R.id.filt_comp -> {
                currentFilter = "Component"
                HomeFragment?.applyFilter(currentFilter)
                Toast.makeText(this, "Searching $currentFilter", Toast.LENGTH_SHORT).show()
            }
            R.id.filt_pc -> {
                currentFilter = "Computer"
                HomeFragment?.applyFilter(currentFilter)
                Toast.makeText(this, "Searching $currentFilter", Toast.LENGTH_SHORT).show()
            }
            R.id.filt_movil -> {
                currentFilter = "Phone"
                HomeFragment?.applyFilter(currentFilter)
                Toast.makeText(this, "Searching $currentFilter", Toast.LENGTH_SHORT).show()
            }
            R.id.filt_elec -> {
                currentFilter = "Appliance"
                HomeFragment?.applyFilter(currentFilter)
                Toast.makeText(this, "Searching $currentFilter", Toast.LENGTH_SHORT).show()
            }
            R.id.filt_comple -> {
                currentFilter = "Component"
                HomeFragment?.applyFilter(currentFilter)
                Toast.makeText(this, "Searching $currentFilter", Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }



}
