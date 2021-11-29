package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;

public class EditMakanan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_makanan);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.btn_profile);

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

        Intent intent = getIntent();
        Model_tb_makanan makananModel = intent.getParcelableExtra("dataMakanan");

        int kalori = makananModel.getKalori();
        int protein = makananModel.getProtein();
        int lemak = makananModel.getLemak();
        String satuan = makananModel.getSatuan();
        String namaMakanan = makananModel.getNama_makanan();

        EditText etNamaMakanan = findViewById(R.id.et_edit_nama_makanan);
        etNamaMakanan.setText(namaMakanan);

        EditText etSatuan = findViewById(R.id.et_edit_satuan);
        etSatuan.setText(satuan);

        EditText etKalori = findViewById(R.id.et_edit_kalori);
        etKalori.setText(String.valueOf(kalori));

        EditText etLemak = findViewById(R.id.et_edit_lemak);
        etLemak.setText(String.valueOf(lemak));

        EditText etProtein = findViewById(R.id.et_edit_protein);
        etProtein.setText(String.valueOf(protein));
    }
}