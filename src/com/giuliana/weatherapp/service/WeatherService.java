package com.giuliana.weatherapp.service;

import java.util.ArrayList;
import java.util.List;

import com.giuliana.weatherapp.api.OpenMeteoClient;
import com.giuliana.weatherapp.model.ForecastDay;
import com.giuliana.weatherapp.model.WeatherData;

public class WeatherService {

    private final OpenMeteoClient client = new OpenMeteoClient();
    private final LocalizationService localization;
    private final CacheService cache;

    public WeatherService(LocalizationService localization, CacheService cache) {
        this.localization = localization;
        this.cache = cache;
    }

    public WeatherData getWeather(String city) throws Exception {

        if (cache.contains(city)) {
            return (WeatherData) cache.get(city);
        }

        // 1) Geocoding
        String geoJson = client.geocode(city);
        if (!geoJson.contains("\"latitude\"")) return null;

        double lat = extractDouble(geoJson, "\"latitude\":");
        double lon = extractDouble(geoJson, "\"longitude\":");

        // 2) Meteo corrente
        String currentJson = client.getCurrent(lat, lon);
        if (!currentJson.contains("\"current_weather\"")) return null;

        int cwIndex = currentJson.indexOf("\"current_weather\"");
        int cwStart = currentJson.indexOf("{", cwIndex);
        int cwEnd = currentJson.indexOf("}", cwStart);
        String cw = currentJson.substring(cwStart, cwEnd + 1);

        double temperature = extractDouble(cw, "\"temperature\":");
        int weatherCode = (int) extractDouble(cw, "\"weathercode\":");
        double wind = extractDouble(cw, "\"windspeed\":");

        String iconDescription = localization.weatherDescription(weatherCode);

        // 3) Previsioni 7 giorni
        String dailyJson = client.getDaily(lat, lon);
        List<ForecastDay> forecast = extractForecast(dailyJson);

        // 4) Hourly (umidità, pressione)
        String hourlyJson = client.getHourly(lat, lon);
        double humidity = extractFirstArrayValue(hourlyJson, "\"relativehumidity_2m\":[");
        double pressure = extractFirstArrayValue(hourlyJson, "\"surface_pressure\":[");

        WeatherData data = new WeatherData(
            city,
            temperature,
            String.valueOf(weatherCode),
            iconDescription,
            wind,
            humidity,
            pressure,
            forecast
        );

        cache.put(city, data);
        return data;
    }

    // --- Forecast parsing ---
    private List<ForecastDay> extractForecast(String json) {
        List<ForecastDay> list = new ArrayList<>();

        if (!json.contains("\"daily\"")) return list;

        int start = json.indexOf("\"daily\"");
        start = json.indexOf("{", start);
        int end = json.indexOf("}", start);
        String daily = json.substring(start, end + 1);

        String[] dates = extractArray(daily, "\"time\":[");
        String[] maxs = extractArray(daily, "\"temperature_2m_max\":[");
        String[] mins = extractArray(daily, "\"temperature_2m_min\":[");
        String[] codes = extractArray(daily, "\"weathercode\":[");

        int len = Math.min(Math.min(dates.length, maxs.length), Math.min(mins.length, codes.length));
        int days = Math.min(len, 7);

        for (int i = 0; i < days; i++) {
            String date = stripQuotes(dates[i]);
            double tMax = parseDoubleSafe(maxs[i]);
            double tMin = parseDoubleSafe(mins[i]);
            int code = (int) parseDoubleSafe(codes[i]);

            list.add(new ForecastDay(date, tMax, tMin, code));
        }

        return list;
    }

    // --- Helpers ---
    private double extractDouble(String text, String key) {
        int index = text.indexOf(key);
        if (index == -1) return 0;

        int start = index + key.length();
        while (start < text.length() &&
              !((text.charAt(start) >= '0' && text.charAt(start) <= '9') || text.charAt(start) == '-')) {
            start++;
        }

        int end = start;
        while (end < text.length() &&
              ((text.charAt(end) >= '0' && text.charAt(end) <= '9') || text.charAt(end) == '.' || text.charAt(end) == '-')) {
            end++;
        }

        return Double.parseDouble(text.substring(start, end));
    }

    private double extractFirstArrayValue(String text, String key) {
        int index = text.indexOf(key);
        if (index == -1) return 0;

        int start = index + key.length();
        while (start < text.length() &&
              !((text.charAt(start) >= '0' && text.charAt(start) <= '9') || text.charAt(start) == '-')) {
            start++;
        }

        int end = start;
        while (end < text.length() &&
              ((text.charAt(end) >= '0' && text.charAt(end) <= '9') || text.charAt(end) == '.' || text.charAt(end) == '-')) {
            end++;
        }

        return Double.parseDouble(text.substring(start, end));
    }

    private String[] extractArray(String text, String key) {
        int index = text.indexOf(key);
        if (index == -1) return new String[0];

        int start = index + key.length();
        int end = text.indexOf("]", start);
        if (end == -1) return new String[0];

        return text.substring(start, end).split(",");
    }

    private String stripQuotes(String s) {
        s = s.trim();
        if (s.startsWith("\"")) s = s.substring(1);
        if (s.endsWith("\"")) s = s.substring(0, s.length() - 1);
        return s;
    }

    private double parseDoubleSafe(String s) {
        try { return Double.parseDouble(s.trim()); }
        catch (Exception e) { return 0; }
    }
}
