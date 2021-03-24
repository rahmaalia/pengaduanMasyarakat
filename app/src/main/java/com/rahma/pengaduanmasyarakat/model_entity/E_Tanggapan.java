package com.rahma.pengaduanmasyarakat.model_entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class E_Tanggapan {
    @SerializedName("id_tanggapan")
    @Expose
    private Integer idTanggapan;
    @SerializedName("id_pengaduan")
    @Expose
    private Integer idPengaduan;
    @SerializedName("tgl_tanggapan")
    @Expose
    private String tglTanggapan;
    @SerializedName("tanggapan")
    @Expose
    private String tanggapan;
    @SerializedName("id_petugas")
    @Expose
    private Integer idPetugas;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getIdTanggapan() {
        return idTanggapan;
    }

    public void setIdTanggapan(Integer idTanggapan) {
        this.idTanggapan = idTanggapan;
    }

    public Integer getIdPengaduan() {
        return idPengaduan;
    }

    public void setIdPengaduan(Integer idPengaduan) {
        this.idPengaduan = idPengaduan;
    }

    public String getTglTanggapan() {
        return tglTanggapan;
    }

    public void setTglTanggapan(String tglTanggapan) {
        this.tglTanggapan = tglTanggapan;
    }

    public String getTanggapan() {
        return tanggapan;
    }

    public void setTanggapan(String tanggapan) {
        this.tanggapan = tanggapan;
    }

    public Integer getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(Integer idPetugas) {
        this.idPetugas = idPetugas;
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
