package com.example.jaime.weatherplaces.model.damModelsAPI;

/**
 * Created by jaime on 04/03/2018.
 */

public class ResponseFile {
    private String url;
    private String title;
    private String coords;
    private String user_id;
    private String uploadDate;
    private String id;


    public ResponseFile(String url, String title,
                        String coords, String user_id,
                        String uploadDate, String id) {
        this.url = url;
        this.title = title;
        this.coords = coords;
        this.user_id = user_id;
        this.uploadDate = uploadDate;
        this.id = id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
