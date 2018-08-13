package com.unbaja.inggi.bengkos.model;

/**
 * Created by sigit on 06/07/2018.
 */

public class MarkerData {
    private boolean isBengkel;
    private long idBengkel;
    private String namaBengkel;
    private String hariBuka;
    private String hariTutup;
    private String jamBuka;
    private String jamTutup;

    public MarkerData(boolean isBengkel) {
        this.setBengkel(isBengkel);
    }

    public MarkerData(boolean isBengkel, long idBengkel, String namaBengkel,
                      String hariBuka, String hariTutup, String jamBuka, String jamTutup) {
        this.setBengkel(isBengkel);
        this.setIdBengkel(idBengkel);
        this.setNamaBengkel(namaBengkel);
        this.setHariBuka(hariBuka);
        this.setHariTutup(hariTutup);
        this.setJamBuka(jamBuka);
        this.setJamTutup(jamTutup);
    }


    public boolean isBengkel() {
        return isBengkel;
    }

    public void setBengkel(boolean bengkel) {
        isBengkel = bengkel;
    }

    public long getIdBengkel() {
        return idBengkel;
    }

    public void setIdBengkel(long idBengkel) {
        this.idBengkel = idBengkel;
    }

    public String getNamaBengkel() {
        return namaBengkel;
    }

    public void setNamaBengkel(String namaBengkel) {
        this.namaBengkel = namaBengkel;
    }


    public String getHariBuka() {
        return hariBuka;
    }

    public void setHariBuka(String hariBuka) {
        this.hariBuka = hariBuka;
    }

    public String getHariTutup() {
        return hariTutup;
    }

    public void setHariTutup(String hariTutup) {
        this.hariTutup = hariTutup;
    }

    public String getJamBuka() {
        return jamBuka;
    }

    public void setJamBuka(String jamBuka) {
        this.jamBuka = jamBuka;
    }

    public String getJamTutup() {
        return jamTutup;
    }

    public void setJamTutup(String jamTutup) {
        this.jamTutup = jamTutup;
    }
}
