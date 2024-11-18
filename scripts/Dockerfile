FROM groovy:4.0-jdk17

USER root
RUN apt update && apt install -y mailutils openssh-client
USER groovy

COPY --chown=groovy:groovy cooperation/*.groovy scripts/
COPY --chown=groovy:groovy cooperation/*.sh scripts/

RUN chmod +x scripts/*.sh \
 && groovy scripts/grabit.groovy

COPY logback-ecs.groovy scripts/logback.groovy

