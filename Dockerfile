#docker build --build-arg env_file_name=application-local.properties --build-arg github_token=$GITHUB_ACCESS_TOKEN --build-arg github_user=$GITHUB_ACCESS_USER --build-arg passphrase=$GPG_PASSPHRASE -t covid19-api .
# FOR LOCAL RUN: docker build --build-arg passphrase=$GPG_PASSPHRASE -t covid19-api .

FROM gradle:jdk11 as builder
ARG github_token
ARG github_user
WORKDIR /
COPY . .
# ENV GITHUB_ACCESS_TOKEN ${github_token}
# ENV GITHUB_ACCESS_USER ${github_user}
RUN gradle clean build --refresh-dependencies

#decrypt container
FROM builder as service_key
ARG passphrase
ARG creds_file_name=service_account
WORKDIR /
ENV PASSPHRASE ${passphrase}
COPY --from=builder /src/main/resources/${creds_file_name}.gpg .
RUN apt-get update && apt-get -y install gnupg2
RUN gpg --batch --output /opt/${creds_file_name}.json --passphrase "${PASSPHRASE}" --decrypt ${creds_file_name}.gpg

# runtime container
FROM gradle:jdk11
ARG env_file_name=application-local.properties
ARG creds_file_name=service_account
ARG creds_file_loc=/opt/resources/${creds_file_name}.json
ENV ENV_FILE_NAME ${env_file_name}
ENV GOOGLE_APPLICATION_CREDENTIALS ${creds_file_loc}
WORKDIR /
COPY --from=service_key /opt/${creds_file_name}.json /opt/resources/
COPY --from=builder /src/main/resources/country-codes.csv /opt/resources/
COPY --from=builder /build/libs/covid-19-api-0.0.1-SNAPSHOT.jar /jar/covid-19-api-0.0.1.jar
COPY --from=builder /src/main/resources/$ENV_FILE_NAME /opt/resources/
EXPOSE 18001
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Dspring.application.json="{\"app\":{\"client\":\"covid\"}}" -Dspring.profiles.active=local -Dspring.config.location=file:/opt/resources/$ENV_FILE_NAME -Djava.security.egd=file:/dev/./urandom -jar /jar/covid-19-api-0.0.1.jar