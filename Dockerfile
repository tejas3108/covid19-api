#docker build --build-arg env_file_name=application-local.properties --build-arg github_token=$GITHUB_ACCESS_TOKEN --build-arg github_user=$GITHUB_ACCESS_USER -t covid19-api .
# FOR LOCAL RUN: docker build -t covid19-api .
FROM gradle:jdk11 as builder
WORKDIR /
COPY . .
ARG github_token
ARG github_user
ARG env_file_name=application-local.properties
ARG creds_file_name=COVID19-API-BIGQUERY.json
ARG creds_file_loc=/opt/resources/${creds_file_name}
# ENV GITHUB_ACCESS_TOKEN ${github_token}
# ENV GITHUB_ACCESS_USER ${github_user}
RUN gradle clean build --refresh-dependencies

# runtime container
# pass in all the files needed for running the JAR
WORKDIR /
COPY /src/main/resources/COVID19-API-BIGQUERY.json /opt/resources/
COPY /src/main/resources/country-codes.csv /opt/resources/
ENV ENV_FILE_NAME ${env_file_name}
ENV GOOGLE_APPLICATION_CREDENTIALS ${creds_file_loc}
FROM builder
COPY --from=builder /build/libs/covid-19-api-0.0.1-SNAPSHOT.jar /jar/covid-19-api-0.0.1.jar
EXPOSE 18001
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Dspring.application.json="{\"app\":{\"client\":\"covid\"}}" -Dspring.profiles.active=local -Dspring.config.location=file:/src/main/resources/$ENV_FILE_NAME -Djava.security.egd=file:/dev/./urandom -jar /jar/covid-19-api-0.0.1.jar