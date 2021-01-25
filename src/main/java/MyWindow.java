import backend.searchAlgorithms.BreadthFirstBasicSearch;
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

        System.out.println("New window creating now...");
        stage.setTitle("First window");
        stage.setWidth(1000);
        stage.setHeight(900);

        VBox parent = new VBox();


        VisualizerField visualizerField = new VisualizerField(pathToConfig);
        BasicSearchAlgorithm basicSearchAlgorithm = new BreadthFirstBasicSearch();
        visualizerField.setSearchAlgorithm(basicSearchAlgorithm);
        VisualizerHeader visualizerHeader = new VisualizerHeader(visualizerField, parent);

        parent.setPadding(new Insets(10,50,50,50));
        parent.getChildren().addAll(visualizerHeader.getHeader(), visualizerField.createFieldByConfig(pathToConfig));

        Scene scene1 = new Scene(parent);
        stage.setScene(scene1);
        stage.show();

    }
}
