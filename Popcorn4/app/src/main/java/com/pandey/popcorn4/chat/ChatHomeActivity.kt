package com.pandey.popcorn4.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pandey.popcorn4.R
import com.pandey.popcorn4.chat.ui.AccountFragment
import com.pandey.popcorn4.chat.ui.PeopleFragment
import kotlinx.android.synthetic.main.activity_chat_home.*

class ChatHomeActivity : AppCompatActivity(), AccountFragment.AccountFragmentListener, PeopleFragment.PeopleFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_home)

        startFragment(PeopleFragment.newInstance())

        nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_my_account -> {
                    startFragment(AccountFragment.newInstance())
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.navigation_people -> {
                    startFragment(PeopleFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

//        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    private fun startFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment, fragment::class.java.simpleName)
//        ft.addToBackStack(null)
        ft.commit()
    }

}
