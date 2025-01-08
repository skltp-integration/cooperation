FROM eclipse-temurin:17-jre-alpine

ENV BASE_DIR=/opt/cooperation/ \
    APP_USER=cooperation

RUN adduser -D -u 1000 -h ${BASE_DIR} ${APP_USER}

ADD target/cooperation-*jar ${BASE_DIR}/app.jar

WORKDIR ${BASE_DIR}
USER ${APP_USER}

CMD java ${JAVA_OPTS} -jar app.jar
