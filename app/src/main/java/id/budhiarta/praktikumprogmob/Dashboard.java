package id.budhiarta.praktikumprogmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;
import id.budhiarta.praktikumprogmob.model.Model_tb_shift_makan;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Dashboard extends AppCompatActivity {
    private RecyclerView.LayoutManager sarapanLayoutManager,mknSiangLayoutManager,mknMalamLayoutManager;
    private AdapterMakanan adapterMakananSarapan,adapterMakananSiang,adapterMakananMalam;
    ArrayList<Model_tb_makanan> daftarMakananSarapan,daftarMakananSiang,daftarMakananMalam = new ArrayList<>();
    private Model_tb_makanan makananModel;
    private Model_tb_shift_makan shiftSarapan,shiftSiang,shiftMalam;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Dashboard");

        db=new DBHelper(this);
        shiftSarapan=db.getShiftMakan(1,1);
        shiftSiang=db.getShiftMakan(2,1);
        shiftMalam=db.getShiftMakan(3,1);
        daftarMakananSarapan=db.getData_tb_makanan_By_Shift(shiftSarapan.getShift_makan_id());
        daftarMakananSiang=db.getData_tb_makanan_By_Shift(shiftSiang.getShift_makan_id());
        daftarMakananMalam=db.getData_tb_makanan_By_Shift(shiftMalam.getShift_makan_id());
        mulaiAdapter();

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
                        startActivity(new Intent(getApplicationContext(), Profile.class));
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

        RecyclerView sarapanRecyclerView = (RecyclerView) findViewById(R.id.rv_sarapan);
        RecyclerView mknSiangRecyclerView = (RecyclerView) findViewById(R.id.rv_makan_siang);
        RecyclerView mknMalamRecyclerView = (RecyclerView) findViewById(R.id.rv_makan_malam);
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
        createItemTouchHelper(sarapanRecyclerView,adapterMakananSarapan,1);
        createItemTouchHelper(mknSiangRecyclerView,adapterMakananSiang,2);
        createItemTouchHelper(mknMalamRecyclerView,adapterMakananMalam,3);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                final int position = viewHolder.getAdapterPosition();
//                if (direction == ItemTouchHelper.RIGHT) {
////            Toast.makeText(this, "Data berhasil disimpan" + angkaUmur, Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(adapterMakanan.getContext());
//                    builder.setTitle("Hapus Makanan");
//                    builder.setMessage("Yakin Hapus Makanan?");
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Model_tb_makanan item = adapterMakanan.getMakananList().get(position);
//                            adapterMakanan.deleteMakanan(position);
//                            db.deleteDataMakananInShiftMakanan(item.getMakanan_id(),jenis_shift);
//
//                        }
//                    });
//                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            adapterMakanan.notifyItemChanged(position);
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//
//            @Override
//            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                        .addSwipeRightBackgroundColor(Color.RED)
//                        .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
//                        .create()
//                        .decorate();
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }
//        });
//
//        itemTouchHelper.attachToRecyclerView(sarapanRecyclerView);
//        itemTouchHelper.attachToRecyclerView(mknSiangRecyclerView);
//        itemTouchHelper.attachToRecyclerView(mknMalamRecyclerView);

    }
    public void createItemTouchHelper(RecyclerView jenis_rv, AdapterMakanan jenis_adapter, int jenis_shift){
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
                            db.deleteDataMakananInShiftMakanan(item.getMakanan_id(),jenis_shift);

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