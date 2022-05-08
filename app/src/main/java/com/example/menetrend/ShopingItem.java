package com.example.menetrend;

public class ShopingItem {

    private String id;
    private String jaratszam;//name
    private String indulasok;
    private String megallok;


    public ShopingItem(String jaratszam, String indulasok, String megallok) {
        this.jaratszam = jaratszam;
        this.indulasok = indulasok;
        this.megallok = megallok;
    }

    public ShopingItem() {}

    public String getJaratszam() {
        return jaratszam;
    }

    public void setJaratszam(String jaratszam) {
        this.jaratszam = jaratszam;
    }

    public String getIndulasok() {
        return indulasok;
    }

    public void setIndulasok(String indulasok) {
        this.indulasok = indulasok;
    }

    public String getMegallok() {
        return megallok;
    }

    public void setMegallok(String megallok) {
        this.megallok = megallok;
    }

    public String _getId(){ return id; }

    public void setId(String id){ this.id = id; }
}
