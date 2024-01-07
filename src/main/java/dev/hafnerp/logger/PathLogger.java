package dev.hafnerp.logger;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class PathLogger {
    private static final Logger logger = LogManager.getLogger(PathLogger.class);

    private static PathLogger pathLogger = null;

    private final TextArea text_paths;

    private boolean first;

    private int logCounter = 0;

    public static PathLogger getInstance(TextArea text_paths) {
        if (pathLogger == null) {
            pathLogger = new PathLogger(text_paths);
        }
        return pathLogger;
    }

    public static PathLogger getInstance() {
        return pathLogger;
    }

    private PathLogger(TextArea text_paths) {
        this.text_paths = text_paths;
    }

    public void logPath(Path path) {
        if (getInstance().isFirst() && (logCounter++ >= 1)) logger.debug("One path had already been found.");
        else {
            text_paths.setText(text_paths.getText() + path + "\n");
            logger.debug("Logging path: " + path);
        }
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public synchronized void reset() {
        text_paths.setText("");
        setFirst(false);
        logCounter = 0;
    }
}
