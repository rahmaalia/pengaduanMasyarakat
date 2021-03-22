package com.rahma.pengaduanmasyarakat.model_entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class M_ProsesPetugas {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<E_ProsesPetugas> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<E_ProsesPetugas> getData() {
        return data;
    }

    public void setData(List<E_ProsesPetugas> data) {
        this.data = data;
    }
}
