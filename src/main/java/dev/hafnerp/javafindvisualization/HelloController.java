package dev.hafnerp.javafindvisualization;

import dev.hafnerp.logger.EventLogger;
import dev.hafnerp.search.SearchA;
import dev.hafnerp.search.SearchMain;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import dev.hafnerp.search.ListWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloController {

    private final Logger logger = LogManager.getLogger(HelloController.class);

    @FXML
    private Label label_ready_to_search;

    @FXML
    private TextArea text_events;

    @FXML
    private TextArea text_paths;

    @FXML
    private Button button_find;

    @FXML
    private CheckBox checkbox_first;

    @FXML
    private TextField text_searchpath;

    @FXML
    private TextField text_searchstring;

    @FXML
    private TextField text_delaytime;

    @FXML
    private Label welcomeText;

    private String paths = "";

    @FXML
    void onSearchButtonClicked(ActionEvent event) {
        text_events.setText("");
        text_paths.setText("");

        String searchString = text_searchstring.getText();
        String searchPathString = text_searchpath.getText();
        searchPathString = searchPathString.isEmpty() ? "." : searchPathString;

        if (!validatePath(searchPathString)) return;

        Path searchPath = Path.of(searchPathString);
        int delayTime = text_delaytime.getText().isEmpty() ? 0:  Integer.parseInt(text_delaytime.getText());

        boolean first = checkbox_first.isSelected();

        logger.debug("Search String: \t\"" + searchString + "\"");
        logger.debug("Search Path:   \t" + searchPathString);
        logger.debug("Is first?:     \t" + first);
        logger.debug("Delay time:    \t" + delayTime);

        final EventLogger eventLogger = EventLogger.getInstance();

        logger.debug("Using path: " + searchPath);
        eventLogger.logEvent("HelloController", "Starting at path: " + searchPath);
        eventLogger.logEvent("HelloController", "Searching for word: \"" + searchString + "\"");

        ListWrapper<Path> pathListWrapper = ListWrapper.getPathInstance();

        logger.debug("Path instance of ListWrapper: "+ pathListWrapper);

        try {
            if (searchString.isEmpty()) searchString = null;
            SearchMain.searchFor(first, searchString, searchPath, delayTime);
        }
        catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validatePath(String paths) {
        boolean validPath = true;
        try {
            Path path = Path.of(paths);
            logger.debug("Checking if the path exists... " + path);
            validPath = Files.exists(path);
            if (!validPath) label_ready_to_search.setText("Not a valid path provided!");
            else label_ready_to_search.setText("Ready to search!");
        } catch (Exception e) {
            label_ready_to_search.setText("Not a valid path provided!");
            validPath = false;
        }
        logger.debug("The path exists? " + validPath);
        return validPath;
    }

    public TextArea getText_events() {
        return text_events;
    }

    public TextArea getText_paths() { return text_paths; }
}
