package com.rahma.pengaduanmasyarakat.model_entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class M_Tanggapan {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<E_Tanggapan> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<E_Tanggapan> getData() {
        return data;
    }

    public void setData(List<E_Tanggapan> data) {
        this.data = data;
    }
}
