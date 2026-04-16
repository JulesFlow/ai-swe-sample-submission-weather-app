package com.giuliana.weatherapp.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.giuliana.weatherapp.model.ForecastDay;
import com.giuliana.weatherapp.model.Language;
import com.giuliana.weatherapp.model.WeatherData;
import com.giuliana.weatherapp.service.LocalizationService;
import com.giuliana.weatherapp.service.WeatherService;

public class ConsoleUI {

    private final WeatherService service;
    private final LocalizationService localization;

    public ConsoleUI(WeatherService service, LocalizationService localization) {
        this.service = service;
        this.localization = localization;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Lingua / Language (IT/EN): ");
        String langInput = scanner.nextLine().trim().toUpperCase();
        if (langInput.equals("EN")) {
            localization.setLanguage(Language.EN);
        } else {
            localization.setLanguage(Language.IT);
        }

        System.out.print("Inserisci una o più città separate da virgola: ");
        String input = scanner.nextLine();

        String[] cities = input.split(",");
        for (int i = 0; i < cities.length; i++) {
            cities[i] = cities[i].trim();
        }
        Arrays.sort(cities, String.CASE_INSENSITIVE_ORDER);

        // tabella meteo corrente
        System.out.println("\n---------------------------------------------------------------");
        System.out.println(String.format("%-15s %-10s %-20s %-8s %-10s %-10s",
            localization.label("CITY"),
            localization.label("TEMP"),
            localization.label("WEATHER"),
            localization.label("WIND"),
            localization.label("HUMIDITY"),
            localization.label("PRESSURE")));
        System.out.println("---------------------------------------------------------------");

        WeatherData[] results = new WeatherData[cities.length];

        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            if (city.isEmpty()) continue;

            try {
                WeatherData data = service.getWeather(city);
                results[i] = data;

                if (data == null) {
                    System.out.println(String.format("%-15s %-10s %-20s %-8s %-10s %-10s",
                        city, "N/D", "N/D", "N/D", "N/D", "N/D"));
                    continue;
                }

                String temp = String.format("%.1f°C", data.getTemperature());
                String wind = String.format("%.1f", data.getWindSpeed());
                String hum = String.format("%.0f%%", data.getHumidity());
                String pres = String.format("%.0f", data.getPressure());

                System.out.println(String.format("%-15s %-10s %-20s %-8s %-10s %-10s",
                    data.getCity(),
                    temp,
                    data.getIconDescription(),
                    wind,
                    hum,
                    pres));

            } catch (Exception e) {
                System.out.println(String.format("%-15s %-10s %-20s %-8s %-10s %-10s",
                    city, "ERR", "ERR", "ERR", "ERR", "ERR"));
            }
        }

        System.out.println("---------------------------------------------------------------");

        // previsioni 7 giorni per ogni città
        for (WeatherData data : results) {
            if (data == null) continue;

            List<ForecastDay> forecast = data.getForecast7Days();
            if (forecast == null || forecast.isEmpty()) continue;

            System.out.println("\n" + localization.label("FORECAST") + " - " + data.getCity());
            System.out.println("-----------------------------------------------");
            System.out.println(String.format("%-12s %-8s %-8s %-20s",
                localization.label("DATE"),
                localization.label("MAX"),
                localization.label("MIN"),
                localization.label("WEATHER")));
            System.out.println("-----------------------------------------------");

            for (ForecastDay day : forecast) {
                String max = String.format("%.1f°C", day.getTempMax());
                String min = String.format("%.1f°C", day.getTempMin());
                String desc = localization.weatherDescription(day.getWeatherCode());

                System.out.println(String.format("%-12s %-8s %-8s %-20s",
                    day.getDate(),
                    max,
                    min,
                    desc));
            }

            System.out.println("-----------------------------------------------");
        }

        //scanner.close();
    }
}
