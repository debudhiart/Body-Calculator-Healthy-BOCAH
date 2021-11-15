package id.budhiarta.praktikumprogmob.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String db_praktikum_progmob = "db_praktikum_progmob";
    public static final String tb_user = "tb_user";
    public static final String user_id = "user_id";
    public static final String nama_depan = "nama_depan";
    public static final String nama_belakang = "nama_belakang";
    public static final String email = "email";
    public static final String jenis_kelamin = "jenis_kelamin";
    public static final String umur = "umur";
    public static final String password = "password";
    public static final String term_and_condition = "term_and_condition";

    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, db_praktikum_progmob, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tb_user + "(" + user_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nama_depan + " TEXT, " + nama_belakang
                + " TEXT, " + email + " TEXT, " + jenis_kelamin + " TEXT, " + umur + " INTEGER, " + password + " TEXT, " + term_and_condition
                + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tb_user);
    }

    public Cursor getAllData_tb_user(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + tb_user + " ORDER BY user_id DESC", null);
        return cursor;
    }

    public void insertData_tb_user(ContentValues contentValues){
        db.insert(tb_user, null, contentValues);

    }

    public void updateData_tb_user (ContentValues contentValues, Integer id){
        db.update(tb_user, contentValues, user_id + "=" + id, null );
    }

    public void deleteData_tb_user (Integer id){
        db.delete(tb_user,user_id + "=" + id, null );
    }
}
