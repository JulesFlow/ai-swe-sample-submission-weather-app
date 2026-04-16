package com.giuliana.weatherapp;

import com.giuliana.weatherapp.model.Language;
import com.giuliana.weatherapp.service.CacheService;
import com.giuliana.weatherapp.service.LocalizationService;
import com.giuliana.weatherapp.service.WeatherService;
import com.giuliana.weatherapp.ui.ConsoleUI;

public class App {
    public static void main(String[] args) {
        LocalizationService localization = new LocalizationService();
        CacheService cache = new CacheService();
        WeatherService service = new WeatherService(localization, cache);
        ConsoleUI ui = new ConsoleUI(service, localization);

        // lingua di default IT, la UI chiederà se cambiarla
        localization.setLanguage(Language.IT);

        ui.start();
    }
}
