package dev.hafnerp.logger;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventLogger {

    private static final Logger logger = LogManager.getLogger(EventLogger.class);

    private static final int MAX_EVENT_LENGTH = 200;

    private static int currentEventLength = 0;

    private final TextArea text_events;

    private static EventLogger eventLogger = null;

    public static EventLogger getInstance() {
        return eventLogger;
    }

    public static EventLogger getInstance(TextArea events) {
        if (eventLogger == null) eventLogger = new EventLogger(events);
        return eventLogger;
    }

    private EventLogger(TextArea events) {
        currentEventLength++;
        this.text_events = events;
    }

    public synchronized void logEvent(String prefix, String event) {
        logger.debug("Logging: " + prefix + " - " + event);
        boolean add = !(currentEventLength >= MAX_EVENT_LENGTH);
        Platform.runLater(() -> text_events.setText(prefix + " - " + event + "\n" + (add ? text_events.getText() : "")));
    }

}
