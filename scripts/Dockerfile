FROM groovy:4.0-jdk21

USER root
RUN apt-get update \
    && apt-get install -y --no-install-recommends openssh-client \
    && rm -rf /var/lib/apt/lists/*
USER groovy

COPY --chown=groovy:groovy cooperation/*.groovy scripts/
COPY --chown=groovy:groovy cooperation/*.sh scripts/
COPY --chown=groovy:groovy logback-ecs.xml scripts/logback.xml

RUN chmod +x scripts/*.sh \
 && groovy scripts/grabit.groovy

