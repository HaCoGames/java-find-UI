package dev.hafnerp.logger;

import javafx.scene.control.TextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventLogger {

    private static final Logger logger = LogManager.getLogger(EventLogger.class);

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
        this.text_events = events;
    }

    public synchronized void logEvent(String event) {
        logger.debug("Logging: " + event);
        text_events.setText(text_events.getText() + event + "\n");
    }

}
