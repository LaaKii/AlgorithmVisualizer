import backend.searchAlgorithms.BreadthFirstSearch;
import backend.searchAlgorithms.interfaces.BasicSearchAlgorithm;
import frontend.VisualizerField;
import frontend.VisualizerHeader;
import javafx.application.Application;
import javafx.geometry.Insets;
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

        System.out.println("Starting Algorithm Visualizer...");
        stage.setTitle("Algorithm Visualizer");
        stage.setWidth(1000);
        stage.setHeight(700);

        VBox parent = new VBox();

        BasicSearchAlgorithm basicSearchAlgorithm = new BreadthFirstSearch();
        VisualizerField visualizerField = new VisualizerField(pathToConfig);
        visualizerField.setSearchAlgorithm(basicSearchAlgorithm);
        VisualizerHeader visualizerHeader = new VisualizerHeader(visualizerField, parent);

        parent.getChildren().addAll(visualizerHeader.getHeader(), visualizerField.createFieldByConfig(pathToConfig));

        Scene scene1 = new Scene(parent);
        stage.setScene(scene1);
        stage.show();

    }
}
