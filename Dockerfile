FROM maven:3-ibm-semeru-11-focal AS maven

WORKDIR /opt/build

RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=bind,source=src,target=src \
    --mount=type=cache,target=/root/.m2\
    mvn clean install -PdockerizedJar -DskipTests=true


FROM ibm-semeru-runtimes:open-11-jre AS cooperation
ENV LOGGING_CONFIG=/opt/app/logback.xml
ADD logback4ecs.xml ${LOGGING_CONFIG}
COPY --from=maven /opt/build/target/*.jar /opt/app/app.jar
RUN useradd ind-app -MU
USER ind-app
CMD ["java", "-jar", "/opt/app/app.jar"]
