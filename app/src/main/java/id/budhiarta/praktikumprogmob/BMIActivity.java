package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BMIActivity extends AppCompatActivity {

    android.widget.Button btnReCalculateBMI, btnAturProgram;
//    android.widget.Button btn_save_BMI;
//    android.widget.Button btn_program_diet_BMI;
//    android.widget.Button btn_program_gain_BMI;
//    android.widget.Button btn_program_maintenance_BMI;

    TextView mbmidisplay, mbmicategory, mgender;
    Intent intent;
    ImageView mimageview;
    String mbmi, textGender;
    float intbmi;

    String height;
    String weight, textAge;
    Integer intAge;
    float intheight, intweight;
//    RelativeLayout mbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i);

//        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setTitle("Values Body Mass Index");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        androidx.appcompat.app.ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Values Body Mass Index");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.btn_dailybook);

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
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        intent = getIntent();


        mbmidisplay = findViewById(R.id.bmidisplay);
        mbmicategory = findViewById(R.id.bmicategory);
//        mgender = findViewById(R.id.genderdisplay);
//        mbackground = findViewById(R.id.contentlayout);
        mimageview = findViewById(R.id.imageview);
        btnReCalculateBMI = findViewById(R.id.recalculateBMI);

        textAge = intent.getStringExtra("age");
        intAge = Integer.valueOf(textAge);

        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");

        intheight = Float.parseFloat(height);
        intweight = Float.parseFloat(weight);

        intheight = intheight /100;
        intbmi = intweight/ (intheight * intheight);
        mbmi = Float.toString(intbmi);

        textGender = intent.getStringExtra("gender");

//        mgender.setText(textGender);
//        Toast.makeText(this, "kamu"+textGender, Toast.LENGTH_SHORT).show();

        if (textGender.equals("Laki-laki")){
            if (intbmi <18.5){
//                mbmicategory.setText("Terlalu kurus");
//                mbackground.setBackgroundColor(Color.RED);
                mimageview.setImageResource(R.drawable.ic_gambar_orang_skinny);
            }else if (intbmi<25 && intbmi > 18.4){
//                mbmicategory.setText("Berat Badan Normal");
//                mbackground.setBackgroundColor(Color.YELLOW);
                mimageview.setImageResource(R.drawable.ic_gambar_orang_fit);
            }else if (intbmi<29.4 && intbmi > 25){
//                mbmicategory.setText("Berat Badan Berlebih");
//                mbackground.setBackgroundColor(Color.RED);
                mimageview.setImageResource(R.drawable.ic_gambar_orang_obes);
            }else{
//                mbmicategory.setText("Obesitas Class 1");
//                mbackground.setBackgroundColor(Color.RED);
                mimageview.setImageResource(R.drawable.ic_gambar_orang_obes);
            }
        }else{
            if (intbmi <18.5){
                mimageview.setImageResource(R.drawable.ic_gambar_orang_fme_skinny);
            }else if (intbmi<25 && intbmi > 18.4){
                mimageview.setImageResource(R.drawable.ic_gambar_orang_fme_fit);
            }else if (intbmi<29.4 && intbmi > 25){
                mimageview.setImageResource(R.drawable.ic_gambar_orang_fme_obes);
            }else{
                mimageview.setImageResource(R.drawable.ic_gambar_orang_fme_obes);
            }
        }

        if (intbmi <16){
            mbmicategory.setText("Terlalu kurus");
//            mbackground.setBackgroundColor(Color.RED);
//            mimageview.setImageResource(R.drawable.ic_crosss_01);
        }else if (intbmi<16.9 && intbmi > 16){
            mbmicategory.setText("Cukup Kurus");
//            mbackground.setBackgroundColor(Color.RED);
//            mimageview.setImageResource(R.drawable.ic_warning_01);
        }else if (intbmi<18.5 && intbmi > 17){
            mbmicategory.setText("Sedikit Kurus");
//            mbackground.setBackgroundColor(Color.RED);
//            mimageview.setImageResource(R.drawable.ic_warning_01);
        }else if (intbmi<25 && intbmi > 18.4){
            mbmicategory.setText("Berat Badan Normal");
//            mbackground.setBackgroundColor(Color.YELLOW);
//            mimageview.setImageResource(R.drawable.ic_ok_01);
        }else if (intbmi<29.4 && intbmi > 25){
            mbmicategory.setText("Berat Badan Berlebih");
//            mbackground.setBackgroundColor(Color.RED);
//            mimageview.setImageResource(R.drawable.ic_warning_01);
        }else{
            mbmicategory.setText("Obesitas Class 1");
//            mbackground.setBackgroundColor(Color.RED);
//            mimageview.setImageResource(R.drawable.ic_warning_01);
        }

        mbmidisplay.setText(mbmi);

        btnReCalculateBMI = findViewById(R.id.recalculateBMI);
        btnReCalculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BMIActivity.this, DailyBook.class);
                startActivity(intent);
            }
        });

        btnAturProgram = findViewById(R.id.btn_atur_program);
        btnAturProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BMIActivity.this, DataProgramActivity.class);
                intent.putExtra("data_gender", textGender);
                intent.putExtra("data_height", height);
                intent.putExtra("data_weight", weight);
                intent.putExtra("data_age", textAge);

                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId()==android.R.id.home){
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}