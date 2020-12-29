package frontend;

import backend.searchAlgorithms.BreadthFirstSearch;
import backend.searchAlgorithms.DepthFirstSearch;
import backend.searchAlgorithms.SearchAlgorithm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.Path;

public class VisualizerHeader {

    private VisualizerField visualizerField;
    private VBox parent;

    public VisualizerHeader(VisualizerField visualizerField, VBox parent){
        this.parent = parent;
        this.visualizerField=visualizerField;
    }

    public Node getHeader() {
        VBox header = new VBox();
        HBox headLine = new HBox();
        headLine.setPadding(new Insets(15,12,15,12));
        headLine.setAlignment(Pos.CENTER);
        Label headerLabel = new Label("Algorithm Visualizer");
        headerLabel.setAlignment(Pos.CENTER);
        headLine.getChildren().add(headerLabel);
        HBox algorithmHeader = createAlgorithmHeader();

        header.getChildren().addAll(headLine, algorithmHeader);
        return header;
    }

    private HBox createAlgorithmHeader() {
        HBox algorithms = new HBox();
        ComboBox<String> algorithmCombo = new ComboBox<>();
        Button startButton = new Button("Next Searchstep");
        Button reloadField = new Button("Reload field");
        Button safeCurrentField = new Button("Safe field");

        startButton.setAlignment(Pos.CENTER);
        reloadField.setAlignment(Pos.CENTER);
        reloadField.setOnAction(e -> {
            visualizerField.resetField(parent);
            visualizerField.setSearchAlgorithm(getSelectedSearchAlgorithm(algorithmCombo.getValue()));
        });
        startButton.setOnAction(e -> {
            visualizerField.startSearch();
        });
        safeCurrentField.setOnAction(e->{
            visualizerField.safeField(Path.of("input.json"));
            reloadField.fire();
        });
        algorithms.setSpacing(20);
        algorithms.setPadding(new Insets(10,30,10,50));


        //TODO load from file or something like that
        algorithmCombo.getItems().add("Breadth-First-Search");
        algorithmCombo.getItems().add("Depth-First-Search");
        algorithmCombo.getSelectionModel().select(0);
        algorithmCombo.valueProperty().addListener((obs, s, t1) -> {
                visualizerField.setSearchAlgorithm(getSelectedSearchAlgorithm(t1));
        });
        visualizerField.setSearchAlgorithm(new BreadthFirstSearch());

        algorithms.getChildren().addAll(algorithmCombo, startButton, reloadField, safeCurrentField);

        return algorithms;
    }

    private SearchAlgorithm getSelectedSearchAlgorithm(String selectedValue){
        if (selectedValue.equals("Breadth-First-Search")){
           return new BreadthFirstSearch();
        }else if(selectedValue.equals("Depth-First-Search")){
           return new DepthFirstSearch();
        }else{
            throw new IllegalArgumentException("Selected Algorithm( " + selectedValue + " ) can't be resolved");
        }
    }
}
