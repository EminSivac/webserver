# Einfacher Java Webserver (Aktueller Stand)

## Übersicht

Dieses Projekt ist ein einfacher HTTP-Webserver, der in Java mit Sockets implementiert wurde. Er kann statische HTML-Dateien ausliefern und einfache API-Anfragen zur Verwaltung von Benutzern verarbeiten.

Der Server zeigt, wie HTTP auf niedriger Ebene funktioniert – ohne die Verwendung von Frameworks.

---

## Funktionen

* Ausliefern statischer Dateien (`index.html`, `404.html`)
* Manuelle Verarbeitung von HTTP-Anfragen
* Unterstützung grundlegender Routen:

  * `GET /` → gibt die Startseite zurück
  * `GET /users` → gibt eine Liste von Benutzern (JSON) zurück
  * `POST /users` → fügt einen neuen Benutzer hinzu
* Senden von HTTP-Antworten mit Headern und Statuscodes

---

## Projektstruktur

* `SimpleWebServer.java` – Hauptimplementierung des Servers
* `index.html` – Startseite
* `404.html` – Fehlerseite

---

## Ausführen des Programms

### Voraussetzungen

* Java (JDK 8 oder höher)

### Schritte

1. Kompilieren:

   ```
   javac SimpleWebServer.java
   ```

2. Starten:

   ```
   java SimpleWebServer
   ```

3. Im Browser öffnen:

   ```
   http://localhost:8080
   ```

---

## API-Endpunkte

### GET /

Gibt die Startseite (`index.html`) zurück.

---

### GET /users

Gibt eine Liste von Benutzern im JSON-Format zurück.

Beispiel:

```
[
  {"name": "Alice"},
  {"name": "Bob"}
]
```

---

### POST /users

Fügt einen neuen Benutzer hinzu.

Beispiel mit curl:

```
curl -X POST http://localhost:8080/users -d "Alice"
```

---

## Einschränkungen

* Daten werden nur im Speicher gehalten (keine Persistenz)
* Unterstützt nur grundlegende HTTP-Funktionalität
* Single-Threaded (bearbeitet nur eine Anfrage gleichzeitig)

---

## Mögliche Erweiterungen

* Benutzeroberfläche mit HTML und JavaScript
* Weitere HTTP-Methoden unterstützen (PUT, DELETE)
* Daten in Datei oder Datenbank speichern
* Fehlerbehandlung
---
