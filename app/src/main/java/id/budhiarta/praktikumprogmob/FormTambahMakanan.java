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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.budhiarta.praktikumprogmob.helper.DBHelper;

public class FormTambahMakanan extends AppCompatActivity {

//    public static final String KIRIM_NAMA_DEPAN = "id.budhiarta.praktikumprogmob.NAMA_DEPAN";
    EditText etInputNamaMakanan;
    EditText etInputSatuan;
    EditText etInputKalori;
    EditText etInputProtein;
    EditText etInputLemak;

    ImageButton imgBtnBack;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tambah_makanan);

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
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Button btnSubmitMakanan = (Button) findViewById(R.id.btn_submit_makanan);
        btnSubmitMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etInputNamaMakanan = findViewById(R.id.et_input_nama_makanan);
                String textNamaMakanan = etInputNamaMakanan.getText().toString();
                if (textNamaMakanan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Makanan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                etInputSatuan = findViewById(R.id.et_input_satuan);
                String textSatuan = etInputSatuan.getText().toString();
                if (textSatuan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Satuan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                etInputKalori = findViewById(R.id.et_input_kalori);
                String angkaKalori = etInputKalori.getText().toString();
                if (angkaKalori.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nilai kalori tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                etInputProtein = findViewById(R.id.et_input_protein);
                String angkaProtein = etInputProtein.getText().toString();
                if (angkaProtein.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nilai protein tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                etInputLemak = findViewById(R.id.et_input_lemak);
                String angkaLemak = etInputLemak.getText().toString();
                if (angkaLemak.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nilai lemak tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog dialog = new AlertDialog.Builder(FormTambahMakanan.this).setTitle("Konfirmasi Data Makanan")
                        .setMessage("Satuan:"+ textSatuan + "\n Kalori:"+ angkaKalori + "\n Protein:"+ angkaProtein + "\n Lemak:"+ angkaLemak)
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
                        values.put(DBHelper.nama_makanan, textNamaMakanan);
                        values.put(DBHelper.satuan, textSatuan);
                        values.put(DBHelper.kalori, angkaKalori);
                        values.put(DBHelper.protein, angkaProtein);
                        values.put(DBHelper.lemak, angkaLemak);
                        dbHelper.insertData_tb_makanan(values);
                        Toast.makeText(FormTambahMakanan.this, "Data berhasil disimpan " + textNamaMakanan, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(FormTambahMakanan.this, TambahMakanan.class);
//                        suksesDaftar.putExtra(KIRIM_NAMA_DEPAN, textNamaDepan);

                        startActivity(intent);
                    }
                });
            }
        });

    }
}