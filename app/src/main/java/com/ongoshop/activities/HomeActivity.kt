package com.ongoshop.activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ongoshop.R
import com.ongoshop.fragments.*
import com.ongoshop.utils.others.Constants

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    var mContaxt: Context? = null
    var doubleBackToExitPressedOnce = false
    var bottomNavigationView: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mContaxt = this
        bottomNavigationView = findViewById<View>(R.id.navigationbar) as BottomNavigationView
        underlineMenuItem(bottomNavigationView!!.menu.getItem(0))
        bottomNavigationView!!.setOnNavigationItemSelectedListener(this as BottomNavigationView.OnNavigationItemSelectedListener)

        val fragmentManager = supportFragmentManager


        if (intent.extras != null) {
            if (intent.getStringExtra("type") == "my") {
                var fragment: Fragment? = null
                fragment = CategoriesFragment()
                loadFragment(fragment)
                bottomNavigationView!!.selectedItemId = R.id.categories
            }
        } else{


            var fragment: Fragment? = null
            fragment = HomeFragment()
            loadFragment(fragment)
        }


/*
        try {
            if (intent.getStringExtra("type") == "my") {
                var fragment: Fragment? = null
                fragment = CategoriesFragment()
                loadFragment(fragment)
                bottomNavigationView!!.selectedItemId = R.id.categories
            }
        } catch (e: Exception) {
            var fragment: Fragment? = null
            fragment = HomeFragment()
            loadFragment(fragment)
        }
*/
        // change to whichever id should be default

        /* final FragmentManager fragmentManager = getSupportFragmentManager();
    loadFragment(new HomeFragment());*/
    }

    /*  private void underlineMenuItem(MenuItem item) {
        SpannableString content = new SpannableString(item.getTitle());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        item.setTitle(content);

    }
    private void removeItemsUnderline(BottomNavigationView bottomNavigationView) {
        for (int i = 0; i <  bottomNavigationView.getMenu().size(); i++) {
            MenuItem item = bottomNavigationView.getMenu().getItem(i);
            item.setTitle(item.getTitle().toString());
        }
    }*/
    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        removeItemsUnderline(bottomNavigationView)
        underlineMenuItem(item)
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.home ->{
                Constants.currentFragment = "Home"
                fragment = HomeFragment()
            }
            R.id.categories -> {
                Constants.currentFragment = "Category"
                fragment = CategoriesFragment()
            }
            R.id.settings -> {
                Constants.currentFragment = "Settings"
                fragment = SettingsFragment()
            }
            R.id.profile -> {
                Constants.currentFragment = "Profile"
                fragment = ProfileFragment()
            }
        }
        return loadFragment(fragment)
    }

    override fun onResume() {
        super.onResume()

/*
        Constants.currentFragment.let {
            when(it){
                "Profile"->loadFragment(ProfileFragment())
                "Category" ->loadFragment(CategoriesFragment())
                "Settings" -> loadFragment(SettingsFragment())
                else -> loadFragment(HomeFragment())
            }
        }
*/

    }
    override fun onRestart() {
        super.onRestart()
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_container)
        if (currentFragment!!.javaClass == HomeFragment().javaClass) {
            if (doubleBackToExitPressedOnce) {
                finishAffinity()
                return
            }
            doubleBackToExitPressedOnce = true
            //  Snackbar.make(login_relative, "", BaseTransientBottomBar.LENGTH_SHORT).show();
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
        }
    }

    private fun removeItemsUnderline(bottomNavigationView: BottomNavigationView?) {
        for (i in 0 until bottomNavigationView!!.menu.size()) {
            val item = bottomNavigationView.menu.getItem(i)
            item.title = item.title.toString()
        }
    }

    private fun underlineMenuItem(item: MenuItem) {
        val content = SpannableString(item.title)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        item.title = content
    }
}