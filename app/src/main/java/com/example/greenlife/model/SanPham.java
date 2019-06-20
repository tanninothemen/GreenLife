package com.example.greenlife.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int id;
    public String tenSanPham;
    public Integer giaSanPham;
    public String hinhSanPham;
    public String motaSanPham;
    public int idSanPham;

    public SanPham(int id, String tenSanPham, Integer giaSanPham, String hinhSanPham, String motaSanPham, int idSanPham) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.hinhSanPham = hinhSanPham;
        this.motaSanPham = motaSanPham;
        this.idSanPham = idSanPham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Integer getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(Integer giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getHinhSanPham() {
        return hinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        this.hinhSanPham = hinhSanPham;
    }

    public String getMotaSanPham() {
        return motaSanPham;
    }

    public void setMotaSanPham(String motaSanPham) {
        this.motaSanPham = motaSanPham;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }
}