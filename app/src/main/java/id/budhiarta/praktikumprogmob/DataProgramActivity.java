package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_program;

public class DataProgramActivity extends AppCompatActivity{

    Intent intent;
    int intAge;
    String textGender, textHeight, textWeight;
    int intHeight, intWeight;
    RadioGroup rgProgramGander;
    RadioButton rbProgramGander;
    private Model_tb_program programModel;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_program);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Tambah Program");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.btn_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_dailybook:
                        return true;
                    case R.id.btn_dashboard:
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
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

        db=new DBHelper(this);
        intent = getIntent();
        intAge = Integer.parseInt(intent.getStringExtra("data_age"));
        textGender = intent.getStringExtra("data_gender");
        textHeight = intent.getStringExtra("data_height");
        textWeight = intent.getStringExtra("data_weight");

        intWeight = Integer.parseInt(textHeight);
        intHeight = Integer.parseInt(textWeight);
        Toast.makeText(this, "umur: " +intAge + "gender: " + textGender + "tinggi: " + intHeight + "beratbadan: " + intWeight, Toast.LENGTH_SHORT).show();

        if (textGender.equals("Laki-laki")){
            RadioButton rbLakiLaki = findViewById(R.id.rb_program_laki_laki);
            rbLakiLaki.setChecked(true);
        }else{
            RadioButton rbPerempuan = findViewById(R.id.rb_program_perempuan);
            rbPerempuan.setChecked(true);
        }

        EditText etTinggiBadan = findViewById(R.id.et_program_tinggi_badan);
        etTinggiBadan.setText(String.valueOf(intHeight));

        EditText etBeratBadan = findViewById(R.id.et_program_berat_badan);
        etBeratBadan.setText(String.valueOf(intWeight));

        EditText etUmur = findViewById(R.id.et_program_umur);
        etUmur.setText(String.valueOf(intAge));

        Button btnSimpanProgram = findViewById(R.id.btn_simpan_program);
        btnSimpanProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgProgramGander = (RadioGroup)findViewById(R.id.rg_program_gender);
                int genderId = rgProgramGander.getCheckedRadioButtonId();
                rbProgramGander =findViewById(genderId);
                int angkaTargetKalori;

                String angkaTinggiBadan = etTinggiBadan.getText().toString();

                String angkaBeratBadan = etBeratBadan.getText().toString();
                String angkaUmur = etUmur.getText().toString();

                Spinner spAktifitas = (Spinner) findViewById(R.id.sp_program_aktifitas_tubuh);
                String textAktifitas = spAktifitas.getSelectedItem().toString();

                Spinner spProgram = (Spinner) findViewById(R.id.sp_program_program);
                String textProgram = spProgram.getSelectedItem().toString();

                double angkaAktifitas;
                if (textAktifitas.equals("Tidak Aktif (Tidak Olahraga)")){
                    angkaAktifitas = 1.2;
                }else if (textAktifitas.equals("Agak Aktif (1-2 X Olahraga)")){
                    angkaAktifitas = 1.375;
                }else if (textAktifitas.equals("Sering Aktif (3-4 X Olahraga)")){
                    angkaAktifitas = 1.725;
                }else{
                    angkaAktifitas = 1.9;
                }

            if(rbProgramGander.getText().equals("Laki-laki")){
                angkaTargetKalori=(int)Math.round(66+(13.7*Integer.parseInt(angkaBeratBadan))+(5*Integer.parseInt(angkaTinggiBadan))-(6.8*Integer.parseInt(angkaUmur))*angkaAktifitas);
            }else{
                angkaTargetKalori=(int)Math.round(65.5+(9.6*Integer.parseInt(angkaBeratBadan))+(1.8*Integer.parseInt(angkaTinggiBadan))-(4.7*Integer.parseInt(angkaUmur))*angkaAktifitas);
            }
//                Pria :
//                66+(13,7xberat)+(5xtinggi)-(6,8xumur) =   *a
//
//                Wanita :
//                655+(9,6xberat)+(1,8xtinggi)-(4,7xumur)= *a


                AlertDialog dialog = new AlertDialog.Builder(DataProgramActivity.this).setTitle("Konfirmasi Program")
                        .setMessage("Jenis Kelamin: " + rbProgramGander.getText() + "\n Tinggi Badan:"+ Integer.parseInt(angkaTinggiBadan) +
                                "\n Berat Badan:"+ Integer.parseInt(angkaBeratBadan) + "\n Umur:"+ Integer.parseInt(angkaUmur) + "\n Aktifitas: "
                                + textAktifitas + "\n Program:"+ textProgram)
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
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Makassar"));
                        String tgl_hari_ini=dtf.format(now);
                        programModel=new Model_tb_program(Integer.parseInt(angkaUmur),
                                Integer.parseInt(angkaBeratBadan),
                                Integer.parseInt(angkaTinggiBadan),
                                angkaTargetKalori,
                                tgl_hari_ini,
                                textAktifitas,
                                textProgram
                        );
                        db.insertProgramData(programModel);
                        Toast.makeText(getApplicationContext(), "Ini Lanjut Buat Perhitungan Kalorinya", Toast.LENGTH_SHORT).show();
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