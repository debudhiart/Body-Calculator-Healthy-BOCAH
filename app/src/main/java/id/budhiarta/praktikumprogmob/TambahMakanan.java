package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahMakanan extends AppCompatActivity {

    MakananData makananData = new MakananData();

    DBHelper db;

    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterMakanan adapterMakanan;
    ArrayList<Model_tb_makanan> daftarMakananAdapter = new ArrayList<>();
    private Model_tb_makanan makananModel;
    public static int idShift;
    private MakananAPI makananAPI;
    private  Call<ArrayList<Model_tb_makanan>> call;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_makanan);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Tambah Makanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Retrofit retrofit= new Retrofit.Builder().baseUrl("http://192.168.1.2:8000/api/").addConverterFactory(GsonConverterFactory.create()).build();
        makananAPI = retrofit.create(MakananAPI.class);
        call = makananAPI.getFood();

//        getSupportActionBar().setBackgroundDrawable(new Color(parseColor("#ffffff")));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.btn_dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_dashboard:
                        return true;
                    case R.id.btn_dailybook:
                        startActivity(new Intent(getApplicationContext(), DailyBook.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

//        datarMakananAdapter.add(new Model_tb_makanan(1,1,2, 1, "Nasi", "1 Sendok Nasi"));


        db = new DBHelper(this);
        daftarMakananAdapter = db.getAllData_tb_makanan();
        idShift=getIntent().getIntExtra("shift_makan_id",0);
        Toast.makeText(this, "ID SHift : "+Integer.toString(idShift), Toast.LENGTH_SHORT).show();
        mulaiAdapter();

        FloatingActionButton fabTambahDataMakanan = findViewById(R.id.fab_tambah_data_makanan);
        fabTambahDataMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewTambahMakanan = new Intent(TambahMakanan.this, FormTambahMakanan.class);
                startActivity(viewTambahMakanan);
            }
        });

//        LinearLayout llTampilMakanan = findViewById(R.id.ll_tampil_makanan);
//        llTampilMakanan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent viewDetailMakanan = new Intent(TambahMakanan.this, DetailMakanan.class);
//                startActivity(viewDetailMakanan);
//            }
//        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void mulaiAdapter(){

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_tambah_makanan);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);



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
                        Toast.makeText(getApplicationContext(), "Code : " + daftarMakananAdapter.get(0).getNama_makanan(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Model_tb_makanan>> call, Throwable t) {
                    Log.d("api",t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                daftarMakananAdapter=db.getAllData_tb_makanan();
                }
            });
        }else {
            adapterMakanan = new AdapterMakanan(getApplicationContext(), makananArrayList, db,idShift);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(adapterMakanan);

        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT){
                    androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(TambahMakanan.this).setTitle("Hapus Makanan")
                            .setMessage("Yakin Hapus Makanan?")
                            .setPositiveButton("Lanjutkan", null)
                            .setNegativeButton("Kembali", null)
                            .show();
                    Button btn_kembali = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    btn_kembali.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapterMakanan.notifyItemChanged(position);
                            dialog.dismiss();
                        }
                    });
                    Button btn_lanjut = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    btn_lanjut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Model_tb_makanan item = adapterMakanan.getMakananList().get(position);
                            db.deleteData_tb_makanan(item.getMakanan_id());
                            adapterMakanan.deleteMakanan(position);
                        }
                    });
//            Toast.makeText(this, "Data berhasil disimpan" + angkaUmur, Toast.LENGTH_SHORT).show();
                }else {
                    makananModel = new Model_tb_makanan(
                            daftarMakananAdapter.get(position).getMakanan_id(),
                            daftarMakananAdapter.get(position).getKalori(),
                            daftarMakananAdapter.get(position).getLemak(),
                            daftarMakananAdapter.get(position).getProtein(),
                            daftarMakananAdapter.get(position).getNama_makanan(),
                            daftarMakananAdapter.get(position).getSatuan()
                    );

                    Intent editMakananIntent = new Intent(TambahMakanan.this, EditMakanan.class);
//                    editMakananIntent.putExtra("idMakanan", daftarMakananAdapter.get(position).getMakanan_id());
                    editMakananIntent.putExtra("dataMakanan", (Parcelable) makananModel);
                    startActivity(editMakananIntent);

                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(adapterMakanan.getContext(), R.color.biru))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                        .addSwipeRightBackgroundColor(Color.RED)
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        itemTouchHelper.attachToRecyclerView(mRecyclerView);


    }

    @Override
    protected void onResume() {
        super.onResume();
        daftarMakananAdapter = db.getAllData_tb_makanan();
//        adapterMakanan.notifyDataSetChanged();
    }



}