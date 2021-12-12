package id.budhiarta.praktikumprogmob.model;

public class Model_tb_program {
    private int id_program,umur_at_program,berat_badan,tinggi_badan,target_kalori;
    private String tgl_dibuat,aktifitas_tubuh,jenis_program;

    public Model_tb_program(int umur_at_program, int berat_badan, int tinggi_badan, int target_kalori, String tgl_dibuat, String aktifitas_tubuh, String jenis_program) {
        this.umur_at_program = umur_at_program;
        this.berat_badan = berat_badan;
        this.tinggi_badan = tinggi_badan;
        this.target_kalori = target_kalori;
        this.tgl_dibuat = tgl_dibuat;
        this.aktifitas_tubuh = aktifitas_tubuh;
        this.jenis_program = jenis_program;
    }

    public int getId_program() {
        return id_program;
    }

    public void setId_program(int id_program) {
        this.id_program = id_program;
    }

    public int getUmur_at_program() {
        return umur_at_program;
    }

    public void setUmur_at_program(int umur_at_program) {
        this.umur_at_program = umur_at_program;
    }

    public int getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(int berat_badan) {
        this.berat_badan = berat_badan;
    }

    public int getTinggi_badan() {
        return tinggi_badan;
    }

    public void setTinggi_badan(int tinggi_badan) {
        this.tinggi_badan = tinggi_badan;
    }

    public int getTarget_kalori() {
        return target_kalori;
    }

    public void setTarget_kalori(int target_kalori) {
        this.target_kalori = target_kalori;
    }

    public String getTgl_dibuat() {
        return tgl_dibuat;
    }

    public void setTgl_dibuat(String tgl_dibuat) {
        this.tgl_dibuat = tgl_dibuat;
    }

    public String getAktifitas_tubuh() {
        return aktifitas_tubuh;
    }

    public void setAktifitas_tubuh(String aktifitas_tubuh) {
        this.aktifitas_tubuh = aktifitas_tubuh;
    }

    public String getJenis_program() {
        return jenis_program;
    }

    public void setJenis_program(String jenis_program) {
        this.jenis_program = jenis_program;
    }
}
