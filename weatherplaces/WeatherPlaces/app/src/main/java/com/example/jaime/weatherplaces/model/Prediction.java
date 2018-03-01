package com.example.jaime.weatherplaces.model;

/**
 * Created by jaime on 20/02/2018.
 */

public class Prediction {

    private String description;
    private String places_id;

    public String getPlaces_id() {
        return places_id;
    }

    public void setPlaces_id(String places_id) {
        this.places_id = places_id;
    }

    public Prediction() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prediction that = (Prediction) o;

        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "description='" + description + '\'' +
                '}';
    }
}
