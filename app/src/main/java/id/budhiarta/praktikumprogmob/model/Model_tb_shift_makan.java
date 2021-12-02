package id.budhiarta.praktikumprogmob.model;

public class Model_tb_shift_makan {
    private int shift_makan_id, total_kalori,user_id;
    private String jenis_shift, tanggal;

    public Model_tb_shift_makan(){
        this.shift_makan_id=0;
    }

    public Model_tb_shift_makan(int shift_makan_id, int total_kalori, int user_id, String jenis_shift, String tanggal) {
        this.shift_makan_id = shift_makan_id;
        this.total_kalori = total_kalori;
        this.user_id = user_id;
        this.jenis_shift = jenis_shift;
        this.tanggal = tanggal;
    }

    public int getShift_makan_id() {
        return shift_makan_id;
    }

    public void setShift_makan_id(int shift_makan_id) {
        this.shift_makan_id = shift_makan_id;
    }

    public int getTotal_kalori() {
        return total_kalori;
    }

    public void setTotal_kalori(int total_kalori) {
        this.total_kalori = total_kalori;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getJenis_shift() {
        return jenis_shift;
    }

    public void setJenis_shift(String jenis_shift) {
        this.jenis_shift = jenis_shift;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
