package com.giuliana.weatherapp.model;

public class ForecastDay {
    private final String date;
    private final double tempMax;
    private final double tempMin;
    private final int weatherCode;

    public ForecastDay(String date, double tempMax, double tempMin, int weatherCode) {
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.weatherCode = weatherCode;
    }

    public String getDate() { return date; }
    public double getTempMax() { return tempMax; }
    public double getTempMin() { return tempMin; }
    public int getWeatherCode() { return weatherCode; }
}
