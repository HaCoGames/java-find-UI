package dev.hafnerp.javafindvisualization;

import dev.hafnerp.logger.EventLogger;
import dev.hafnerp.search.ListWrapper;
import dev.hafnerp.search.SearchA;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

public class HelloApplication extends Application {

    private static final Logger logger = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);

        HelloController controller = fxmlLoader.getController();
        logger.debug("Created eventLogger on HelloController text_events: " + EventLogger.getInstance(controller.getText_events()));

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}