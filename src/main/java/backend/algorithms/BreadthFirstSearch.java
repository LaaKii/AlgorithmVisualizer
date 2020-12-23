package backend.algorithms;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class BreadthFirstSearch implements SearchAlgorithm {

    private List<Index> indexToContinueSearch = new ArrayList<>();
    private List<Index> currentIndex = new ArrayList<>();
    private GridPane searchField = new GridPane();
    private Button[][] buttons;
    private boolean firstSearch=true;
    boolean[][] visited = null;

    @Override
    public void doSearch(GridPane searchField, Button[][] buttons, Index startField) {
        this.searchField = searchField;
        this.buttons = buttons;

        boolean foundTarget = false;

        if (visited==null){
            visited = new boolean[searchField.getRowCount()][searchField.getColumnCount()];
        }


        //TODO change this with second if
        if (firstSearch) {

            currentIndex.add(startField);
        }


        for (Index index : currentIndex) {
            if (index.getRow()==10&&index.getColumn()==12){
                System.out.println("burr");
            }
            foundTarget = searchIndexInEveryDirection(index);
        }
        if (firstSearch){
            visited[startField.getRow()][startField.getColumn()]=true;
            firstSearch=false;
        }

        currentIndex = List.copyOf(indexToContinueSearch);
        indexToContinueSearch.clear();
        System.out.println("for over");
    }


    public boolean searchIndexInEveryDirection(Index index) {

        boolean found = false;

        found = checkBelow(index);
        found = checkAbove(index);
        found = checkRight(index);
        found = checkLeft(index);


        return found;
    }

    private boolean checkLeft(Index index) {
        if (index.getColumn()-1 >= 0 && !visited[index.getRow()][index.getColumn()-1]) {
            Index rightIndex = Index.copy(index);
            rightIndex.setColumn(rightIndex.getColumn() - 1);
            return checkEndPosition(rightIndex);
        }
        return false;
    }

    private boolean checkRight(Index index) {
        if (index.getColumn()+1 < buttons[index.getRow()].length && !visited[index.getRow()][index.getColumn()+1]) {
            Index rightIndex = Index.copy(index);
            rightIndex.setColumn(rightIndex.getColumn() + 1);
            return checkEndPosition(rightIndex);
        }
        return false;
    }

    private boolean checkAbove(Index index) {
        if (index.getRow()-1 >= 0 && !visited[index.getRow()-1][index.getColumn()]) {
            Index aboveIndex = Index.copy(index);
            aboveIndex.setRow(aboveIndex.getRow() - 1);
            return checkEndPosition(aboveIndex);
        }
        return false;
    }

    private boolean checkBelow(Index index) {
        if (index.getRow()+1 < buttons.length && !visited[index.getRow()+1][index.getColumn()]) {
            Index belowIndex = Index.copy(index);
            belowIndex.setRow(belowIndex.getRow() + 1);
            return checkEndPosition(belowIndex);
        }
        return false;
    }

    private boolean checkEndPosition(Index index) {
        int row = index.getRow();
        int column = index.getColumn();
        visited[row][column] = true;
        if (index.getRow()==10&&index.getColumn()==12){
            System.out.println("burr");
        }
        Button visitedButton = buttons[row][column];
        if (visitedButton.getText().equals("Z")) {
            System.out.println("Target found at: [" + index.getRow()+"]["+index.getColumn()+"]");
            return true;
        } else if (visitedButton.getText().equals("X")) {
            System.out.println("wall");
            return false;
        } else {
            searchField.getChildren().remove(visitedButton);
            visitedButton.setStyle("-fx-background-color: #89c1c7 ");
            searchField.add(visitedButton, column, row);
            indexToContinueSearch.add(index);

            return false;
        }
    }

}
