package id.budhiarta.praktikumprogmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SuksesDaftar extends AppCompatActivity {

    private Integer btn_back = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukses_daftar);

        Intent intent_namaDepan = getIntent();
        Toast.makeText(this, "Terima kasih " + intent_namaDepan.getStringExtra(Register.KIRIM_NAMA_DEPAN) +
                " sudah mendaftar", Toast.LENGTH_SHORT ).show();

        String txt_namaDepan = intent_namaDepan.getStringExtra(Register.KIRIM_NAMA_DEPAN);
        TextView tvIdPesanSapa = (TextView) findViewById(R.id.tv_id_pesanSapa);
        tvIdPesanSapa.setText("Hai " + txt_namaDepan);

        Button btn_halamanDashboard = (Button) findViewById(R.id.btn_halaman_dashboard);
        btn_halamanDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDashboard = new Intent(SuksesDaftar.this, Dashboard.class);
                startActivity(intentDashboard);
//                Toast.makeText(getApplicationContext(),"Tampil Halaman Dashboard", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_halamanProfile = (Button)  findViewById(R.id.btn_halaman_profile);
        btn_halamanProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(SuksesDaftar.this, ProfileActivity.class);
                startActivity(intentProfile);
//                Toast.makeText(getApplicationContext(),"Tampil Halaman Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}