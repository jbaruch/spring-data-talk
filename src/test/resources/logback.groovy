import ch.qos.logback.classic.encoder.PatternLayoutEncoder

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%-5level %logger{5} - %msg%n"
    }
}
root(ERROR, ["STDOUT"])
logger("conference", INFO)