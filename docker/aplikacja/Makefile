APP_PATH= /var/lib/docker/volumes/6aac12ffa2f14f3a80148f5ba9b1b2740006b22a072469717e0554402bc8a860/_data/jobs/fiszki-multi-branch/branches/master/workspace/build/libs/gs-serving-web-content-0.1.0.jar
all: app.jar

app.jar: ${APP_PATH}
        cp $^ ~/aplikacja/app.jar
        docker-compose build
        docker-compose up -d

