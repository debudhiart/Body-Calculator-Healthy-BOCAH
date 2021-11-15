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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BMIActivity extends AppCompatActivity {

    android.widget.Button mrecalculatebmi;

    TextView mbmidisplay, mbmicategory, mgender;
    Intent intent;
    ImageView mimageview;
    String mbmi;
    float intbmi;

    String height;
    String weight;
    float intheight, intweight;
    RelativeLayout mbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i);

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
        mgender = findViewById(R.id.genderdisplay);
        mbackground = findViewById(R.id.contentlayout);
        mimageview = findViewById(R.id.imageview);
        mrecalculatebmi = findViewById(R.id.recalculateBMI);

        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");

        intheight = Float.parseFloat(height);
        intweight = Float.parseFloat(weight);

        intheight = intheight /100;
        intbmi = intweight/ (intheight * intheight);
        mbmi = Float.toString(intbmi);

        if (intbmi <16){
            mbmicategory.setText("Terlalu kurus");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.ic_crosss_01);
        }else if (intbmi<16.9 && intbmi > 16){
            mbmicategory.setText("Cukup Kurus");
            mbackground.setBackgroundColor(Color.RED);
//            mimageview.setImageResource(R.drawable.ic_warning_01);
        }else if (intbmi<18.5 && intbmi > 17){
            mbmicategory.setText("Sedikit Kurus");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.ic_warning_01);
        }else if (intbmi<25 && intbmi > 18.4){
            mbmicategory.setText("Berat Badan Normal");
            mbackground.setBackgroundColor(Color.YELLOW);
            mimageview.setImageResource(R.drawable.ic_ok_01);
        }else if (intbmi<29.4 && intbmi > 25){
            mbmicategory.setText("Berat Badan Berlebih");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.ic_warning_01);
        }else{
            mbmicategory.setText("Obesitas Class 1");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.ic_warning_01);
        }

        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(mbmi);

        mrecalculatebmi = findViewById(R.id.recalculateBMI);
        mrecalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BMIActivity.this, DailyBook.class);
                startActivity(intent);
                finish();
            }
        });


    }
}