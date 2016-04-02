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
