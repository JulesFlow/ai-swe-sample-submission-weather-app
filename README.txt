🌤️ WeatherApp – Applicazione Meteo in Java

Meteo attuale + Previsioni a 7 giorni · Multilingua · Cache · Open‑Meteo API

WeatherApp è un’applicazione Java da console che permette di ottenere:

    Meteo attuale

    Previsioni a 7 giorni

    Vento, umidità, pressione

    Icone meteo testuali

    Supporto multilingua (IT/EN)

    Input di più città contemporaneamente

    Ordinamento alfabetico automatico

    Cache locale per evitare chiamate duplicate

Il tutto utilizzando le API pubbliche e gratuite di Open‑Meteo.
🚀 Funzionalità principali
✔ Meteo attuale

Per ogni città vengono mostrati:

    Temperatura

    Condizioni meteo (con icona)

    Velocità del vento

    Umidità

    Pressione

✔ Previsioni a 7 giorni

Per ogni città vengono mostrate:

    Data

    Temperatura massima

    Temperatura minima

    Condizioni meteo (con icona)

✔ Multilingua (IT/EN)

L’utente può scegliere la lingua all’avvio.
✔ Input multiplo

Esempio:
Codice

Milano, Roma, Napoli

✔ Ordinamento alfabetico

Le città vengono automaticamente ordinate prima dell’elaborazione.
✔ Cache locale

Se una città è già stata richiesta, i dati vengono recuperati dalla cache senza richiamare l’API.
✔ Nessuna libreria esterna

Parsing JSON implementato manualmente, compatibile con qualsiasi ambiente Java.
🛠️ Requisiti

    Java 17 o superiore

    Qualsiasi IDE (Eclipse, IntelliJ, VS Code)

    Connessione Internet

📁 Struttura del progetto
Codice

WeatherApp/
 └── src/
     └── com.giuliana.weatherapp/
         ├── App.java
         ├── api/
         │    └── OpenMeteoClient.java
         ├── model/
         │    ├── WeatherData.java
         │    ├── ForecastDay.java
         │    └── Language.java
         ├── service/
         │    ├── WeatherService.java
         │    ├── CacheService.java
         │    └── LocalizationService.java
         └── ui/
              └── ConsoleUI.java

🌐 API utilizzate
1. Geocoding
Codice

https://geocoding-api.open-meteo.com/v1/search?name=<city>

2. Meteo attuale
Codice

https://api.open-meteo.com/v1/forecast?latitude=<lat>&longitude=<lon>&current_weather=true&timezone=auto

3. Previsioni 7 giorni
Codice

https://api.open-meteo.com/v1/forecast?latitude=<lat>&longitude=<lon>
&daily=temperature_2m_max,temperature_2m_min,weathercode,time
&forecast_days=7
&timezone=auto

4. Dati orari (vento, umidità, pressione)
Codice

https://api.open-meteo.com/v1/forecast?latitude=<lat>&longitude=<lon>
&hourly=windspeed_10m,relativehumidity_2m,surface_pressure
&timezone=auto

▶️ Come eseguire l’app
Compilazione
Codice

javac -d bin src/com/giuliana/weatherapp/**/*.java

Esecuzione
Codice

java -cp bin com.giuliana.weatherapp.App

Oppure semplicemente Run → App.java da Eclipse.
🧑‍💻 Utilizzo

All’avvio l’app chiede:
Codice

Lingua / Language (IT/EN):

Poi:
Codice

Inserisci una o più città separate da virgola:

Esempio:
Codice

Milano, Roma, Napoli

Output meteo attuale
Codice

---------------------------------------------------------------
CITTÀ           TEMP       METEO                VENTO    UMIDITÀ    PRESSIONE 
---------------------------------------------------------------
Milano          20.2°C     ⛅ Parzialmente nuvoloso 17.7     80%        944
Roma            22.6°C     ☀️ Sereno            3.1      60%        1018
Napoli          21.0°C     ☁️ Coperto           7.8      64%        1007
---------------------------------------------------------------

Output previsioni 7 giorni
Codice

PREVISIONI 7 GIORNI - Milano
-----------------------------------------------
DATA         MAX      MIN      METEO
-----------------------------------------------
2026-04-17   22.1°C   14.3°C   ⛅ Parzialmente nuvoloso
2026-04-18   23.0°C   15.1°C   ☀️ Sereno
...
-----------------------------------------------

🧠 Come funziona internamente
1. Geocoding

Ricava latitudine e longitudine della città.
2. Chiamate API separate

Tre chiamate indipendenti garantiscono stabilità:

    meteo attuale

    previsioni giornaliere

    dati orari (vento/umidità/pressione)

3. Parsing JSON manuale

Nessuna libreria esterna:
il parsing è implementato tramite funzioni robuste che estraggono solo i valori necessari.
4. Cache locale

I risultati vengono salvati in memoria per evitare chiamate duplicate.
5. Multilingua

Tutte le etichette e le descrizioni meteo passano da LocalizationService.
🔮 Possibili estensioni future

    Colori ANSI nella console

    Esportazione CSV

    Interfaccia grafica JavaFX

    Supporto per più lingue

    Previsioni orarie

    Modalità “monitoraggio continuo”

📜 Licenza

Puoi usare, modificare e distribuire liberamente questo progetto.
❤️ Autrice

Progetto sviluppato da Giuliana, con supporto tecnico passo‑passo.