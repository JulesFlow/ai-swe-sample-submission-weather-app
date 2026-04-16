package com.giuliana.weatherapp.service;

import com.giuliana.weatherapp.model.Language;

public class LocalizationService {

    private Language lang = Language.IT;

    public void setLanguage(Language lang) {
        this.lang = lang;
    }

    public Language getLanguage() {
        return lang;
    }

    public String label(String key) {
        return switch (lang) {
            case IT -> switch (key) {
                case "CITY" -> "CITTÀ";
                case "TEMP" -> "TEMP";
                case "WEATHER" -> "METEO";
                case "WIND" -> "VENTO";
                case "HUMIDITY" -> "UMIDITÀ";
                case "PRESSURE" -> "PRESSIONE";
                case "FORECAST" -> "PREVISIONI 7 GIORNI";
                case "MAX" -> "MAX";
                case "MIN" -> "MIN";
                case "DATE" -> "DATA";
                default -> key;
            };
            case EN -> switch (key) {
                case "CITY" -> "CITY";
                case "TEMP" -> "TEMP";
                case "WEATHER" -> "WEATHER";
                case "WIND" -> "WIND";
                case "HUMIDITY" -> "HUMIDITY";
                case "PRESSURE" -> "PRESSURE";
                case "FORECAST" -> "7-DAY FORECAST";
                case "MAX" -> "MAX";
                case "MIN" -> "MIN";
                case "DATE" -> "DATE";
                default -> key;
            };
        };
    }

    public String weatherDescription(int code) {
        return switch (lang) {
            case IT -> italian(code);
            case EN -> english(code);
        };
    }

    private String italian(int code) {
        return switch (code) {
            case 0 -> "☀️ Sereno";
            case 1, 2 -> "⛅ Parzialmente nuvoloso";
            case 3 -> "☁️ Coperto";
            case 45, 48 -> "🌫️ Nebbia";
            case 51, 53, 55 -> "🌦️ Pioviggine";
            case 61, 63, 65 -> "🌧️ Pioggia";
            case 71, 73, 75 -> "❄️ Neve";
            case 95 -> "⛈️ Temporale";
            case 96, 99 -> "⛈️🌨️ Temporale con grandine";
            default -> "🌍 Condizioni non disponibili";
        };
    }

    private String english(int code) {
        return switch (code) {
            case 0 -> "☀️ Clear";
            case 1, 2 -> "⛅ Partly cloudy";
            case 3 -> "☁️ Overcast";
            case 45, 48 -> "🌫️ Fog";
            case 51, 53, 55 -> "🌦️ Drizzle";
            case 61, 63, 65 -> "🌧️ Rain";
            case 71, 73, 75 -> "❄️ Snow";
            case 95 -> "⛈️ Thunderstorm";
            case 96, 99 -> "⛈️🌨️ Thunderstorm with hail";
            default -> "🌍 Conditions unavailable";
        };
    }
}
