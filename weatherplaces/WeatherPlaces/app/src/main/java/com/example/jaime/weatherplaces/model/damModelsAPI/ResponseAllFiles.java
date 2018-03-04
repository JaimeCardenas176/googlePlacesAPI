package com.example.jaime.weatherplaces.model.damModelsAPI;

import java.util.List;

/**
 * Created by jaime on 04/03/2018.
 */

public class ResponseAllFiles {
    private List<FileAll> allFiles;

    public ResponseAllFiles(List<FileAll> allFiles) {
        this.allFiles = allFiles;
    }

    public List<FileAll> getAllFiles() {
        return allFiles;
    }

    public void setAllFiles(List<FileAll> allFiles) {
        this.allFiles = allFiles;
    }
}
