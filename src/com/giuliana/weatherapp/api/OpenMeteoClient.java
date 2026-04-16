package com.giuliana.weatherapp.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoClient {

    private final HttpClient client = HttpClient.newHttpClient();

    public String geocode(String city) throws Exception {
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=10";
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
        String json = client.send(req, HttpResponse.BodyHandlers.ofString()).body();

        // Se ci sono più risultati, scegliamo quello più rilevante
        // 1) Italia se esiste
        // 2) Altrimenti il più popoloso
        if (json.contains("\"results\"")) {
            String[] entries = json.split("\\{");
            String best = null;
            int bestPop = -1;

            for (String e : entries) {
                if (!e.contains("\"name\"")) continue;

                boolean isItaly = e.contains("\"country_code\":\"IT\"");
                int pop = extractPopulation(e);

                if (isItaly) {
                    best = "{" + e;
                    break;
                }

                if (pop > bestPop) {
                    bestPop = pop;
                    best = "{" + e;
                }
            }

            if (best != null) {
                return "{\"results\":[" + best + "]}";
            }
        }

        return json;
    }

    private int extractPopulation(String block) {
        try {
            int idx = block.indexOf("\"population\":");
            if (idx == -1) return 0;

            int start = idx + 13;
            int end = block.indexOf(",", start);
            return Integer.parseInt(block.substring(start, end).trim());
        } catch (Exception e) {
            return 0;
        }
    }


    public String getCurrent(double lat, double lon) throws Exception {
        String url =
            "https://api.open-meteo.com/v1/forecast?latitude=" + lat +
            "&longitude=" + lon +
            "&current_weather=true&timezone=auto";
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getDaily(double lat, double lon) throws Exception {
        String url =
            "https://api.open-meteo.com/v1/forecast?latitude=" + lat +
            "&longitude=" + lon +
            "&daily=temperature_2m_max,temperature_2m_min,weathercode,time" +
            "&forecast_days=7" +
            "&timezone=auto";
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }


    public String getHourly(double lat, double lon) throws Exception {
        String url =
            "https://api.open-meteo.com/v1/forecast?latitude=" + lat +
            "&longitude=" + lon +
            "&hourly=windspeed_10m,relativehumidity_2m,surface_pressure" +
            "&timezone=auto";
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }
}
