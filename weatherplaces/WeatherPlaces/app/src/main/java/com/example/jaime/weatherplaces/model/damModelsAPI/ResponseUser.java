package com.example.jaime.weatherplaces.model.damModelsAPI;

import android.media.Image;

/**
 * Created by jaime on 02/03/2018.
 */

public class ResponseUser {
    private String email;
    private String password;
    private String displayName;
    private Image photo;
    private String token;

    public ResponseUser(String email, String password, String displayName, Image photo, String token) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.photo = photo;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
