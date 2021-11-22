package id.budhiarta.praktikumprogmob.model;

public class Model_tb_makanan {
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
}
