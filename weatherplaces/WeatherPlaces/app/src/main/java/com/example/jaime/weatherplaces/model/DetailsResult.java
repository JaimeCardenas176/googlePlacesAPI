package com.example.jaime.weatherplaces.model;

/**
 * Created by jaime on 27/02/2018.
 */

public class DetailsResult {
    private Result result;
    private String status;

    public DetailsResult() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetailsResult that = (DetailsResult) o;

        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (status != null ? status.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "DetailsResult{" +
                "result=" + result +
                ", status='" + status + '\'' +
                '}';
    }
}
