package backend.algorithms;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class BreadthFirstSearch implements SearchAlgorithm {

    private List<Index> currentIndex = new ArrayList<>();
    private GridPane searchField = new GridPane();
    private Button[][] buttons;

    @Override
    public void doSearch(GridPane searchField, Button[][] buttons, Index startField) {
        this.searchField = searchField;
        this.buttons = buttons;
        boolean[][] visited = new boolean[searchField.getRowCount()][searchField.getColumnCount()];
        boolean foundTarget = false;
        currentIndex.add(startField);


//        while(!foundTarget){
//            for(Index index : currentIndex){
                foundTarget = searchIndexInEveryDirection(currentIndex.get(0));
//            }
//        }
    }


    public boolean searchIndexInEveryDirection(Index index) {

        boolean found = false;
        currentIndex.remove(index);

        found = checkBelow(index);
        found = checkAbove(index);


        return found;
    }

    private boolean checkAbove(Index index) {
        if (index.getRow()>0){
            Index aboveIndex = Index.copy(index);
            aboveIndex.setRow(aboveIndex.getRow()-1);
            index.setRow(index.getRow()-1);
            return checkEndPosition(index);
        }
        return false;
    }

    private boolean checkBelow(Index index) {
        if (buttons.length > index.getRow()) {
            Index belowIndex = Index.copy(index);
            belowIndex.setRow(belowIndex.getRow()+1);
            return checkEndPosition(belowIndex);
        }
        return false;
    }

    private boolean checkEndPosition(Index index){
        int row = index.getRow();
        int column = index.getColumn();

        Button visitedButton = buttons[row][column];
        if (visitedButton.getText().equals("Z")){
            return true;
        }else if(visitedButton.getText().equals("X")){
            return false;
        }else{
            searchField.getChildren().remove(visitedButton);
            visitedButton.setStyle("-fx-background-color: #89c1c7 ");
            searchField.add(visitedButton, column, row);
            currentIndex.add(index);
            return false;
        }
    }

}
