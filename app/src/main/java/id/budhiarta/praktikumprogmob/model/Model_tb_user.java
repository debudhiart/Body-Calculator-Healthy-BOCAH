package id.budhiarta.praktikumprogmob.model;

public class Model_tb_user {

    int user_id, umur, term_and_condition;
    String password, jenis_kelamin, email, nama_belakang, nama_depan;

    public Model_tb_user(int user_id, int umur, int term_and_condition, String password, String jenis_kelamin, String email, String nama_belakang, String nama_depan) {
        this.user_id = user_id;
        this.umur = umur;
        this.term_and_condition = term_and_condition;
        this.password = password;
        this.jenis_kelamin = jenis_kelamin;
        this.email = email;
        this.nama_belakang = nama_belakang;
        this.nama_depan = nama_depan;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public int getTerm_and_condition() {
        return term_and_condition;
    }

    public void setTerm_and_condition(int term_and_condition) {
        this.term_and_condition = term_and_condition;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama_belakang() {
        return nama_belakang;
    }

    public void setNama_belakang(String nama_belakang) {
        this.nama_belakang = nama_belakang;
    }

    public String getNama_depan() {
        return nama_depan;
    }

    public void setNama_depan(String nama_depan) {
        this.nama_depan = nama_depan;
    }
}
