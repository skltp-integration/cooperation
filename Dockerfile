FROM eclipse-temurin:17-jre-alpine

ENV BASE_DIR=/opt/cooperation/ \
    APP_USER=cooperation \
    JAVA_OPTS="-XX:MaxRAMPercentage=75 -Dspring.profiles.active=production -Dapp.conf.dir=/etc/cooperation"

ADD target/cooperation-*jar ${BASE_DIR}/app.jar

RUN adduser -HD -u 1000 -h ${BASE_DIR} ${APP_USER}

WORKDIR ${BASE_DIR}
USER ${APP_USER}

CMD java ${JAVA_OPTS} -jar app.jar
