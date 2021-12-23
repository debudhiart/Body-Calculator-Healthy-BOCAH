package id.budhiarta.praktikumprogmob;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import id.budhiarta.praktikumprogmob.helper.DBHelper;

import static android.content.ContentValues.TAG;

public class Register extends AppCompatActivity {

    public static final String KIRIM_NAMA_DEPAN = "id.budhiarta.praktikumprogmob.NAMA_DEPAN";
    EditText etNamaDepan;
    EditText etNamaBelakang;
    EditText etEmail;
    EditText etPassword;

    RadioGroup rgGander;
    RadioButton rbGander;

    CheckBox cbPersyaratan;
    Integer textPersyaratan;

    SeekBar sbUmur;
    Integer angkaUmur;
    TextView tvAngkaUmur;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Daftar BOCAH");

        Log.i(TAG, "onCreate - Budhi");

        dbHelper = new DBHelper(this);

        tvAngkaUmur = (TextView)findViewById(R.id.tv_angka_umur);
        sbUmur = (SeekBar)findViewById(R.id.sb_umur);
        sbUmur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAngkaUmur.setText(String.valueOf(progress));
                angkaUmur = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button submit = (Button) findViewById(R.id.btn_daftar);
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                etNamaDepan = findViewById(R.id.et_namaDepan);
                String textNamaDepan = etNamaDepan.getText().toString();
                if (textNamaDepan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Depan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                etNamaBelakang = findViewById(R.id.et_namaBelakang);
                String textNamaBelakang = etNamaBelakang.getText().toString();
                if (textNamaBelakang.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Belakang tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                etEmail = findViewById(R.id.et_email);
                String textEmail = etEmail.getText().toString();
                if (textEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                etPassword = findViewById(R.id.et_password);
                String textPassword = etPassword.getText().toString();
                if (textPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                rgGander = (RadioGroup)findViewById(R.id.rg_gender);
                int genderId = rgGander.getCheckedRadioButtonId();
                rbGander =findViewById(genderId);

                cbPersyaratan = findViewById(R.id.cb_persyaratan);
                if (cbPersyaratan.isChecked()){
                    textPersyaratan = 1;
                }else{
                    textPersyaratan = 0;
                }

                if (angkaUmur == null){
                    angkaUmur = 18;
                }

                AlertDialog dialog = new AlertDialog.Builder(Register.this).setTitle("Konfirmasi Bioadata Diri")
                        .setMessage("Nama Depan:"+ textNamaDepan + "\n Nama Belakang:"+ textNamaBelakang +
                                "\n Email:"+ textEmail + "\n Password:"+ textPassword + "\n Jenis Kelamin: "
                                + rbGander.getText() + "\n Persyaratan:"+ textPersyaratan + "\n Umur:"+ angkaUmur)
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
                        values.put(DBHelper.nama_depan, etNamaDepan.getText().toString());
                        values.put(DBHelper.nama_belakang, etNamaBelakang.getText().toString());
                        values.put(DBHelper.email, etEmail.getText().toString());
                        values.put(DBHelper.password, etPassword.getText().toString());
                        values.put(DBHelper.jenis_kelamin, rbGander.getText().toString());
                        values.put(DBHelper.term_and_condition, textPersyaratan);
                        values.put(DBHelper.umur, angkaUmur);
                        dbHelper.insertData_tb_user(values);
//                        Toast.makeText(Register.this, "Data berhasil disimpan" + angkaUmur, Toast.LENGTH_SHORT).show();
                        Intent suksesDaftar = new Intent(Register.this, SuksesDaftar.class);
                        suksesDaftar.putExtra(KIRIM_NAMA_DEPAN, textNamaDepan);

                        startActivity(suksesDaftar);
                    }
                });
            }
        });

        TextView tvMasukSekarang = findViewById(R.id.tv_masuk_sekarang);
        tvMasukSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(Register.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart - Budhi");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "onPause- Budhi");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG,  "onResume - Budhi");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "onStop - Budhi");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy - Budhi");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "onRestart - Budhi");
    }

    //    public void chekGender(View v){
//        int genderId = rgGander.getCheckedRadioButtonId();
//        rbGander =findViewById(genderId);
//    }

}