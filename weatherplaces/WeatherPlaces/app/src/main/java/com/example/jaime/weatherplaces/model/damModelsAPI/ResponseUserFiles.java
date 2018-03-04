package com.example.jaime.weatherplaces.model.damModelsAPI;

import java.util.List;

/**
 * Created by jaime on 04/03/2018.
 */

public class ResponseUserFiles {
    List<ResponseFile> userFiles;

    public ResponseUserFiles(List<ResponseFile> userFiles) {
        this.userFiles = userFiles;
    }

    public List<ResponseFile> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(List<ResponseFile> userFiles) {
        this.userFiles = userFiles;
    }
}
