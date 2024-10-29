package com.example.eventadminapp1;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationHelper {

    private final Context context;
    private final FragmentManager fragmentManager;
    private final DrawerLayout drawerLayout;

    public NavigationHelper(Context context, FragmentManager fragmentManager, DrawerLayout drawerLayout) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.drawerLayout = drawerLayout;
    }

    public void setupNavigation(Toolbar toolbar, NavigationView navigationView) {
        // Set up ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                (MainActivity) context, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up default fragment
        if (((MainActivity) context).getIntent().getExtras() == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new ViewUsersFragment())  // Replace with your default fragment
                    .commit();
            navigationView.setCheckedItem(R.id.nav_view_users);
        }

        // Set navigation item selection listener
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        if (item.getItemId() == R.id.nav_view_users) {
            selectedFragment = new ViewUsersFragment();
        } else if (item.getItemId() == R.id.nav_add_event) {
            selectedFragment = new AddEventFragment();
        } else if (item.getItemId() == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            context.startActivity(new Intent(context, LoginActivity.class));
            ((MainActivity) context).finish();
            return true;
        }

        if (selectedFragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
