
package model.forecastOpenWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastInfo {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Long cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherList> list = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ForecastInfo() {
    }

    /**
     * 
     * @param message
     * @param cnt
     * @param cod
     * @param list
     */
    public ForecastInfo(String cod, Double message, Long cnt, java.util.List<WeatherList> list) {
        super();
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    public java.util.List<WeatherList> getList() {
        return list;
    }

    public void setList(java.util.List<WeatherList> list) {
        this.list = list;
    }

}
