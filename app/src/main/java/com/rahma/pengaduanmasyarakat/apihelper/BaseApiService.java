package com.rahma.pengaduanmasyarakat.apihelper;

import com.rahma.pengaduanmasyarakat.model_entity.M_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.M_Selesai;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("nik") String nik,
                                       @Field("nama") String nama,
                                       @Field("telp") String telp,
                                       @Field("username") String username,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("foto")
    Call<ResponseBody> inputLaporan(@Field("tgl_pengaduan") String tgl_pengaduan,
                                    @Field("nik") String nik,
                                    @Field("isi_laporan") String isi_laporan,
                                    @Field("foto") String foto);


    @Multipart
    @POST("addFoto")
    Call<RequestBody> uploadFoto(@Part MultipartBody.Part part);

    @GET("getProses/{nik}")
    Call<M_Proses> getProses (@Path("nik") String nik);

    @GET("getSelesai/{nik}")
    Call<M_Selesai> getSelesai (@Path("nik") String nik);
}
