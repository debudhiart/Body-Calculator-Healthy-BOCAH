package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.btn_dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_dashboard:
                        return true;
                    case R.id.btn_dailybook:
                        startActivity(new Intent(getApplicationContext(), DailyBook.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Button btnTambahSarapan = findViewById(R.id.btn_tambah_sarapan);
        btnTambahSarapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent halamanTambahSarapan = new Intent(Dashboard.this, TambahMakanan.class);
                startActivity(halamanTambahSarapan);
            }
        });
        Button btnTambahMakanSiang = findViewById(R.id.btn_tambah_makan_siang);
        btnTambahSarapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent halamanTambahMakanSiang = new Intent(Dashboard.this, TambahMakanan.class);
                startActivity(halamanTambahMakanSiang);
            }
        });
        Button btnTambahMakanMalam = findViewById(R.id.btn_tambah_makan_malam);
        btnTambahSarapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent halamanTambahMakanMalam = new Intent(Dashboard.this, TambahMakanan.class);
                startActivity(halamanTambahMakanMalam);
            }
        });
    }
}