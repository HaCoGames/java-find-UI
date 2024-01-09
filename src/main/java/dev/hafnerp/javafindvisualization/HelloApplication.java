package dev.hafnerp.javafindvisualization;

import dev.hafnerp.logger.EventLogger;
import dev.hafnerp.logger.PathLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HelloApplication extends Application {

    private static final Logger logger = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 833, 400);

        HelloController controller = fxmlLoader.getController();
        logger.debug("Created eventLogger on HelloController text_events: " + EventLogger.getInstance(controller.getText_events()));
        logger.debug("Created pathLogger  on HelloController text_paths: " + PathLogger.getInstance(controller.getText_paths()));

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}