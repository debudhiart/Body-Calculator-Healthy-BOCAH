package id.budhiarta.praktikumprogmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_user;

public class LoginActivity extends AppCompatActivity {

    UserSession userSession = new UserSession();
    DBHelper dbHelper;

    Model_tb_user userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        dbHelper = new DBHelper(this);

        Button btnLogin =findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etLoginNamaDepan = findViewById(R.id.et_login_nama_depan);
                String textLoginNamaDepan = etLoginNamaDepan.getText().toString();

                EditText etPassword = findViewById(R.id.et_password);
                String textPassword = etPassword.getText().toString();

                Cursor cursor = (Cursor) dbHelper.login(textLoginNamaDepan, textPassword);;
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
//                Toast.makeText(getApplicationContext(),"userModel: " +userModel.getNama_depan(), Toast.LENGTH_SHORT ).show();


                userSession.login(userModel.getUser_id(), userModel.getNama_depan(),userModel.getPassword(), LoginActivity.this);
                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        TextView tvMasukSekarang = findViewById(R.id.tv_masuk_sekarang);
        tvMasukSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }



}