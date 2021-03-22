package com.rahma.pengaduanmasyarakat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rahma.pengaduanmasyarakat.fragment.BerandaPetugasFragment;
import com.rahma.pengaduanmasyarakat.fragment.HomeFragment;
import com.rahma.pengaduanmasyarakat.fragment.ProfilPetugasFragment;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

public class PetugasActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas);
        sharedPrefManager = new SharedPrefManager(this);

        BottomNavigationView bottomNav = findViewById(R.id.navigasiP);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_wadahP, new BerandaPetugasFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_homeP:
                    selectedFragment = new BerandaPetugasFragment();
                    break;
                case R.id.nav_profilP:
                    selectedFragment = new ProfilPetugasFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_wadahP,selectedFragment).commit();
            return true;
        }
    };
}
