package com.example.jaime.weatherplaces.model;

/**
 * Created by jaime on 27/02/2018.
 */

public class Foto {
    private String urlfoto;

    public Foto() {
    }

    public String getUrlFoto() {
        return urlfoto;
    }

    public void setUrlFoto(String photo_reference) {
        this.urlfoto = photo_reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Foto foto = (Foto) o;

        return urlfoto != null ? urlfoto.equals(foto.urlfoto) : foto.urlfoto == null;
    }

    @Override
    public int hashCode() {
        return urlfoto != null ? urlfoto.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photo_reference='" + urlfoto + '\'' +
                '}';
    }
}
