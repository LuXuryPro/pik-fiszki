# Opis zaplecza projektowego

## Gradle
### Przeładowanie projektu w IntelliJ IDEA
Zawsze gdy zmieni się plik build.gradle trzeba ponownie go przeładować
w IntelliJ IDEA. Bez tego nie będzie działać uzupełnianie składni i
mogą się pojawić problemy z kompilowaniem i sprawdzaniem błędów w kodzie
w czasie pisania.
```
View -> Tool Windows -> Gradle
```
W nowym oknie naciskami na pierwszy niebieski guzik z lewej (↻)

## CI
### Jenkis
Jenkis jest zainstalowany na serwerze pik01. Do jego odpalenia jest używany
docker. Jest domyślnie wystawiony na porcie 8080.

##### Zestawienie połączenia przez tunel ssh
- poprzez MobaXterm
- lub komendą w terminalu
```bash
ssh -NL 5555:pik01:8080 user@galera.ii.pw.edu.pl
```

##### Uruchomienie
```bash
sudo docker start myjenkins
```

##### Wgrane pluginy
- cobertura - podgląd pokrycia kodu testami jest dostępny ze strony projektu na
Jenkis
- gradle
- github

### Travis
Travis jest zintegrowany z repozytroim na github. Wyniku budowania są dostępne
pod [linkiem](https://travis-ci.org/LuXuryPro/pik-fiszki)

## Docker


## Spring
### Tutorials
- [spring.io](https://spring.io/guides)

## IntelliJ IDEA
### Checkstyle
Aby używać ujednoliconego formatowania kodu w IntelliJ IDEA należy
wykonać:

#### Instalacja
1. File -> Settings -> Plugins -> Browse Repositories
2. W pole wyszukiwania wpisać Checkstyle-IDEA
3. Zainstalować plugin

#### Konfiguracja
1. File -> Settings -> Other Settings -> Checkstyle
2. Configuration File -> +
3. Description: pik
4. Use local configuration file
5. File config/checkstyle/checkstyle.xml
6. OK

#### Używanie
1. View -> Tool Windows -> Checkstyle
2. Rules: pik
3. Wybieramy plik java do przeskanowania lub odpalamy na całym projekcie

## Bug tracker
### Mantisbt
#### Zestawienie połączenia
```bash
ssh -NL 8989:pik01:8989 user@galera.ii.pw.edu.pl
```

#### Format commitu który automatycznie zamknie issue
```
/(?:fixe?d?s?|resolved?s?)+\s*:?\s+(?:#(?:\d+)[,\.\s]*)+/i
```
