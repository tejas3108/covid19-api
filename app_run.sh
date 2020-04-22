gradle clean build

java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -Dspring.profiles.active=local -Dspring.profiles.active=local -Dspring.application.json="{\"app\":{\"client\":\"covid\"}}" -jar build/libs/covid-19-api-0.0.1-SNAPSHOT.jar
