package com.nicolasdelton.films;

import android.app.ActionBar;
import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nicolasdelton.films.ui.FilmFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setSelectedItemId(R.id.navigation_film);
        layout = findViewById(R.id.login);

        /**
         * Per mettere lo sfondo a schermo intero
         */
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        twoClickListeners();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_film:
                        navController.navigate(R.id.navigation_film);
                        break;

                    case R.id.navigation_noleggio:
                        navController.navigate(R.id.navigation_noleggio);
                        break;

                    case R.id.navigation_resoconto:
                        navController.navigate(R.id.navigation_resoconto);
                        break;

                    case R.id.navigation_logout:
                        layout.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });

    }

    public static boolean isAdmin;
    /**
     * Thread in ascolto su pressione pulsanti
     */
    private void twoClickListeners () {
        Button admin = findViewById(R.id.adminBut);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navView.setSelectedItemId(R.id.navigation_film);

                navView.getMenu()
                        .findItem(R.id.navigation_resoconto).setVisible(true);
                navView.getMenu()
                        .findItem(R.id.navigation_noleggio).setVisible(true);
                navView.getMenu()
                        .findItem(R.id.navigation_film).setVisible(true);

                isAdmin = true;

                layout.setVisibility(View.GONE);
            }
        });

        Button guest = findViewById(R.id.guestBut);
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navView.setSelectedItemId(R.id.navigation_resoconto);

                navView.getMenu()
                        .findItem(R.id.navigation_resoconto).setVisible(true);
                navView.getMenu()
                        .findItem(R.id.navigation_noleggio).setVisible(false);
                navView.getMenu()
                        .findItem(R.id.navigation_film).setVisible(false);

                isAdmin = false;

                layout.setVisibility(View.GONE);
            }
        });

    }

}