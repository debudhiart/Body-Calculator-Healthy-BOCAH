package id.budhiarta.praktikumprogmob;

import java.util.ArrayList;

import id.budhiarta.praktikumprogmob.model.Model_tb_makanan;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MakananAPI {

    @GET("makanan")
    Call<ArrayList<Model_tb_makanan>> getFood();

    @FormUrlEncoded
    @POST("makanan/tambah")
    Call<Model_tb_makanan> insertMakananAPI(
            @Field("nama_makanan") String inputNamaMakanan,
            @Field("satuan") String inputSatuan,
            @Field("kalori") Integer inputKalori,
            @Field("protein") Integer inputProtein,
            @Field("lemak") Integer inputLemak
    );

    @FormUrlEncoded
    @POST("/makanan/{id}/update")
    Call<Model_tb_makanan> updatMakananAPI(
      @Path("id") Integer idMakanan,
      @Field("nama_makanan") String inputNamaMakanan,
      @Field("satuan") String inputSatuan,
      @Field("kalori") Integer inputKalori,
      @Field("protein") Integer inputProtein,
      @Field("lemak") Integer inputLemak
    );

}
