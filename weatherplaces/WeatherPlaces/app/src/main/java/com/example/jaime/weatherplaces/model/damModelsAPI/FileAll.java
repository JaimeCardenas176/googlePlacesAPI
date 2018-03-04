package com.example.jaime.weatherplaces.model.damModelsAPI;

/**
 * Created by jaime on 04/03/2018.
 */

public class FileAll {
    private String url;
    private String title;
    private String coords;
    private ResponseUser User;
    private  String uploadDate;
    private String _id;

    public FileAll(String url, String title,
                   String coords, ResponseUser user,
                   String uploadDate, String _id) {
        this.url = url;
        this.title = title;
        this.coords = coords;
        User = user;
        this.uploadDate = uploadDate;
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public ResponseUser getUser() {
        return User;
    }

    public void setUser(ResponseUser user) {
        User = user;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

