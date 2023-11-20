import co.elastic.logging.logback.EcsEncoder;
appender("STDOUT", ConsoleAppender) {
  encoder(EcsEncoder) {}
}

logger("org.hibernate", ERROR)
logger("org.hibernate.cache", ERROR)

// Use file only or stdout for easier debugging
//root(DEBUG, ["FILE"])
root(DEBUG, ["STDOUT"])
