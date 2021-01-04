package com.ongoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    Context mContaxt;
    boolean doubleBackToExitPressedOnce = false;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContaxt=this;

         bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigationbar);

        underlineMenuItem(bottomNavigationView.getMenu().getItem(0));
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        final FragmentManager fragmentManager = getSupportFragmentManager();


        try {
            if(getIntent().getStringExtra("type").equals("my"))
            {
                Fragment fragment = null;
                fragment = new CategoriesFragment();
                loadFragment( fragment);
                bottomNavigationView.setSelectedItemId(R.id.categories);
            }
        }catch (Exception e)
        {
            Fragment fragment = null;
            fragment = new HomeFragment();
            loadFragment( fragment);
        }
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

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        removeItemsUnderline(bottomNavigationView);
        underlineMenuItem(item);
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.categories:
                fragment = new CategoriesFragment();
                break;

            case R.id.settings:
                fragment = new SettingsFragment();
                break;

            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (currentFragment.getClass().equals(new HomeFragment().getClass())) {
            if (doubleBackToExitPressedOnce) {

                finishAffinity();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            //  Snackbar.make(login_relative, "", BaseTransientBottomBar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
        else
        {
            super.onBackPressed();
        }


    }
    private void removeItemsUnderline(BottomNavigationView bottomNavigationView) {
        for (int i = 0; i <  bottomNavigationView.getMenu().size(); i++) {
            MenuItem item = bottomNavigationView.getMenu().getItem(i);
            item.setTitle(item.getTitle().toString());
        }
    }

    private void underlineMenuItem(MenuItem item) {
        SpannableString content = new SpannableString(item.getTitle());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        item.setTitle(content);

    }
}
