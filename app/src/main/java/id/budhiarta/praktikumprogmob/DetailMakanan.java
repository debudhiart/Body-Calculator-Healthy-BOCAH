package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;

public class DetailMakanan extends AppCompatActivity {
    private DBHelper dbHelper;
    private Model_tb_makanan makananModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Detail Makanan");

        dbHelper = new DBHelper(this);

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
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Intent intent = getIntent();
        makananModel= intent.getParcelableExtra("detailMakanan");

        int kalori = makananModel.getKalori();
        int protein = makananModel.getProtein();
        int lemak = makananModel.getLemak();
        String satuan = makananModel.getSatuan();
        String namaMakanan = makananModel.getNama_makanan();

        TextView tvNamaMakanan = findViewById(R.id.tv_id_view_nama_makanan);
        tvNamaMakanan.setText(namaMakanan);

        TextView tvSatuan = findViewById(R.id.tv_id_view_satuan_saji);
        tvSatuan.setText(satuan);

        TextView tvKalori = findViewById(R.id.tv_id_view_jumlah_kalori);
        tvKalori.setText(String.valueOf(kalori));

        TextView tvLemak = findViewById(R.id.tv_id_view_jumlah_lemak);
        tvLemak.setText(String.valueOf(lemak));

        TextView tvProtein = findViewById(R.id.tv_id_view_jumlah_protein);
        tvProtein.setText(String.valueOf(protein));



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        else if(item.getItemId()==R.id.btn_submit_makan){
            int id_shift=getIntent().getIntExtra("shift_makan_id",0);
////            if(id_shift==2131296360){
////                values.put(DBHelper.SHIFT_MAKAN_ID,1);
////            }
            dbHelper.insertMakananToDetailShift(id_shift,makananModel);
            Intent intent= new Intent(getApplicationContext(),Dashboard.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_makanan, menu);
        return super.onCreateOptionsMenu(menu);
    }
}