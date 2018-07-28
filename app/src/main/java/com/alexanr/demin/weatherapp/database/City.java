package com.alexanr.demin.weatherapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = @Index(value = "name", unique = true))
public class City {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int temperature;
    private int maxTemperature;
    private int minTemperature;
    private int humidity;
    private int pressure;
    private String weatherParams;

    public String getWeatherParams() {
        return weatherParams;
    }

    public void setWeatherParams(String weatherParams) {
        this.weatherParams = weatherParams;
    }

    private String lastUpd;

    public String getLastUpd() {
        return lastUpd;
    }

    public void setLastUpd(String lastUpd) {
        this.lastUpd = lastUpd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
}
