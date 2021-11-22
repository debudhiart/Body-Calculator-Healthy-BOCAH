package id.budhiarta.praktikumprogmob.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;

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

    public static final String tb_makanan = "tb_makanan";
    public static final String makanan_id = "makanan_id";
    public static final String nama_makanan = "nama_makanan";
    public static final String satuan = "satuan";
    public static final String kalori = "nama_belakang";
    public static final String protein = "protein";
    public static final String lemak = "lemak";

    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, db_praktikum_progmob, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTabelUser = "CREATE TABLE " + tb_user + "(" + user_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nama_depan + " TEXT, "
                + nama_belakang + " TEXT, " + email + " TEXT, " + jenis_kelamin + " TEXT, " + umur + " INTEGER, "
                + password + " TEXT, " + term_and_condition + " INTEGER)";
        String queryCreateTabelMakanan = "CREATE TABLE " + tb_makanan + "(" + makanan_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nama_makanan
                + " TEXT, " + satuan + " TEXT, " + kalori + " INTEGER, " + protein + " INTEGER, " + lemak + " INTEGER)";
        db.execSQL(queryCreateTabelUser);
        db.execSQL(queryCreateTabelMakanan);
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

    public void insertData_tb_makanan(ContentValues contentValues){
        db.insert(tb_makanan, null, contentValues);
    }

    public void updateData_tb_makanan (ContentValues contentValues, Integer id){
        db.update(tb_makanan, contentValues, makanan_id + "=" + id, null );
    }

    public void deleteData_tb_makanan (Integer id){
        db.delete(tb_makanan,makanan_id + "=" + id, null );
    }

    public ArrayList<Model_tb_makanan> getAllData_tb_makanan(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tb_makanan, null);
        ArrayList<Model_tb_makanan> data = new ArrayList<Model_tb_makanan>();

        if (cursor.moveToFirst()) {
            do {
                data.add(new Model_tb_makanan(
                        cursor.getInt(cursor.getColumnIndex(makanan_id))
                        ,cursor.getInt(cursor.getColumnIndex(kalori))
                        ,cursor.getInt(cursor.getColumnIndex(lemak))
                        ,cursor.getInt(cursor.getColumnIndex(protein))
                        ,cursor.getString(cursor.getColumnIndex(nama_makanan))
                        ,cursor.getString(cursor.getColumnIndex(satuan))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }
}
