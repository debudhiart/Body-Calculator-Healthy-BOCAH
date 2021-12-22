package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditMakanan extends AppCompatActivity {

    private DBHelper dbHelper;

    private MakananAPI makananAPI;
    private Call<Model_tb_makanan> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_makanan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Update Data");

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
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        Model_tb_makanan makananModel = intent.getParcelableExtra("dataMakanan");

        int idMakanan = makananModel.getMakanan_id();
        int kalori = makananModel.getKalori();
        int protein = makananModel.getProtein();
        int lemak = makananModel.getLemak();
        String satuan = makananModel.getSatuan();
        String namaMakanan = makananModel.getNama_makanan();

        EditText etEditNamaMakanan = findViewById(R.id.et_edit_nama_makanan);
        etEditNamaMakanan.setText(namaMakanan);

        EditText etEditSatuan = findViewById(R.id.et_edit_satuan);
        etEditSatuan.setText(satuan);

        EditText etEditKalori = findViewById(R.id.et_edit_kalori);
        etEditKalori.setText(String.valueOf(kalori));

        EditText etEditLemak = findViewById(R.id.et_edit_lemak);
        etEditLemak.setText(String.valueOf(lemak));

        EditText etEditProtein = findViewById(R.id.et_edit_protein);
        etEditProtein.setText(String.valueOf(protein));

        Button btnEditMakanan = findViewById(R.id.btn_edit_makanan);
        btnEditMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringNamaMakanan = etEditNamaMakanan.getText().toString();
                if (stringNamaMakanan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Makanan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String stringSatuan = etEditSatuan.getText().toString();
                if (stringSatuan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Satuan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String integerKalori = etEditKalori.getText().toString();
                if (integerKalori.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nilai kalori tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String integerProtein = etEditProtein.getText().toString();
                if (integerProtein.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nilai protein tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String integerLemak = etEditLemak.getText().toString();
                if (integerLemak.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nilai lemak tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog dialog = new AlertDialog.Builder(EditMakanan.this).setTitle("Konfirmasi Data Makanan")
                        .setMessage("Nama Makanan:"+ stringNamaMakanan +"\nSatuan:"+ stringSatuan + "\n Kalori:"+ integerKalori + "\n Protein:"+ integerProtein + "\n Lemak:"+ integerLemak)
                        .setPositiveButton("Lanjutkan", null)
                        .setNegativeButton("Kembali", null)
                        .show();

                Button btn_kembali = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                btn_kembali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btn_lanjutkan = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btn_lanjutkan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ContentValues values = new ContentValues();
                        values.put(DBHelper.nama_makanan, stringNamaMakanan);
                        values.put(DBHelper.satuan, stringSatuan);
                        values.put(DBHelper.kalori, Integer.parseInt(integerKalori));
                        values.put(DBHelper.protein, Integer.parseInt(integerProtein));
                        values.put(DBHelper.lemak, Integer.parseInt(integerLemak));
//                        Toast.makeText(getApplicationContext(), "ID" +stringNamaMakanan, Toast.LENGTH_SHORT).show();
                        dbHelper.updateData_tb_makanan(values, makananModel.getMakanan_id());
                        
                        //Ini masih ERROR gak bisa edit API
                        editAPIMakanan(idMakanan, stringNamaMakanan, stringSatuan, integerKalori, integerProtein, integerLemak);

                        Intent intent = new Intent(EditMakanan.this, Dashboard.class);

                        startActivity(intent);
                        finish();

                    }
                });
            }
        });


    }

    private void editAPIMakanan(int idMakanan, String stringNamaMakanan, String stringSatuan, String integerKalori, String integerProtein, String integerLemak) {
        Retrofit retrofit= new Retrofit.Builder().baseUrl("http://192.168.1.3:8000/api/").addConverterFactory(GsonConverterFactory.create()).build();
        makananAPI = retrofit.create(MakananAPI.class);
        makananAPI.updatMakananAPI(idMakanan, stringNamaMakanan, stringSatuan, Integer.parseInt(integerKalori), Integer.parseInt(integerProtein), Integer.parseInt(integerLemak)).enqueue(new Callback<Model_tb_makanan>() {
            @Override
            public void onResponse(Call<Model_tb_makanan> call, Response<Model_tb_makanan> response) {
                Toast.makeText(getApplicationContext(), "Berhasil Edit Makanan API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Model_tb_makanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Bangsat Edit", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}