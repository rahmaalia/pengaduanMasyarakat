package com.rahma.pengaduanmasyarakat.apihelper;

import com.rahma.pengaduanmasyarakat.model_entity.M_Beranda;
import com.rahma.pengaduanmasyarakat.model_entity.M_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.M_ProsesPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.M_Selesai;
import com.rahma.pengaduanmasyarakat.model_entity.M_SelesaiPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.M_Tanggapan;
import com.rahma.pengaduanmasyarakat.model_entity.M_Verifikasi;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @POST("tambahFoto")
    Call<RequestBody> uploadFoto(@Part MultipartBody.Part part);

    @FormUrlEncoded
    @POST("tanggapan")
    Call<ResponseBody> inputTanggapan(@Field("id_pengaduan") int id_pengaduan,
                                      @Field("tgl_tanggapan") String tgl_tanggapan,
                                      @Field("tanggapan") String tanggapan,
                                      @Field("id_petugas") int id_petugas);

    @GET("getProses/{nik}")
    Call<M_Proses> getProses (@Path("nik") String nik);

    @GET("getSelesai/{nik}")
    Call<M_Selesai> getSelesai (@Path("nik") String nik);

    @GET("getSemua")
    Call<M_Beranda> getSemua ();

    @GET("delete/{id}")
    Call<ResponseBody> deletePengaduan(@Path("id") int id_pengaduan);

    @GET("getProsesPetugas")
    Call<M_ProsesPetugas> getProsesPetugas ();

    @GET("getSelesaiPetugas")
    Call<M_SelesaiPetugas> getSelesaiPetugas ();

    @GET("getVerifikasiPetugas")
    Call<M_Verifikasi> getVerifikasiPetugas ();

    @GET("getTanggapan/{id}")
    Call<M_Tanggapan> getTanggapan (@Path("id") int id_pengaduan);

    @FormUrlEncoded
    @PUT("updateStatus/{id}")
    Call<ResponseBody> updateStatus (@Path("id") int id_pengaduan,
                                    @Field("status") String status);
}
