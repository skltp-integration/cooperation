FROM maven:3-eclipse-temurin-11 AS maven

WORKDIR /opt/build

RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=bind,source=src,target=src \
    --mount=type=cache,target=/root/.m2\
    mvn clean install -PecsLogging -DskipTests=true


FROM tomcat:9-jdk11-temurin AS cooperation
ENV LOGGING_CONFIG=/usr/local/tomcat/conf/logback.xml
ADD https://repo1.maven.org/maven2/co/elastic/logging/jul-ecs-formatter/1.5.0/jul-ecs-formatter-1.5.0.jar /usr/local/tomcat/lib
ADD logback4ecs.xml ${LOGGING_CONFIG}
COPY --from=maven /opt/build/target/*.war /usr/local/tomcat/webapps/coop.war
RUN useradd ind-app -MU && chown ind-app -R /usr/local/tomcat/webapps
COPY <<EOF /usr/local/tomcat/conf/logging.properties
java.util.logging.ConsoleHandler.level = FINE
java.util.logging.ConsoleHandler.formatter = co.elastic.logging.jul.EcsFormatter
EOF

USER ind-app
