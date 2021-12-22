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

        Button btn_halamanProfile = (Button)  findViewById(R.id.btn_kembali_login);
        btn_halamanProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(SuksesDaftar.this, LoginActivity.class);
                startActivity(intentLogin);
//                Toast.makeText(getApplicationContext(),"Tampil Halaman Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}