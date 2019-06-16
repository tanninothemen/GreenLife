package com.example.greenlife.model;

public class LoaiSP {
    public int id;
    public String TenLoaiSP;
    public String HinhAnhLoaiSP;

    public LoaiSP(int id, String tenLoaiSP, String hinhAnhLoaiSP) {
        this.id = id;
        TenLoaiSP = tenLoaiSP;
        HinhAnhLoaiSP = hinhAnhLoaiSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSP() {
        return TenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        TenLoaiSP = tenLoaiSP;
    }

    public String getHinhAnhLoaiSP() {
        return HinhAnhLoaiSP;
    }

    public void setHinhAnhLoaiSP(String hinhAnhLoaiSP) {
        HinhAnhLoaiSP = hinhAnhLoaiSP;
    }
}
