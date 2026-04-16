package com.giuliana.weatherapp.model;

import java.util.List;

public class WeatherData {
    private final String city;
    private final double temperature;
    private final String weatherCode;
    private final String iconDescription;
    private final double windSpeed;
    private final double humidity;
    private final double pressure;
    private final List<ForecastDay> forecast7Days;

    public WeatherData(String city,
                       double temperature,
                       String weatherCode,
                       String iconDescription,
                       double windSpeed,
                       double humidity,
                       double pressure,
                       List<ForecastDay> forecast7Days) {
        this.city = city;
        this.temperature = temperature;
        this.weatherCode = weatherCode;
        this.iconDescription = iconDescription;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
        this.forecast7Days = forecast7Days;
    }

    public String getCity() { return city; }
    public double getTemperature() { return temperature; }
    public String getWeatherCode() { return weatherCode; }
    public String getIconDescription() { return iconDescription; }
    public double getWindSpeed() { return windSpeed; }
    public double getHumidity() { return humidity; }
    public double getPressure() { return pressure; }
    public List<ForecastDay> getForecast7Days() { return forecast7Days; }
}
