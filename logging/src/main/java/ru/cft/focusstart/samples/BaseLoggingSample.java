package ru.cft.focusstart.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseLoggingSample implements LoggingSample {

    private final Logger logger;

    protected BaseLoggingSample(String loggerName) {
        logger = LoggerFactory.getLogger(loggerName);
    }

    protected BaseLoggingSample(Class<? extends BaseLoggingSample> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void loggingMethod() {
        logger.trace("I am message from {} in trace level", getClass().getSimpleName());
        logger.debug("I am message from {} in debug level", getClass().getSimpleName());
        logger.info("I am message from {} in info level", getClass().getSimpleName());
        logger.warn("I am message from {} in warn level", getClass().getSimpleName());
        logger.error("I am message from {} in error level", getClass().getSimpleName());
    }
}
