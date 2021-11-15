package id.budhiarta.praktikumprogmob;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession{



    public void login(Integer user_id, String nama_depan, String token, Context context ){
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("key_id", user_id);
        editor.putString("keyNamaDepan", nama_depan);
        editor.putString("keyToken", token);
        editor.putBoolean("isLogin", true);
        editor.apply();
    }

    public void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }

    public boolean isLogin(Context context){
        return context.getSharedPreferences("pref_name", 0).getBoolean("isLogin", false);
//        return sharedPreferences.getBoolean("isLogin", false);
    }
}
