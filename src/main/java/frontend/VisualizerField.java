package frontend;

import fileprocessing.JSONReader;
import fileprocessing.Reader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.nio.file.Path;

public class VisualizerField {

    private Button[][] field;
    private Button startField;
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
                    startField = tempButton;
                    startFound=true;
                }else if(tempButton.getText().equals("Z")){
                    tempButton.setStyle("-fx-background-color: #f1f514; ");
                }else if(!endFound && tempButton.getText().equals("X")){
                    tempButton.setStyle("-fx-background-color: #aaadb3; ");
                    endField = tempButton;
                    endFound = true;
                }
                grid.add(tempButton, j,i);
            }
        }
        return grid;
    }
}

