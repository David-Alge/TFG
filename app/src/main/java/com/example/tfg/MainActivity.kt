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
    var bottom_navigation_view: BottomNavigationView? = null
    private var drawer_layout: DrawerLayout? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var binding: ActivityMainBinding


    private lateinit var taskViewModel: TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val perfilFragment= supportFragmentManager.findFragmentByTag("ProfileFragment")


        taskViewModel.name.observe(this){
            val txtName =perfilFragment?.view?.findViewById<TextView>(R.id.name)
            txtName?.text = String.format("Perfil Name: %s", it)
        }


        val buttonExit: ImageButton = findViewById(R.id.imgbtnSalir)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0)
        drawer_layout?.addDrawerListener(toggle)
        toggle.syncState()
        actionBarDrawerToggle = toggle


        nav_view.setNavigationItemSelectedListener(this)

        buttonExit.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, InicioFragment()).commit()
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, InicioFragment()).commit()
    }
    fun getDrawerToggle(): ActionBarDrawerToggle? {
        return actionBarDrawerToggle
    }


    fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filt_comp -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.filt_pc -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.filt_movil -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.filt_elec -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }

}