package frontend;

import backend.searchAlgorithms.Index;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ResultDisplayer {



    public void displayResult(Button[][] buttons, Index index, GridPane searchField){
        while(!buttons[index.getRow()][index.getColumn()].getText().equals("S")){
            Button visitedButton = buttons[index.getRow()][index.getColumn()];
            searchField.getChildren().remove(visitedButton);
            visitedButton.setStyle("-fx-background-color: #f1f514; ");
            searchField.add(visitedButton, index.getColumn(), index.getRow());
            System.out.println("["+index.getRow()+"]["+index.getColumn()+"]");
            index = index.getPreviousIndex();
        }
    }

}
