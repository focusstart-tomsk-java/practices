package ru.cft.focusstart;

import org.apache.log4j.Logger;

public class LegacyLoggingApplication {

    private static final Logger log = Logger.getLogger(LegacyLoggingApplication.class);

    public static void main(String[] args) {
        log.info("I am message from legacy application that uses log4j");
    }
}
