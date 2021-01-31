package backend.searchAlgorithms;

import backend.searchAlgorithms.interfaces.BasicSearchAlgorithm;
import frontend.ResultDisplayer;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class DepthFirstSearch implements BasicSearchAlgorithm {
    private GridPane searchField;
    private Button[][] buttons;
    boolean[][] visited = null;
    private Index currentField = null;
    private boolean firstRun = true;
    private boolean targetCannotBeReached=false;

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField) {
        this.searchField = searchField;
        this.buttons = buttons;
        if (firstRun) {
            currentField = startField;
            visited = new boolean[buttons.length][buttons[0].length];
            firstRun = false;
        }


        if (searchIndexInDepth(currentField)) {
            System.out.println("Target found at: [" + currentField.getRow() + "][" + currentField.getColumn() + "]");
            ResultDisplayer.displayResult(buttons, currentField, searchField);
            return true;
        }else if (targetCannotBeReached){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Target could not be found");
            String s = "Either there is no target or the target couldn't be reached";
            alert.setContentText(s);
            alert.show();
            return true;
        }
        return false;
    }

    private boolean searchIndexInDepth(Index indexToSearch) {
        Index nextIndex = giveNextIndex(indexToSearch);
        //TODO Recursion is at the moment going the whole way back.
        // We need to somehow check if the previous index is a already seen one.
        if (nextIndex!=null) {
            return lookupNextField(nextIndex);
        }else{
            targetCannotBeReached=true;
            return false;
        }
    }

    private Index giveNextIndex(Index indexToSearch) {
        //TODO nothing to say...
        if (indexToSearch==null){
            return null;
        }
        if (isFieldLeftVisitable(indexToSearch)){
            Index oldIndex = Index.copy(indexToSearch);
            indexToSearch.setPreviousIndex(oldIndex);
            indexToSearch.setColumn(indexToSearch.getColumn() - 1);
        } else if(isFieldBelowVisitable(indexToSearch)){
            Index oldIndex = Index.copy(indexToSearch);
            indexToSearch.setPreviousIndex(oldIndex);
            indexToSearch.setRow(indexToSearch.getRow() + 1);
        } else if(isFieldRightVisitable(indexToSearch)){
            Index oldIndex = Index.copy(indexToSearch);
            indexToSearch.setPreviousIndex(oldIndex);
            indexToSearch.setColumn(indexToSearch.getColumn() + 1);
        } else if(isFieldOnTopVisitable(indexToSearch)){
            Index oldIndex = Index.copy(indexToSearch);
            indexToSearch.setPreviousIndex(oldIndex);
            indexToSearch.setRow(indexToSearch.getRow() - 1);
        }
        else {
            //Move one node back up
            indexToSearch = giveNextIndex(indexToSearch.getPreviousIndex());
        }
        return indexToSearch;
    }

    private boolean isFieldOnTopVisitable(Index indexToSearch) {
        return indexToSearch.getRow()-1 > 0
                && !visited[indexToSearch.getRow()-1][indexToSearch.getColumn()]
                && !buttons[indexToSearch.getRow()-1][indexToSearch.getColumn()].getText().equals("X");
    }

    private boolean isFieldLeftVisitable(Index indexToSearch){
        return indexToSearch.getColumn()>0
                && !(buttons[indexToSearch.getRow()][indexToSearch.getColumn() - 1].getText().equals("X"))
                && !(buttons[indexToSearch.getRow()][indexToSearch.getColumn() - 1].getText().equals("X"))
                && !visited[indexToSearch.getRow()][indexToSearch.getColumn() - 1 ]
                && !(buttons[indexToSearch.getRow()][indexToSearch.getColumn() - 1].getText().equals("S"));
    }

    private boolean isFieldRightVisitable(Index indexToSearch){
        if (indexToSearch.getColumn()+1 >= visited[indexToSearch.getRow()].length){
            return false;
        }
        return !(buttons[indexToSearch.getRow()][indexToSearch.getColumn() + 1].getText().equals("X"))
                && !(buttons[indexToSearch.getRow()][indexToSearch.getColumn() + 1].getText().equals("X"))
                && !visited[indexToSearch.getRow()][indexToSearch.getColumn() + 1]
                && indexToSearch.getColumn()+1 < buttons[indexToSearch.getRow()].length;
    }

    private boolean isFieldBelowVisitable(Index indexToSearch){
        return indexToSearch.getRow()+1<buttons.length && !(buttons[indexToSearch.getRow() + 1][indexToSearch.getColumn()].getText().equals("X")) && !visited[indexToSearch.getRow() + 1][indexToSearch.getColumn()];
    }

    private boolean lookupNextField(Index nextIndex) {
        if (nextIndex.getRow() < buttons.length && !visited[nextIndex.getRow()][nextIndex.getColumn()]) {
            return checkEndPosition(nextIndex);
        }
        return false;
    }

    private boolean checkEndPosition(Index index) {
        int row = index.getRow();
        int column = index.getColumn();
        visited[row][column] = true;
        Button visitedButton = buttons[row][column];
        if (visitedButton.getText().equals("Z")) {
            return true;
        } else if (visitedButton.getText().equals("X")) {
            Index nextSideIndex = Index.copy(index);
            nextSideIndex.setColumn(index.getColumn() + 1);
            nextSideIndex.setRow(index.getRow() - 2);
            currentField = nextSideIndex;
            return false;
        } else {
            searchField.getChildren().remove(visitedButton);
            visitedButton.setStyle("-fx-background-color: #89c1c7 ");
            searchField.add(visitedButton, column, row);
            currentField = index;
            return false;
        }
    }

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField) {
        return doSearch(searchField, buttons, startField);
    }
}
