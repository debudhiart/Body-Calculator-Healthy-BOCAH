package id.budhiarta.praktikumprogmob.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_tb_makanan implements Parcelable {
    int makanan_id, kalori, lemak, protein;
    String nama_makanan, satuan;

    public Model_tb_makanan(int makanan_id, int kalori, int lemak, int protein, String nama_makanan, String satuan) {
        this.makanan_id = makanan_id;
        this.kalori = kalori;
        this.lemak = lemak;
        this.protein = protein;
        this.nama_makanan = nama_makanan;
        this.satuan = satuan;
    }

    protected Model_tb_makanan(Parcel in) {
        makanan_id = in.readInt();
        kalori = in.readInt();
        lemak = in.readInt();
        protein = in.readInt();
        nama_makanan = in.readString();
        satuan = in.readString();
    }

    public static final Creator<Model_tb_makanan> CREATOR = new Creator<Model_tb_makanan>() {
        @Override
        public Model_tb_makanan createFromParcel(Parcel in) {
            return new Model_tb_makanan(in);
        }

        @Override
        public Model_tb_makanan[] newArray(int size) {
            return new Model_tb_makanan[size];
        }
    };

    public int getMakanan_id() {
        return makanan_id;
    }

    public void setMakanan_id(int makanan_id) {
        this.makanan_id = makanan_id;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }

    public int getLemak() {
        return lemak;
    }

    public void setLemak(int lemak) {
        this.lemak = lemak;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(makanan_id);
        dest.writeInt(kalori);
        dest.writeInt(lemak);
        dest.writeInt(protein);
        dest.writeString(nama_makanan);
        dest.writeString(satuan);
    }
}
