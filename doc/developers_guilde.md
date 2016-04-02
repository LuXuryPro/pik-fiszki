# Opis zaplecza projektowego

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
