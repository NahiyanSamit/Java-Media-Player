module com.player.javamusicplayergradle {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    requires org.controlsfx.controls;


    opens com.player.javamusicplayergradle to javafx.fxml;
    exports com.player.javamusicplayergradle;
}