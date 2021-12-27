package id.budhiarta.praktikumprogmob.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;
import id.budhiarta.praktikumprogmob.model.Model_tb_program;
import id.budhiarta.praktikumprogmob.model.Model_tb_shift_makan;
import id.budhiarta.praktikumprogmob.model.Model_tb_user;

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
    public static final String kalori = "kalori";
    public static final String protein = "protein";
    public static final String lemak = "lemak";

    public static final String TB_SHIFT_MAKAN = "tb_shift_makan";
    public static final String SHIFT_MAKAN_ID = "shift_makan_id";
    public static final String JENIS_SHIFT = "jenis_shift";
    public static final String TOTAL_KALORI = "total_kalori";
    public static final String TANGGAL = "tanggal";
    public static final String TB_DETAIL_SHIFT="tb_detail_shift";
    public static final String DETAIL_SHIFT_ID = "detail_shift_id";

    public static final String TB_PROGRAM="tb_program";
    public static final String ID_PROGRAM="id_program";
    public static final String TANGGAL_DIBUAT="tgl_dibuat";
    public static final String UMUR_AT_PROGRAM="umur_at_program";
    public static final String BERAT_BADAN="berat_badan";
    public static final String TINGGI_BADAN="tinggi_badan";
    public static final String TARGET_KALORI="target_kalori";
    public static final String AKTIFITAS_TUBUH="aktifitas_tubuh";
    public static final String JENIS_PROGRAM="jenis_program";


    private Context context;

    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, db_praktikum_progmob, null, 2);
        db = getWritableDatabase();
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTabelUser = "CREATE TABLE " + tb_user + "(" + user_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nama_depan + " TEXT, "
                + nama_belakang + " TEXT, " + email + " TEXT, " + jenis_kelamin + " TEXT, " + umur + " INTEGER, "
                + password + " TEXT, " + term_and_condition + " INTEGER)";
        String queryCreateTabelMakanan = "CREATE TABLE " + tb_makanan + "(" + makanan_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nama_makanan
                + " TEXT, " + satuan + " TEXT, " + kalori + " INTEGER, " + protein + " INTEGER, " + lemak + " INTEGER)";

        String queryCreateTabelShiftMakan = "CREATE TABLE " + TB_SHIFT_MAKAN + "(" + SHIFT_MAKAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + JENIS_SHIFT
                + " TEXT, " + TOTAL_KALORI + " INTEGER, "+  TANGGAL+" TEXT, "+user_id + " INTEGER, FOREIGN KEY ("+user_id+") REFERENCES "+tb_user+"("+user_id+") ON DELETE CASCADE ON UPDATE NO ACTION)";

        String queryCreateTabelDetailShift = "CREATE TABLE " + TB_DETAIL_SHIFT + "(" + DETAIL_SHIFT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + makanan_id + " INTEGER, " + SHIFT_MAKAN_ID + " INTEGER, FOREIGN KEY ("+makanan_id+") REFERENCES "+tb_makanan+"("+makanan_id+") ON DELETE CASCADE ON UPDATE NO ACTION, FOREIGN KEY ("+SHIFT_MAKAN_ID+") REFERENCES "+TB_SHIFT_MAKAN+"("+SHIFT_MAKAN_ID+") ON DELETE CASCADE ON UPDATE NO ACTION)";

        String queryCreateTableProgram="CREATE TABLE "+TB_PROGRAM+" ( "+ID_PROGRAM+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TANGGAL_DIBUAT+" TEXT, "+UMUR_AT_PROGRAM+" INTEGER, "+BERAT_BADAN+" INTEGER, "+TINGGI_BADAN+" INTEGER, "+TARGET_KALORI+" INTEGER, "+AKTIFITAS_TUBUH+" TEXT, "+JENIS_PROGRAM+" TEXT, "+user_id+" INTEGER, FOREIGN KEY("+user_id+") REFERENCES "+tb_user+"("+user_id+") ON DELETE CASCADE ON UPDATE NO ACTION)";

        db.execSQL(queryCreateTabelUser);
        db.execSQL(queryCreateTabelMakanan);
        db.execSQL(queryCreateTabelShiftMakan);
        db.execSQL(queryCreateTabelDetailShift);
        db.execSQL(queryCreateTableProgram);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tb_user);
        db.execSQL("DROP TABLE IF EXISTS " + tb_makanan);
        db.execSQL("DROP TABLE IF EXISTS " + TB_SHIFT_MAKAN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_DETAIL_SHIFT);
        db.execSQL("DROP TABLE IF EXISTS " + TB_PROGRAM);

        String queryCreateTabelUser = "CREATE TABLE " + tb_user + "(" + user_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nama_depan + " TEXT, "
                + nama_belakang + " TEXT, " + email + " TEXT, " + jenis_kelamin + " TEXT, " + umur + " INTEGER, "
                + password + " TEXT, " + term_and_condition + " INTEGER)";
        String queryCreateTabelMakanan = "CREATE TABLE " + tb_makanan + "(" + makanan_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nama_makanan
                + " TEXT, " + satuan + " TEXT, " + kalori + " INTEGER, " + protein + " INTEGER, " + lemak + " INTEGER)";

        String queryCreateTabelShiftMakan = "CREATE TABLE " + TB_SHIFT_MAKAN + "(" + SHIFT_MAKAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + JENIS_SHIFT
                + " TEXT, " + TOTAL_KALORI + " INTEGER, "+  TANGGAL+" TEXT, "+user_id + " INTEGER, FOREIGN KEY ("+user_id+") REFERENCES "+tb_user+"("+user_id+") ON DELETE CASCADE ON UPDATE NO ACTION)";

        String queryCreateTabelDetailShift = "CREATE TABLE " + TB_DETAIL_SHIFT + "(" + DETAIL_SHIFT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + makanan_id + " INTEGER, " + SHIFT_MAKAN_ID + " INTEGER, FOREIGN KEY ("+makanan_id+") REFERENCES "+tb_makanan+"("+makanan_id+") ON DELETE CASCADE ON UPDATE NO ACTION, FOREIGN KEY ("+SHIFT_MAKAN_ID+") REFERENCES "+TB_SHIFT_MAKAN+"("+SHIFT_MAKAN_ID+") ON DELETE CASCADE ON UPDATE NO ACTION)";

        String queryCreateTableProgram="CREATE TABLE "+TB_PROGRAM+" ( "+ID_PROGRAM+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TANGGAL_DIBUAT+" TEXT, "+UMUR_AT_PROGRAM+" INTEGER, "+BERAT_BADAN+" INTEGER, "+TINGGI_BADAN+" INTEGER, "+TARGET_KALORI+" INTEGER, "+AKTIFITAS_TUBUH+" TEXT, "+JENIS_PROGRAM+" TEXT, "+user_id+" INTEGER, FOREIGN KEY("+user_id+") REFERENCES "+tb_user+"("+user_id+") ON DELETE CASCADE ON UPDATE NO ACTION)";

        db.execSQL(queryCreateTabelUser);
        db.execSQL(queryCreateTabelMakanan);
        db.execSQL(queryCreateTabelShiftMakan);
        db.execSQL(queryCreateTabelDetailShift);
        db.execSQL(queryCreateTableProgram);
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
//    public void createShiftMakan(int user_id){
//
//
//    }
    public void insertMakananToDetailShift(int shift_makan_id,Model_tb_makanan food){
        ContentValues values = new ContentValues();
        values.put(DBHelper.makanan_id, food.getMakanan_id());
        values.put(DBHelper.SHIFT_MAKAN_ID,shift_makan_id);
        db.insert(TB_DETAIL_SHIFT, null, values);
        updateShiftMakan(shift_makan_id,1,food.getKalori());
    }

    public Model_tb_shift_makan getShiftMakan(int shift_makan_id, int user_id){
        int id_shift_makanan=0;
        String str_jenis_shift="";
        if(shift_makan_id==1){
            str_jenis_shift="Sarapan";
        }else if(shift_makan_id==2){
            str_jenis_shift="Makan Siang";
        }else if(shift_makan_id==3){
            str_jenis_shift="Makan Malam";
        }else{

        }
        Model_tb_shift_makan shift_makan=new Model_tb_shift_makan();
        SQLiteDatabase db = this.getReadableDatabase();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String tgl_hari_ini=dtf.format(now);

        Cursor cursor = db.rawQuery("SELECT * FROM "+TB_SHIFT_MAKAN+" WHERE "+this.user_id+"="+ user_id +" AND "+JENIS_SHIFT+"='"+ str_jenis_shift +"' AND date("+ TANGGAL+") = '"+tgl_hari_ini+"';",null);

        if(cursor.moveToFirst()){
             shift_makan=new Model_tb_shift_makan(
                    cursor.getInt(cursor.getColumnIndex(SHIFT_MAKAN_ID)),
                    cursor.getInt(cursor.getColumnIndex(TOTAL_KALORI)),
                    cursor.getInt(cursor.getColumnIndex(this.user_id)),
                    cursor.getString(cursor.getColumnIndex(JENIS_SHIFT)),
                    cursor.getString(cursor.getColumnIndex(TANGGAL))

            );
        }
        return shift_makan;
    }


    public ArrayList<Model_tb_makanan> getData_tb_makanan_By_Shift(int shift_makan_id){
        ArrayList<Model_tb_makanan> data= new ArrayList<Model_tb_makanan>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime now = LocalDateTime.now();
//        String tgl_hari_ini=dtf.format(now);
//        Cursor cursor = db.rawQuery("SELECT * FROM "+TB_SHIFT_MAKAN+" JOIN "+TB_DETAIL_SHIFT+" USING("+SHIFT_MAKAN_ID+") JOIN "+tb_makanan+" USING("+makanan_id+") WHERE "+this.user_id+"="+ user_id +" AND "+JENIS_SHIFT+"='"+ str_jenis_shift +"' AND date("+ TANGGAL+") = '"+tgl_hari_ini+"';",null);

        Cursor cursor = db.rawQuery("SELECT * FROM "+TB_SHIFT_MAKAN+" JOIN "+TB_DETAIL_SHIFT+" USING("+SHIFT_MAKAN_ID+") JOIN "+tb_makanan+" USING("+makanan_id+") WHERE "+SHIFT_MAKAN_ID+"="+shift_makan_id,null);
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{
                    data.add(new Model_tb_makanan(
                            cursor.getInt(cursor.getColumnIndex(makanan_id)),
                            cursor.getInt(cursor.getColumnIndex(kalori)),
                            cursor.getInt(cursor.getColumnIndex(lemak)),
                            cursor.getInt(cursor.getColumnIndex(protein)),
                            cursor.getString(cursor.getColumnIndex(nama_makanan)),
                            cursor.getString(cursor.getColumnIndex(satuan))
                    ));
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return data;
    }
    public void deleteDataMakananInShiftMakanan(Model_tb_makanan makananModel,int shift_makan_id){
        db.delete(TB_DETAIL_SHIFT,this.DETAIL_SHIFT_ID+" IN " +"(SELECT "+DETAIL_SHIFT_ID+" FROM "+TB_DETAIL_SHIFT+" WHERE "+this.makanan_id + "=" + makananModel.getMakanan_id()+" AND "+SHIFT_MAKAN_ID+"="+shift_makan_id+" LIMIT 1)", null );
        updateShiftMakan(shift_makan_id,2,makananModel.getKalori());
    }

    public void updateShiftMakan(int shift_makan_id,int opsi, int kalori){
        ContentValues values=new ContentValues();
        Cursor cursor=db.rawQuery("SELECT "+TOTAL_KALORI+" FROM "+TB_SHIFT_MAKAN+" WHERE "+SHIFT_MAKAN_ID+"="+Integer.toString(shift_makan_id),null);
        cursor.moveToFirst();
        int total_kalori=cursor.getInt(0);
        if(opsi==1){
                values.put(TOTAL_KALORI,total_kalori+kalori);
                int hasil=db.update(TB_SHIFT_MAKAN,values,SHIFT_MAKAN_ID+"=?",new String[]{Integer.toString(shift_makan_id)});
//                Toast.makeText(context,"Hasil : "+Integer.toString(hasil), Toast.LENGTH_LONG).show();
        }else{
            values.put(TOTAL_KALORI,total_kalori-kalori);
            int hasil=db.update(TB_SHIFT_MAKAN,values,SHIFT_MAKAN_ID+"=?",new String[]{Integer.toString(shift_makan_id)});
//            Toast.makeText(context,"Hasil : "+Integer.toString(hasil), Toast.LENGTH_LONG).show();
        }
    }

    public void insertAllData(ArrayList<Model_tb_makanan> makanan){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Model_tb_makanan makan : makanan) {
                values.put(nama_makanan, makan.getNama_makanan());
                values.put(satuan, makan.getSatuan());
                values.put(kalori, makan.getKalori());
                values.put(protein, makan.getProtein());
                values.put(lemak, makan.getLemak());
                db.insert(tb_makanan, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void insertProgramData(Model_tb_program model_tb_program){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TANGGAL_DIBUAT,model_tb_program.getTgl_dibuat());
        values.put(UMUR_AT_PROGRAM,model_tb_program.getUmur_at_program());
        values.put(BERAT_BADAN,model_tb_program.getBerat_badan());
        values.put(TINGGI_BADAN,model_tb_program.getTinggi_badan());
        values.put(TARGET_KALORI,model_tb_program.getTarget_kalori());
        values.put(JENIS_PROGRAM,model_tb_program.getJenis_program());
        values.put(AKTIFITAS_TUBUH,model_tb_program.getAktifitas_tubuh());
        values.put(user_id,model_tb_program.getUser_id());
        db.insert(TB_PROGRAM,null,values);
    }

    public Model_tb_program getProgram(int id_user){
        SQLiteDatabase db=this.getReadableDatabase();
        Model_tb_program programModel;
        Cursor cursor = db.rawQuery("SELECT * FROM "+TB_PROGRAM+" WHERE "+user_id+"="+Integer.toString(id_user),null);
        if(cursor.moveToLast()){
            programModel=new Model_tb_program(
                    cursor.getInt(cursor.getColumnIndex(UMUR_AT_PROGRAM)),
                    cursor.getInt(cursor.getColumnIndex(BERAT_BADAN)),
                    cursor.getInt(cursor.getColumnIndex(TINGGI_BADAN)),
                    cursor.getInt(cursor.getColumnIndex(TARGET_KALORI)),
                    cursor.getString(cursor.getColumnIndex(TANGGAL_DIBUAT)),
                    cursor.getString(cursor.getColumnIndex(AKTIFITAS_TUBUH)),
                    cursor.getString(cursor.getColumnIndex(JENIS_PROGRAM)),
                    cursor.getInt(cursor.getColumnIndex(user_id))
            );
            programModel.setId_program(cursor.getInt(cursor.getColumnIndex(ID_PROGRAM)));
        }else{
            programModel= new Model_tb_program(0);
        }

        return programModel;
    }

    public Cursor login(String login_nama_depan, String login_password){
        SQLiteDatabase db=this.getReadableDatabase();
        Model_tb_user userModel;
        Cursor cursor = db.rawQuery("SELECT * FROM "+tb_user+" WHERE nama_depan = '"+ login_nama_depan + "' AND password = '"+ login_password +"'", null);

        return cursor;
    }
    public void checkAndCreateShift(int user_id){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String tgl_hari_ini=dtf.format(now);
        Cursor cursor = db.rawQuery("SELECT * FROM "+TB_SHIFT_MAKAN+" WHERE "+this.user_id+"="+ user_id +" AND date("+ TANGGAL+") = '"+tgl_hari_ini+"';",null);
        if(cursor.getCount()<=0){
            ContentValues values = new ContentValues();
            values.put(JENIS_SHIFT, "Sarapan");
            values.put(TOTAL_KALORI,0);
            values.put(TANGGAL,tgl_hari_ini);
            values.put(DBHelper.user_id,user_id);
            db.insert(TB_SHIFT_MAKAN, null, values);
            values.put(JENIS_SHIFT, "Makan Siang");
            db.insert(TB_SHIFT_MAKAN, null, values);
            values.put(JENIS_SHIFT, "Makan Malam");
            db.insert(TB_SHIFT_MAKAN, null, values);
        }
    }

    public Cursor  getDataUser(int id_user){
        SQLiteDatabase db=this.getReadableDatabase();
        Model_tb_user userModel;
        Cursor cursor = db.rawQuery("SELECT * FROM "+tb_user+" WHERE "+user_id+"="+id_user,null);

        return cursor;
    }
}
