package frontend;

import backend.algorithms.Index;
import backend.algorithms.SearchAlgorithm;
import fileprocessing.JSONReader;
import fileprocessing.Reader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.nio.file.Path;

public class VisualizerField {

    private Button[][] field;
    private Index startField;
    private GridPane grid;

    //needed for heuristic search algorithms
    private Button endField;
    private Reader fileReader = new JSONReader();

    public Node getField(Path pathToConfig) {
        field = fileReader.readFile(pathToConfig);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        boolean startFound = false;
        boolean endFound = false;

        for(int i = 0; i<field.length; i++){
            for(int j = 0; j<field[i].length; j++){
                Button tempButton = field[i][j];
                if (!startFound && tempButton.getText().equals("S")){
                    tempButton.setStyle("-fx-background-color: #f1f514; ");
                    startField = new Index(i,j);
                    startFound=true;
                }else if(!endFound && tempButton.getText().equals("Z")){
                    tempButton.setStyle("-fx-background-color: #f1f514; ");
                    endField = tempButton;
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

    public void startSearch(SearchAlgorithm searchAlgorithm){
        searchAlgorithm.doSearch(getGrid(), getField(), startField);
    }

    public Button[][] getField(){
        return field;
    }

    public GridPane getGrid(){
        return grid;
    }
}

