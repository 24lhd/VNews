package com.duongstudio.obj;

public class Date {
    public String phut;
    public String gio;
    public String ngay;
    public String thang;
    public String nam;

    @Override
    public String toString() {
        return "pubDate{" +
                "phut='" + phut + '\'' +
                ", gio='" + gio + '\'' +
                ", ngay='" + ngay + '\'' +
                ", thang='" + thang + '\'' +
                ", nam='" + nam + '\'' +
                '}';
    }

    public String getPhut() {
        return phut;
    }

    public void setPhut(String phut) {
        this.phut = phut;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public Date(String phut, String gio, String ngay, String thang, String nam) {
        this.phut = phut;
        this.gio = gio;
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
    }


}