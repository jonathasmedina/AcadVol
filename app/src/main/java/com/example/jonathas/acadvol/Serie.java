package com.example.jonathas.acadvol;

import java.util.UUID;

public class Serie {
    String gpMusc;
    int numRep;
    int carga;
    String id;
    int volume;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Serie() {
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGpMusc() {
        return gpMusc;
    }

    public void setGpMusc(String gpMusc) {
        this.gpMusc = gpMusc;
    }

    public int getNumRep() {
        return numRep;
    }

    public void setNumRep(int numRep) {
        this.numRep = numRep;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }
}
