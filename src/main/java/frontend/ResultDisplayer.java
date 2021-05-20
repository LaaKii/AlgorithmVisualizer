package frontend;

import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.SearchField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ResultDisplayer {

    public static void displayResult(SearchField searchField, Index index){
        while(index!=null && (!searchField.getButtons()[index.getRow()][index.getColumn()].getText().equals("S") || !searchField.getButtons()[index.getRow()][index.getColumn()].getText().equals("Z"))){
            Button visitedButton = searchField.getButtons()[index.getRow()][index.getColumn()];
            searchField.getGrid().getChildren().remove(visitedButton);
            visitedButton.setStyle("-fx-background-color: #f1f514; ");
            searchField.getGrid().add(visitedButton, index.getColumn(), index.getRow());
            index = index.getPreviousIndex();
        }
    }

}
