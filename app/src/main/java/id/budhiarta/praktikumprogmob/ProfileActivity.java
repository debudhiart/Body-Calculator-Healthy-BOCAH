package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_user;

public class ProfileActivity extends AppCompatActivity {
    UserSession userSession;
    DBHelper dbHelper;
    Model_tb_user userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int userID = this.getSharedPreferences("pref_name", 0).getInt("key_id", 0);
        dbHelper = new DBHelper(this);

        try {
            Cursor cursor = (Cursor) dbHelper.getDataUser(userID);
            cursor.moveToLast();
            userModel=new Model_tb_user(
                    cursor.getInt(cursor.getColumnIndex(DBHelper.user_id)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.umur)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.term_and_condition)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.password)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.jenis_kelamin)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.email)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.nama_belakang)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.nama_depan))
            );
        }catch (Exception e){
            Log.e("error user", "Error:" + e.getMessage());
            return;
        }

//        Toast.makeText(getApplicationContext(), "userModel:" + userModel.getNama_depan(), Toast.LENGTH_SHORT).show();

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Akun");

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

        TextView tvNamaBelakangUser = findViewById(R.id.tv_id_nama_belakang_user);
        tvNamaBelakangUser.setText(userModel.getNama_belakang());

        TextView tvNamaDepanUser = findViewById(R.id.tv_id_nama_depan_user);
        tvNamaDepanUser.setText(userModel.getNama_depan());

        TextView tvViewJenisKelamin = findViewById(R.id.tv_id_view_jenis_kelamin);
        tvViewJenisKelamin.setText(userModel.getJenis_kelamin());

        TextView tvViewUmur = findViewById(R.id.tv_id_view_umur);
        tvViewUmur.setText(Integer.toString(userModel.getUmur()));

        TextView tvViewEmail = findViewById(R.id.tv_id_view_email);
        tvViewEmail.setText(userModel.getEmail());


        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ProfileActivity.this).setTitle("Konfirmasi Logout")
                        .setMessage("Yakin ingin keluar?")
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
                         userSession = new UserSession();
                         userSession.logout(ProfileActivity.this);
                         Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                         startActivity(intent);
                         finish();
                    }
                });
            }


        });

        FloatingActionButton fabTambahDataMakanan = findViewById(R.id.fab_edit_user);
        fabTambahDataMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewTambahMakanan = new Intent(ProfileActivity.this, EditUserActivity.class);
                viewTambahMakanan.putExtra("dataUser", (Parcelable) userModel);
                startActivity(viewTambahMakanan);
            }
        });

    }
}