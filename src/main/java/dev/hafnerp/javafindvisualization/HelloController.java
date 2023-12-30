package dev.hafnerp.javafindvisualization;

import dev.hafnerp.logger.EventLogger;
import dev.hafnerp.search.SearchA;
import dev.hafnerp.search.SearchMain;
import javafx.scene.control.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import dev.hafnerp.search.ListWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.nio.file.Path;

public class HelloController {

    private final Logger logger = LogManager.getLogger(HelloController.class);

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
    private Label welcomeText;

    @FXML
    void onSearchButtonClicked(ActionEvent event) {
        text_events.setText("");
        text_paths.setText("");

        String searchString = text_searchstring.getText();
        String searchPathString = text_searchpath.getText();

        Path searchPath = Path.of(searchPathString.isEmpty() ? "." : searchPathString );

        boolean first = checkbox_first.isSelected();

        logger.debug("Search String: \t\"" + searchString + "\"");
        logger.debug("Search Path:   \t" + searchPathString);
        logger.debug("Is first?:     \t" + first);

        final EventLogger eventLogger = EventLogger.getInstance();

        logger.debug("Using path: " + searchPath);
        eventLogger.logEvent("Starting at path: " + searchPath);
        eventLogger.logEvent("Searching for word: \"" + searchString + "\"");

        ListWrapper<Path> pathListWrapper = ListWrapper.getPathInstance();

        logger.debug("Path instance of ListWrapper: "+ pathListWrapper);

        try {
            if (searchString.isEmpty()) searchString = null;
            SearchMain.searchFor(first, searchString, searchPath);
        }
        catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        text_paths.setText(pathListWrapper.toString());

        pathListWrapper.getList().forEach(logger::info);
        pathListWrapper.resetVariables();
        SearchA.setInstanceCounterAll(0);
    }


    public TextArea getText_events() {
        return text_events;
    }
}
