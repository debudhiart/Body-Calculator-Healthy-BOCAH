package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;
import id.budhiarta.praktikumprogmob.model.Model_tb_program;
import id.budhiarta.praktikumprogmob.model.Model_tb_shift_makan;
import id.budhiarta.praktikumprogmob.model.Model_tb_user;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class Dashboard extends AppCompatActivity {
    private RecyclerView.LayoutManager sarapanLayoutManager,mknSiangLayoutManager,mknMalamLayoutManager;
    private AdapterMakanan adapterMakananSarapan,adapterMakananSiang,adapterMakananMalam;
    ArrayList<Model_tb_makanan> daftarMakananSarapan,daftarMakananSiang,daftarMakananMalam = new ArrayList<>();
    private Model_tb_makanan makananModel;
    private Model_tb_shift_makan shiftSarapan,shiftSiang,shiftMalam;
    private Model_tb_program programModel;
    private DBHelper db;
    private TextView tv_kalori_sarapan,tv_kalori_mkn_siang,tv_kalori_mkn_malam,tv_total_kalori,tv_target_kalori,tv_sisa_kalori;
    private RecyclerView sarapanRecyclerView,mknSiangRecyclerView,mknMalamRecyclerView;
    private int total_kalori,target_kalori;

    int userID;
    ArrayList<Model_tb_makanan> daftarMakananAdapter = new ArrayList<>();

    private MakananAPI makananAPI;
    private Call<ArrayList<Model_tb_makanan>> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userID = this.getSharedPreferences("pref_name", 0).getInt("key_id", 0);

        Retrofit retrofit= new Retrofit.Builder().baseUrl("http://192.168.1.3:8000/api/").addConverterFactory(GsonConverterFactory.create()).build();
        makananAPI = retrofit.create(MakananAPI.class);
        call = makananAPI.getFood();

        db = new DBHelper(this);
        daftarMakananAdapter = db.getAllData_tb_makanan();

        ArrayList <Model_tb_makanan> makananArrayList = db.getAllData_tb_makanan();
        if (makananArrayList.isEmpty()){
            call.enqueue(new Callback<ArrayList<Model_tb_makanan>>() {
                @Override
                public void onResponse(Call<ArrayList<Model_tb_makanan>> call, Response<ArrayList<Model_tb_makanan>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Code : " + Integer.toString(response.code()), Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        daftarMakananAdapter = response.body();
                        db.insertAllData(daftarMakananAdapter);
                        Toast.makeText(getApplicationContext(), "Selamat datang kembali di aplikasi BOCAH ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Model_tb_makanan>> call, Throwable t) {
                    Log.d("api",t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                daftarMakananAdapter=db.getAllData_tb_makanan();
                }
            });
        }

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Dashboard");
        sarapanRecyclerView = (RecyclerView) findViewById(R.id.rv_sarapan);
        mknSiangRecyclerView = (RecyclerView) findViewById(R.id.rv_makan_siang);
        mknMalamRecyclerView = (RecyclerView) findViewById(R.id.rv_makan_malam);
        tv_kalori_sarapan=findViewById(R.id.tv_id_jumlah_sarapan);
        tv_kalori_mkn_siang=findViewById(R.id.tv_id_jumlah_makan_siang);
        tv_kalori_mkn_malam=findViewById(R.id.tv_id_jumlah_makan_malam);
        tv_total_kalori=findViewById(R.id.tv_totalKalori);
        tv_target_kalori=findViewById(R.id.tv_target_kalori);
        tv_sisa_kalori=findViewById(R.id.tv_sisa_kalori);

        db=new DBHelper(this);
        db.checkAndCreateShift(userID);

//        try {
//            programModel=db.getProgram(userID);
//
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(), "Nama Depan atau Password salah", Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "Error:" + e.getMessage());
//            return;
//        }

        programModel=db.getProgram(userID);
        shiftSarapan=db.getShiftMakan(1,userID);
        shiftSiang=db.getShiftMakan(2,userID);
        shiftMalam=db.getShiftMakan(3,userID);
        daftarMakananSarapan=db.getData_tb_makanan_By_Shift(shiftSarapan.getShift_makan_id());
        daftarMakananSiang=db.getData_tb_makanan_By_Shift(shiftSiang.getShift_makan_id());
        daftarMakananMalam=db.getData_tb_makanan_By_Shift(shiftMalam.getShift_makan_id());
        target_kalori=programModel.getTarget_kalori();
        tv_target_kalori.setText(Integer.toString(target_kalori));
        mulaiAdapter();

//        Toast.makeText(getApplicationContext(), daftarMakananSarapan.get(0).toString() , Toast.LENGTH_SHORT).show();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.btn_dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_dashboard:
                        return true;
                    case R.id.btn_dailybook:
                        startActivity(new Intent(getApplicationContext(), DailyBook.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.btn_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        Button btnTambahSarapan = findViewById(R.id.btn_tambah_sarapan);
        btnTambahSarapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent halamanTambahSarapan = new Intent(Dashboard.this, TambahMakanan.class);
                halamanTambahSarapan.putExtra("shift_makan_id", shiftSarapan.getShift_makan_id());
                startActivity(halamanTambahSarapan);
            }
        });
        Button btnTambahMakanSiang = findViewById(R.id.btn_tambah_makan_siang);
        btnTambahMakanSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent halamanTambahMakanSiang = new Intent(Dashboard.this, TambahMakanan.class);
                halamanTambahMakanSiang.putExtra("shift_makan_id", shiftSiang.getShift_makan_id());
                startActivity(halamanTambahMakanSiang);
            }
        });
        Button btnTambahMakanMalam = findViewById(R.id.btn_tambah_makan_malam);
        btnTambahMakanMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent halamanTambahMakanMalam = new Intent(Dashboard.this, TambahMakanan.class);
                halamanTambahMakanMalam.putExtra("shift_makan_id", shiftMalam.getShift_makan_id());
                startActivity(halamanTambahMakanMalam);
            }
        });

    }

    public void mulaiAdapter() {
        total_kalori=shiftSarapan.getTotal_kalori()+shiftSiang.getTotal_kalori()+shiftMalam.getTotal_kalori();
        tv_kalori_sarapan.setText(Integer.toString(shiftSarapan.getTotal_kalori()));
        tv_kalori_mkn_siang.setText(Integer.toString(shiftSiang.getTotal_kalori()));
        tv_kalori_mkn_malam.setText(Integer.toString(shiftMalam.getTotal_kalori()));
        tv_total_kalori.setText(Integer.toString(total_kalori));
        tv_sisa_kalori.setText(Integer.toString(target_kalori-total_kalori));
        Log.d("kalorian",Integer.toString(target_kalori));
        Log.d("kalorian",Integer.toString(total_kalori));
        Log.d("kalorian",Integer.toString(target_kalori-total_kalori));
        sarapanRecyclerView.setHasFixedSize(false);
        mknSiangRecyclerView.setHasFixedSize(false);
        mknMalamRecyclerView.setHasFixedSize(false);
        sarapanLayoutManager = new LinearLayoutManager(this);
        mknSiangLayoutManager = new LinearLayoutManager(this);
        mknMalamLayoutManager = new LinearLayoutManager(this);
        adapterMakananSarapan = new AdapterMakanan(this, daftarMakananSarapan,  db,0);
        adapterMakananSiang = new AdapterMakanan(this, daftarMakananSiang,  db,0);
        adapterMakananMalam = new AdapterMakanan(this, daftarMakananMalam,  db,0);
        sarapanRecyclerView.setLayoutManager(sarapanLayoutManager);
        sarapanRecyclerView.setAdapter(adapterMakananSarapan);
        mknSiangRecyclerView.setLayoutManager(mknSiangLayoutManager);
        mknSiangRecyclerView.setAdapter(adapterMakananSiang);
        mknMalamRecyclerView.setLayoutManager(mknMalamLayoutManager);
        mknMalamRecyclerView.setAdapter(adapterMakananMalam);
        createItemTouchHelper(sarapanRecyclerView,adapterMakananSarapan,shiftSarapan.getShift_makan_id());
        createItemTouchHelper(mknSiangRecyclerView,adapterMakananSiang,shiftSiang.getShift_makan_id());
        createItemTouchHelper(mknMalamRecyclerView,adapterMakananMalam,shiftMalam.getShift_makan_id());

    }
    public void createItemTouchHelper(RecyclerView jenis_rv, AdapterMakanan jenis_adapter, int id_shift){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
//            Toast.makeText(this, "Data berhasil disimpan" + angkaUmur, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(jenis_adapter.getContext());
                    builder.setTitle("Hapus Makanan");
                    builder.setMessage("Yakin Hapus Makanan?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Model_tb_makanan item = jenis_adapter.getMakananList().get(position);
                            jenis_adapter.deleteMakanan(position);
                            db.deleteDataMakananInShiftMakanan(item,id_shift);

                            shiftSarapan=db.getShiftMakan(1,userID);
                            shiftSiang=db.getShiftMakan(2,userID);
                            shiftMalam=db.getShiftMakan(3,userID);

                            total_kalori=shiftSarapan.getTotal_kalori()+shiftSiang.getTotal_kalori()+shiftMalam.getTotal_kalori();
                            tv_kalori_sarapan.setText(Integer.toString(shiftSarapan.getTotal_kalori()));
                            tv_kalori_mkn_siang.setText(Integer.toString(shiftSiang.getTotal_kalori()));
                            tv_kalori_mkn_malam.setText(Integer.toString(shiftMalam.getTotal_kalori()));
                            tv_total_kalori.setText(Integer.toString(total_kalori));
                            tv_sisa_kalori.setText(Integer.toString(target_kalori-total_kalori));

                        }
                    });
                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            jenis_adapter.notifyItemChanged(position);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(Color.RED)
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        itemTouchHelper.attachToRecyclerView(jenis_rv);
    }



}