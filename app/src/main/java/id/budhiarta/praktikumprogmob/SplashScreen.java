package id.budhiarta.praktikumprogmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        UserSession session = new UserSession();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.isLogin(SplashScreen.this)){
                    startActivity(new Intent(SplashScreen.this, Register.class));
                }else{
                    startActivity(new Intent(SplashScreen.this, Login.class));
                }
                finish();
            }
        }, 3000);

    }
}