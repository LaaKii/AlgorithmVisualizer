import frontend.VisualizerField;
import frontend.VisualizerHeader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MyWindow extends Application {

    @Override
    public void start(Stage stage) {
        //TODO use runtime arguments for path injection
        Path pathToConfig = Paths.get("input.json");

        System.out.println("New window creating now...");
        stage.setTitle("First window");
        stage.setWidth(800);
        stage.setHeight(650);

        VBox parent = new VBox();

        VisualizerField visualizerField = new VisualizerField();
        VisualizerHeader visualizerHeader = new VisualizerHeader();

        parent.getChildren().addAll(visualizerHeader.getHeader(), visualizerField.getField(pathToConfig));

        Scene scene1 = new Scene(parent);
        stage.setScene(scene1);
        stage.show();
    }
}
