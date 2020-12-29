package frontend;

import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.SearchAlgorithm;
import fileprocessing.JSONReader;
import fileprocessing.Reader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    private Button endField;
    private Reader fileReader = new JSONReader();

    public VisualizerField(Path pathToConfig) {
        this.pathToConfig=pathToConfig;
    }

    public Node refreshField(){
        return createFieldByConfig(pathToConfig);
    }

    public void setPathToConfig(Path pathToConfig){
        this.pathToConfig=pathToConfig;
    }

    public Node createFieldByConfig(Path pathToConfig) {
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

    public void startSearch(){
        if (searchAlgorithm!=null){
            searchAlgorithm.doSearch(getGrid(), getCurrentButtonField(), startField);
        } else{
            throw new NullPointerException("Search Algorithm isn't set");
        }
    }

    public void setSearchAlgorithm(SearchAlgorithm searchAlgorithm){
        this.searchAlgorithm=searchAlgorithm;
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

}

