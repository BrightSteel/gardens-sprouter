package com.sproutermc.sprouter.common.logger;

public interface SprouterLogger {
    void info(String message);
    void warn(String message);
    void error(String message);
    void error(String message, Throwable throwable);
}
