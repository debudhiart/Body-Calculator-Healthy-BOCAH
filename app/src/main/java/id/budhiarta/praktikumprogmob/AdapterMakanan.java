package id.budhiarta.praktikumprogmob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.budhiarta.praktikumprogmob.helper.DBHelper;
import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;

public class AdapterMakanan extends RecyclerView.Adapter<AdapterMakanan.AdapterMakananViewHolder> {
    private ArrayList<Model_tb_makanan> mMakananList;
    Context context;
    private DBHelper myDB;
    private TambahMakanan tambahMakanan;
    private Model_tb_makanan makanan;
    private int id_shift;
    public AdapterMakanan(Context context, ArrayList<Model_tb_makanan> datarMakananList, DBHelper myDB,int id_shift) {
//        this.tambahMakanan = tambahMakanan;
        this.context = context;
        this.mMakananList= datarMakananList;
        this.myDB = myDB;
        this.id_shift=id_shift;
    }


    @NonNull
    @Override
    public AdapterMakananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_makanan,parent,false);
        return new AdapterMakananViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMakananViewHolder holder, int position) {

        holder.view_nama_makanan.setText(mMakananList.get(position).getNama_makanan());
        holder.view_keterangan_makanan.setText(mMakananList.get(position).getSatuan());
        holder.view_kalori_makanan.setText(Integer.toString(mMakananList.get(position).getKalori()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_shift!=0){
                    makanan = new Model_tb_makanan(
                            mMakananList.get(position).getMakanan_id(),
                            mMakananList.get(position).getKalori(),
                            mMakananList.get(position).getLemak(),
                            mMakananList.get(position).getProtein(),
                            mMakananList.get(position).getNama_makanan(),
                            mMakananList.get(position).getSatuan()
                    );
                    Intent intentDetailMakanan = new Intent(holder.itemView.getContext(), DetailMakanan.class);
                    intentDetailMakanan.putExtra("detailMakanan", (Parcelable) makanan);
                    intentDetailMakanan.putExtra("shift_makan_id", id_shift);
                    holder.itemView.getContext().startActivity(intentDetailMakanan);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMakananList.size();
    }

    public class AdapterMakananViewHolder extends RecyclerView.ViewHolder {
        TextView view_nama_makanan;
        TextView view_keterangan_makanan;
        TextView view_kalori_makanan;


        public AdapterMakananViewHolder(@NonNull View itemView) {
            super(itemView);
            view_nama_makanan = itemView.findViewById(R.id.tv_id_nama_makanan);
            view_keterangan_makanan = itemView.findViewById(R.id.tv_id_keterangan_makanan);
            view_kalori_makanan = itemView.findViewById(R.id.tv_id_kalori_makanan);

        }
    }

    public Context getContext(){
        return context;
    }

    public void deleteMakanan(int position){
        mMakananList.remove(position);
        notifyItemRemoved(position);

    }

    public ArrayList<Model_tb_makanan> getMakananList(){
        return mMakananList;
    }
//    public void editItem(int position){
//        Model_tb_makanan item = mMakananList.get(position);
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", item.getMakanan_id());
//        bundle.putString("task", item.getTask());
//
//        TambahTaksBaru tambahTaksBaru = new TambahTaksBaru();
//        tambahTaksBaru.setArguments(bundle);
//        tambahTaksBaru.show(activity.getSupportFragmentManager(), tambahTaksBaru.getTag());
//    }
}
