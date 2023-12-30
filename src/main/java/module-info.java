module dev.hanferp.javafindvisualization {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;

    opens dev.hafnerp.javafindvisualization to javafx.fxml;
    exports dev.hafnerp.javafindvisualization;
}