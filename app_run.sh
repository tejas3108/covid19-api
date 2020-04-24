gradle clean build

java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -Dspring.profiles.active=local -Dspring.application.json="{\"app\":{\"client\":\"covid\"}}" -jar build/libs/*
