package leomessi.uno.Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class Logger {
    private static Logger instance;
    private final java.util.logging.Logger logger;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BOLD = "\u001B[1m";

    private Logger() {
        logger = java.util.logging.Logger.getLogger("TTUno");
        logger.setUseParentHandlers(false);
        
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            @Override
            public String format(LogRecord record) {
                String time = LocalTime.now().format(timeFormatter);
                return String.format("[%s] %s%n", time, record.getMessage());
            }
        });
        
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        
        // Suppress other loggers
        java.util.logging.Logger.getLogger("org.eclipse.jetty").setLevel(Level.WARNING);
        java.util.logging.Logger.getLogger("org.glassfish").setLevel(Level.WARNING);
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void info(String message) {
        logger.info(ANSI_BLUE + message + ANSI_RESET);
    }

    public void success(String message) {
        logger.info(ANSI_GREEN + message + ANSI_RESET);
    }

    public void warning(String message) {
        logger.warning(ANSI_YELLOW + message + ANSI_RESET + "\n");
    }

    public void announcement(String message, String type) {
        if ("info".equals(type)) {
            logger.info(ANSI_BOLD + ANSI_BLUE + message + ANSI_RESET + "\n");
        } else if ("success".equals(type)) {
            logger.info(ANSI_BOLD + ANSI_GREEN + message + ANSI_RESET + "\n");
        } else {
            throw new IllegalArgumentException("Invalid type. Choose 'info' or 'success'.");
        }
    }

    public void error(String message) {
        logger.severe(ANSI_RED + message + ANSI_RESET + "\n");
    }
} 