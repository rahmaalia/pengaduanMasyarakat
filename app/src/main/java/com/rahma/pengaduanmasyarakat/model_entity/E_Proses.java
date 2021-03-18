package com.rahma.pengaduanmasyarakat.model_entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class E_Proses {
    @SerializedName("id_pengaduan")
    @Expose
    private Integer idPengaduan;
    @SerializedName("tgl_pengaduan")
    @Expose
    private String tglPengaduan;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("isi_laporan")
    @Expose
    private String isiLaporan;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getIdPengaduan() {
        return idPengaduan;
    }

    public void setIdPengaduan(Integer idPengaduan) {
        this.idPengaduan = idPengaduan;
    }

    public String getTglPengaduan() {
        return tglPengaduan;
    }

    public void setTglPengaduan(String tglPengaduan) {
        this.tglPengaduan = tglPengaduan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getIsiLaporan() {
        return isiLaporan;
    }

    public void setIsiLaporan(String isiLaporan) {
        this.isiLaporan = isiLaporan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
