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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;
import id.budhiarta.praktikumprogmob.model.Model_tb_user;

public class EditUserActivity extends AppCompatActivity {

    DBHelper dbHelper;
    RadioGroup rgEditGander;
    RadioButton rbEditGander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Edit Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        Model_tb_user userModel = intent.getParcelableExtra("dataUser");

        int idUser = userModel.getUser_id();
        int angkaTermAndCondition = userModel.getTerm_and_condition();
        int angkaUmur = userModel.getUmur();
        String textNamaDepan = userModel.getNama_depan();
        String textNamaBelakang = userModel.getNama_belakang();
        String textGender = userModel.getJenis_kelamin();
        String textEmail = userModel.getEmail();
        String textPassword = userModel.getPassword();

        EditText etNamaDepan = findViewById(R.id.et_edit_nama_depan_user);
        etNamaDepan.setText(textNamaDepan);

        EditText etNamaBelakang = findViewById(R.id.et_edit_nama_belakang_user);
        etNamaBelakang.setText(textNamaBelakang);

        EditText etEmail = findViewById(R.id.et_edit_email);
        etEmail.setText(String.valueOf(textEmail));

        EditText etUmur = findViewById(R.id.et_edit_umur);
        etUmur.setText(String.valueOf(angkaUmur));

        EditText etPassword = findViewById(R.id.et_edit_password);
        etPassword.setText(String.valueOf(textPassword));

        if (textGender.equals("Laki-laki")){
            RadioButton rbEditLakiLaki = findViewById(R.id.rb_edit_laki_laki);
            rbEditLakiLaki.setChecked(true);
        }else{
            RadioButton rbEditPerempuan = findViewById(R.id.rb_edit_perempuan);
            rbEditPerempuan.setChecked(true);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.btn_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_profile:
                        return true;
                    case R.id.btn_dailybook:
                        startActivity(new Intent(getApplicationContext(), DailyBook.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn_dashboard:
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Button btnSimpanEdit = findViewById(R.id.btn_simpan_edit);
        btnSimpanEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringNamaDepan = etNamaDepan.getText().toString();
                if (stringNamaDepan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Depan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String stringNamaBelakang = etNamaBelakang.getText().toString();
                if (stringNamaBelakang.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Belakang tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String stringEmail = etEmail.getText().toString();
                if (stringEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String integerUmur = etUmur.getText().toString();
                if (integerUmur.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Umur tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                rgEditGander = (RadioGroup)findViewById(R.id.rg_edit_gender);
                int genderId = rgEditGander.getCheckedRadioButtonId();
                rbEditGander =findViewById(genderId);

                AlertDialog dialog = new AlertDialog.Builder(EditUserActivity.this).setTitle("Konfirmasi Bioadata Diri")
                        .setMessage("Nama Depan:"+ stringNamaDepan + "\n Nama Belakang:"+ stringNamaBelakang +
                                "\n Email:"+ stringEmail + "\n Umur:"+ integerUmur + "\n Jenis Kelamin: "
                                + rbEditGander.getText())
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
                        ContentValues values =new ContentValues();
                        values.put(DBHelper.nama_depan, stringNamaDepan);
                        values.put(DBHelper.nama_belakang, stringNamaBelakang);
                        values.put(DBHelper.email, stringEmail);
                        values.put(DBHelper.umur, integerUmur);
                        values.put(DBHelper.jenis_kelamin, rbEditGander.getText().toString());
                        dbHelper.updateData_tb_user(values, idUser);
                        Toast.makeText(EditUserActivity.this, "Data berhasil disimpan" + angkaUmur, Toast.LENGTH_SHORT).show();
                        Intent suksesDaftar = new Intent(EditUserActivity.this, ProfileActivity.class);
//                        suksesDaftar.putExtra(KIRIM_NAMA_DEPAN, textNamaDepan);

                        startActivity(suksesDaftar);
                    }
                });

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