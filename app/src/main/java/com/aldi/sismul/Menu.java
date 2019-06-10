package com.aldi.sismul;

public class Menu {

    private String nama;
    private Double data;

    public Menu(String nama, Double data) {
        this.nama = nama;
        this.data = data;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

}
