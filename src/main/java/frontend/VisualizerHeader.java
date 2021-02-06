package frontend;

import backend.searchAlgorithms.BidirectionalBreadthFirstSearch;
import backend.searchAlgorithms.BreadthFirstSearch;
import backend.searchAlgorithms.DepthFirstSearch;
import backend.searchAlgorithms.GreedyFirstSearch;
import backend.searchAlgorithms.interfaces.SearchAlgorithm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
        VBox.setMargin(headLine, new Insets(0,0,50,0));
        headLine.setPadding(new Insets(15,12,15,12));
        headLine.setAlignment(Pos.CENTER);
        headLine.setStyle("-fx-background-color: #333333;");
        Label headerLabel = new Label("Algorithm Visualizer");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextFill(Color.web("#FFFFFF"));
        headerLabel.setFont(Font.font("Verdana", 35));
        headLine.getChildren().add(headerLabel);
        HBox algorithmHeader = createAlgorithmHeader();

        header.getChildren().addAll(headLine, algorithmHeader);
        return header;
    }

    private HBox createAlgorithmHeader() {
        HBox algorithms = new HBox();
        ComboBox<String> algorithmCombo = new ComboBox<>();
        NumberTextField amountOfSearchStepsButton = new NumberTextField("1");
        amountOfSearchStepsButton.setPrefWidth(35);
        Button startButton = new Button("Next Searchstep");
        Button reloadField = new Button("Reload field");
        Button safeCurrentField = new Button("Safe field");
        reloadField.setOnAction(e -> {
            startButton.setDisable(false);
            visualizerField.resetField(parent);
            visualizerField.setSearchAlgorithm(getSelectedSearchAlgorithm(algorithmCombo.getValue()));
        });
        startButton.setOnAction(e -> {
            for(int i = 0; i<amountOfSearchStepsButton.getAmount(); i++){
                boolean searchFinished = visualizerField.nextSearchStep();
                if (searchFinished){
                    startButton.setDisable(true);
                    break;
                }
            }
        });
        safeCurrentField.setOnAction(e->{
            visualizerField.safeField(Path.of("input.json"));
            reloadField.fire();
        });
        algorithms.setSpacing(20);
        algorithms.setPadding(new Insets(10,30,10,50));


        //TODO load from file or something like that
        algorithmCombo.getItems().add("Breadth-First-Search");
        algorithmCombo.getItems().add("Bidirectional Breadth-First-Search");
        algorithmCombo.getItems().add("Depth-First-Search");
        algorithmCombo.getItems().add("Greedy-First-Search");
        algorithmCombo.getSelectionModel().select(0);
        algorithmCombo.valueProperty().addListener((obs, s, t1) -> {
                visualizerField.setSearchAlgorithm(getSelectedSearchAlgorithm(t1));
        });
        visualizerField.setSearchAlgorithm(new BreadthFirstSearch());

        algorithms.getChildren().addAll(algorithmCombo, amountOfSearchStepsButton, startButton, reloadField, safeCurrentField);

        return algorithms;
    }

    private SearchAlgorithm getSelectedSearchAlgorithm(String selectedValue){
        if (selectedValue.equals("Breadth-First-Search")){
           return new BreadthFirstSearch();
        }else if(selectedValue.equals("Depth-First-Search")){
           return new DepthFirstSearch();
        }else if(selectedValue.equals("Bidirectional Breadth-First-Search")){
            return new BidirectionalBreadthFirstSearch();
        }else if(selectedValue.equals("Greedy-First-Search")){
            return new GreedyFirstSearch();
        }else{
            throw new IllegalArgumentException("Selected Algorithm( " + selectedValue + " ) can't be resolved");
        }
    }
}
