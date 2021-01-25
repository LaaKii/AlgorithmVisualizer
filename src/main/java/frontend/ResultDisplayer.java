package frontend;

import backend.searchAlgorithms.Index;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ResultDisplayer {

    public static void displayResult(Button[][] buttons, Index index, GridPane searchField){
        while(index!=null && (!buttons[index.getRow()][index.getColumn()].getText().equals("S") || !buttons[index.getRow()][index.getColumn()].getText().equals("Z"))){
            Button visitedButton = buttons[index.getRow()][index.getColumn()];
            searchField.getChildren().remove(visitedButton);
            visitedButton.setStyle("-fx-background-color: #f1f514; ");
            searchField.add(visitedButton, index.getColumn(), index.getRow());
            index = index.getPreviousIndex();
        }
    }

}
