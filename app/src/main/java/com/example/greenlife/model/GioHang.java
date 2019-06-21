package com.example.greenlife.model;

public class GioHang  {
    private int maSP;
    private String tenSP;
    private long giaSP;
    public String hinhSP;
    public int soluongSP;

    public GioHang(int maSP, String tenSP, long giaSP, String hinhSP, int soluongSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhSP = hinhSP;
        this.soluongSP = soluongSP;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public long getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(long giaSP) {
        this.giaSP = giaSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public int getSoluongSP() {
        return soluongSP;
    }

    public void setSoluongSP(int soluongSP) {
        this.soluongSP = soluongSP;
    }
}
