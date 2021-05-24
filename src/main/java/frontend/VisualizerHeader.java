package frontend;

import backend.searchAlgorithms.*;
import backend.searchAlgorithms.interfaces.SearchAlgorithm;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

public class VisualizerHeader {

    private VisualizerField visualizerField;
    private VBox parent;

    public VisualizerHeader(VisualizerField visualizerField, VBox parent){
        this.parent = parent;
        this.visualizerField = visualizerField;
    }

    public Node getHeader() {
        VBox header = new VBox();
        HBox headLine = new HBox();
        VBox.setMargin(headLine, new Insets(0,0,10,0));
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
        AtomicInteger algorithmSpeed = new AtomicInteger(1000);
        ComboBox<String> algorithmCombo = new ComboBox<>();
        Separator separatorAfterAlgorithms = new Separator();
        separatorAfterAlgorithms.setOrientation(Orientation.VERTICAL);
        NumberTextField amountOfSearchStepsButton = new NumberTextField("1");
        amountOfSearchStepsButton.setPrefWidth(35);
        Button startButton = new Button("Next Step");
        Separator separatorAfterStart = new Separator();
        separatorAfterStart.setOrientation(Orientation.VERTICAL);
        Button reloadField = new Button("Reload field");
        Button safeCurrentField = new Button("Safe field");
        Button runButton = new Button("Run");
        Button slowSearchSpeedButton = new Button("Slow");
        Button midSearchSpeedButton = new Button("Medium");
        midSearchSpeedButton.setDisable(true);
        Button fastSearchSpeedButton = new Button("Fast");
        Separator separatorAfterRun = new Separator();
        separatorAfterRun.setOrientation(Orientation.VERTICAL);
        slowSearchSpeedButton.setOnAction(e->{
            algorithmSpeed.set(2000);
            slowSearchSpeedButton.setDisable(true);
            midSearchSpeedButton.setDisable(false);
            fastSearchSpeedButton.setDisable(false);
        });
        midSearchSpeedButton.setOnAction(e->{
            algorithmSpeed.set(1000);
            midSearchSpeedButton.setDisable(true);
            slowSearchSpeedButton.setDisable(false);
            fastSearchSpeedButton.setDisable(false);
        });
        fastSearchSpeedButton.setOnAction(e->{
            algorithmSpeed.set(500);
            fastSearchSpeedButton.setDisable(true);
            midSearchSpeedButton.setDisable(false);
            slowSearchSpeedButton.setDisable(false);
        });

        runButton.setOnAction(e -> {
            reloadField.fire();
            algorithmCombo.setDisable(true);
            startButton.setDisable(true);
            amountOfSearchStepsButton.setDisable(true);
            runButton.setDisable(true);
            new Thread(){
                boolean running = true;
                @Override
                public void run() {
                    while(running){
                        Platform.runLater(() -> {
                            if (visualizerField.nextSearchStep()){
                                algorithmCombo.setDisable(false);
                                running=false;
                            }
                        });
                        try {
                            Thread.sleep(algorithmSpeed.get());
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                }
            }.start();
        });
        reloadField.setOnAction(e -> {
            startButton.setDisable(false);
            algorithmCombo.setDisable(false);
            runButton.setDisable(false);
            amountOfSearchStepsButton.setDisable(false);
            slowSearchSpeedButton.setDisable(false);
            midSearchSpeedButton.setDisable(false);
            fastSearchSpeedButton.setDisable(false);
            visualizerField.resetField(parent);
            visualizerField.setSearchAlgorithm(getSelectedSearchAlgorithm(algorithmCombo.getValue()));
        });
        startButton.setOnAction(e -> {
            runButton.setDisable(true);
            slowSearchSpeedButton.setDisable(true);
            midSearchSpeedButton.setDisable(true);
            fastSearchSpeedButton.setDisable(true);
            algorithmCombo.setDisable(true);
            for(int i = 0; i<amountOfSearchStepsButton.getAmount(); i++){
                boolean searchFinished = visualizerField.nextSearchStep();
                if (searchFinished){
                    startButton.setDisable(true);
                    algorithmCombo.setDisable(false);
                    slowSearchSpeedButton.setDisable(false);
                    midSearchSpeedButton.setDisable(false);
                    fastSearchSpeedButton.setDisable(false);
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
                reloadField.fire();
                visualizerField.setSearchAlgorithm(getSelectedSearchAlgorithm(t1));
        });
        visualizerField.setSearchAlgorithm(new BreadthFirstSearch());

        algorithms.getChildren().addAll(algorithmCombo,separatorAfterAlgorithms, amountOfSearchStepsButton, startButton, separatorAfterStart, slowSearchSpeedButton, midSearchSpeedButton, fastSearchSpeedButton, runButton, separatorAfterRun, reloadField, safeCurrentField);

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
