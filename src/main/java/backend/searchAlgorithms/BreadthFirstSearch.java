package backend.searchAlgorithms;

import backend.common.AlertManager;
import backend.searchAlgorithms.interfaces.BasicSearchAlgorithm;
import frontend.Button;
import frontend.ResultDisplayer;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class BreadthFirstSearch implements BasicSearchAlgorithm {

    private List<Index> indexToContinueSearch = new ArrayList<>();
    private List<Index> currentIndex = new ArrayList<>();
    private GridPane searchField = new GridPane();
    private Button[][] buttons;
    private boolean firstSearch = true;
    private boolean isSearchForGreedyFirstSearch=false;

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField) {
        this.searchField = searchField;
        this.buttons = buttons;

        boolean searchFinished = false;

        //TODO change this with second if
        if (firstSearch) {
            currentIndex.add(startField);
        }

        for (Index index : currentIndex) {
            searchFinished = searchIndexInEveryDirection(index);
            if (searchFinished) {
                System.out.println("Target found at: [" + index.getRow() + "][" + index.getColumn() + "]");
                ResultDisplayer.displayResult(buttons, index, searchField);
                return true;
            }
        }
        if (firstSearch) {
            buttons[startField.getRow()][startField.getColumn()].setVisited(true);
            firstSearch = false;
        }

        currentIndex = List.copyOf(indexToContinueSearch);
        if (!isSearchForGreedyFirstSearch){
            if (currentIndex.size() == 0) {
                showSearchFinishedAlert();
                return true;
            }
        }
        indexToContinueSearch.clear();
        return false;
    }

    private void showSearchFinishedAlert() {
        AlertManager alertManager = new AlertManager.Builder()
                .setTitle("Information")
                .setHeaderText("Target could not be found")
                .setContextText("Either there is no target or the target couldn't be reached")
                .setAlertType(Alert.AlertType.INFORMATION)
                .build();
        alertManager.showAlert();
    }


    public boolean searchIndexInEveryDirection(Index index) {
        if (checkBelow(index) || checkAbove(index) || checkLeft(index) || checkRight(index)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkLeft(Index index) {
        if (index.getColumn() - 1 >= 0  && !buttons[index.getRow()][index.getColumn() - 1].isStartField() && !buttons[index.getRow()][index.getColumn() - 1].isVisited()) {
            Index leftIndex = Index.copy(index);
            leftIndex.setPreviousIndex(index);
            leftIndex.setColumn(leftIndex.getColumn() - 1);
            return checkEndPosition(leftIndex);
        }
        return false;
    }

    private boolean checkRight(Index index) {
        if (index.getColumn() + 1 < buttons[index.getRow()].length && !buttons[index.getRow()][index.getColumn() + 1].isStartField() && !buttons[index.getRow()][index.getColumn() + 1].isVisited()) {
            Index rightIndex = Index.copy(index);
            rightIndex.setPreviousIndex(index);
            rightIndex.setColumn(rightIndex.getColumn() + 1);
            return checkEndPosition(rightIndex);
        }
        return false;
    }

    private boolean checkAbove(Index index) {
        if (index.getRow() - 1 >= 0 && !buttons[index.getRow() - 1][index.getColumn()].isVisited() && !buttons[index.getRow() - 1][index.getColumn()].isStartField()) {
            Index aboveIndex = Index.copy(index);
            aboveIndex.setPreviousIndex(index);
            aboveIndex.setRow(aboveIndex.getRow() - 1);
            return checkEndPosition(aboveIndex);
        }
        return false;
    }

    private boolean checkBelow(Index index) {
        if (index.getRow() + 1 < buttons.length && !buttons[index.getRow() + 1][index.getColumn()].isVisited() && !buttons[index.getRow() + 1][index.getColumn()].isStartField()) {
            Index belowIndex = Index.copy(index);
            belowIndex.setPreviousIndex(index);
            belowIndex.setRow(belowIndex.getRow() + 1);
            return checkEndPosition(belowIndex);
        }
        return false;
    }

    private boolean checkEndPosition(Index index) {
        int row = index.getRow();
        int column = index.getColumn();
        buttons[index.getRow()][index.getColumn()].setVisited(true);
        Button visitedButton = buttons[row][column];
        if (visitedButton.getText().equals("Z")) {
            return true;
        } else if (visitedButton.getText().equals("X")) {
            return false;
        } else {
            searchField.getChildren().remove(visitedButton);
            visitedButton.setStyle("-fx-background-color: #89c1c7 ");
            searchField.add(visitedButton, column, row);
            indexToContinueSearch.add(index);
            return false;
        }
    }

    public List<Index> getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField) {
        return doSearch(searchField, buttons, startField);
    }

    public boolean doSearchForGreedyFirstSearch(GridPane searchField, Button[][] buttons, List<Index> currentIndex, Index indexWithNextLowestDistance) {
        this.currentIndex = new ArrayList<>(currentIndex);
        this.firstSearch=false;
        isSearchForGreedyFirstSearch = true;
        //currentIndex.get(0) because this is the field with ne lowest manhattan distance to the goal
        return doSearch(searchField, buttons, indexWithNextLowestDistance);
    }

}
