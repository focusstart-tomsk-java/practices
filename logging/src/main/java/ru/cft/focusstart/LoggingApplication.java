package ru.cft.focusstart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.samples.foo.Bar;
import ru.cft.focusstart.samples.foo.Foo;
import ru.cft.focusstart.samples.foo.buzz.BuzzBar;
import ru.cft.focusstart.samples.foo.buzz.BuzzFoo;

import java.io.File;

public class LoggingApplication {

    private static final Logger log = LoggerFactory.getLogger(LoggingApplication.class);

    public static void main(String[] args) {
        log.info("I am message from logger");

        File file = new File("C:\\myfile.txt");

        if (log.isInfoEnabled()) {
            log.info("I am old style log message concatenation method: file = " + file);
        }

        log.info("I am new style log message concatenation method: file = {}", file);

        showHierarchySamples();
    }

    private static void showHierarchySamples() {
        new Foo().loggingMethod();
        new Bar().loggingMethod();
        new BuzzFoo().loggingMethod();
        new BuzzBar().loggingMethod();
    }
}
