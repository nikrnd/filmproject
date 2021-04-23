package com.nicolasdelton.films;

import android.app.ActionBar;
import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        /**
         * Per mettere lo sfondo a schermo intero
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        twoClickListeners();

    }

    private void twoClickListeners () {
        View noleggioView = findViewById(R.id.navigation_noleggio);
        ConstraintLayout layout = findViewById(R.id.login);
        Button admin = findViewById(R.id.adminBut);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noleggioView.setActivated(true);
                layout.setVisibility(View.GONE);
            }
        });
        Button guest = findViewById(R.id.guestBut);
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noleggioView.setActivated(false);
                layout.setVisibility(View.GONE);
            }
        });

    }

}