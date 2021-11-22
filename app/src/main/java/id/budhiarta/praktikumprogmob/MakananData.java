package id.budhiarta.praktikumprogmob;

import java.util.ArrayList;

import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;

public class MakananData {

    ArrayList<Model_tb_makanan> makananList = new ArrayList<>();

    public MakananData() {
        makananList.add(new Model_tb_makanan(1,0,2, 1, "Nasi", "1 Sendok Nasi"));
    }

    public ArrayList<Model_tb_makanan> getModel_tb_makanan() {
        return makananList;
    }

    public void setMakananList(ArrayList<Model_tb_makanan> makananList) {
        this.makananList = makananList;
    }
}
