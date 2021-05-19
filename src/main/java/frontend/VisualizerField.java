package frontend;

import backend.common.AlertManager;
import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.interfaces.SearchAlgorithm;
import fileprocessing.interfaces.FileProcessor;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.nio.file.Path;

public class VisualizerField {

    private Button[][] field;
    private Index startField;
    private GridPane grid;
    private SearchAlgorithm searchAlgorithm;
    private Path pathToConfig;

    //needed for heuristic search algorithms
    private Index endField;
    private FileProcessor fileProcessor;

    public VisualizerField(Path pathToConfig, FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
        this.pathToConfig=pathToConfig;
    }

    public Node refreshField(){
        return createFieldByConfig(pathToConfig);
    }

    public void setPathToConfig(Path pathToConfig){
        this.pathToConfig=pathToConfig;
    }

    public Node createFieldByConfig(Path pathToConfig) {
        field = fileProcessor.processFile(pathToConfig);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        boolean startFound = false;
        boolean endFound = false;

        for(int i = 0; i<field.length; i++){
            for(int j = 0; j<field[i].length; j++){
                Button tempButton = field[i][j];
                tempButton.setOnAction(actionEvent -> {
                    tempButton.switchField(tempButton.getText());
                });
                if (!startFound && tempButton.getText().equals("S")){
                    tempButton.setStyle("-fx-background-color: #f1f514; ");
                    startField = new Index(i,j);
                    startFound=true;
                }else if(!endFound && tempButton.getText().equals("Z")){
                    tempButton.setStyle("-fx-background-color: #f1f514; ");
                    endField = new Index(i,j);
                    endFound = true;
                }else if(tempButton.getText().equals("X")){
                    tempButton.setStyle("-fx-background-color: #aaadb3; ");
                }
                grid.add(tempButton, j,i);
            }
        }
        this.grid=grid;
        return grid;
    }

    public boolean nextSearchStep(){
        if (searchAlgorithm !=null){
          return searchAlgorithm.doSearch(getGrid(), getCurrentButtonField(), startField, endField);
        } else{
            showAlgorithmError();
            throw new NullPointerException("Search Algorithm isn't set");
        }
    }

    private void showAlgorithmError(){
        AlertManager alertManager = new AlertManager.Builder()
                .setTitle("Algorithm Error")
                .setHeaderText("Algorithm not set")
                .setContextText("Either the algorithm couldn't be recognized or it isn't set")
                .setAlertType(Alert.AlertType.ERROR)
                .build();
        alertManager.showAlert();
    }

    public void setSearchAlgorithm(SearchAlgorithm searchAlgorithm){
        this.searchAlgorithm = searchAlgorithm;
    }

    public void resetField(VBox parent){
        parent.getChildren().remove(grid);
        field = null;
        parent.getChildren().add(refreshField());
    }

    public Button[][] getCurrentButtonField(){
        return field;
    }

    public GridPane getGrid(){
        return grid;
    }

    public void safeField(Path pathToWriteFile) {
        fileProcessor.writeFile(field,pathToWriteFile);
    }
}

