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
    private SearchField searchField = new SearchField();
    private boolean firstSearch = true;
    private boolean isSearchForGreedyFirstSearch=false;

    @Override
    public boolean doSearch(SearchField searchField) {
        this.searchField = searchField;
        Index startField = searchField.getStartField();

        boolean searchFinished = false;

        //TODO change this with second if
        if (firstSearch) {
            currentIndex.add(startField);
        }

        for (Index index : currentIndex) {
            searchFinished = searchIndexInEveryDirection(index);
            if (searchFinished) {
                System.out.println("Target found at: [" + index.getRow() + "][" + index.getColumn() + "]");
                ResultDisplayer.displayResult(searchField, index);
                return true;
            }
        }
        if (firstSearch) {
            searchField.getButtons()[startField.getRow()][startField.getColumn()].setVisited(true);
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
        if (index.getColumn() - 1 >= 0  && !searchField.getButtons()[index.getRow()][index.getColumn() - 1].isStartField() && !searchField.getButtons()[index.getRow()][index.getColumn() - 1].isVisited()) {
            Index leftIndex = Index.copy(index);
            leftIndex.setPreviousIndex(index);
            leftIndex.setColumn(leftIndex.getColumn() - 1);
            return checkEndPosition(leftIndex);
        }
        return false;
    }

    private boolean checkRight(Index index) {
        if (index.getColumn() + 1 < searchField.getButtons()[index.getRow()].length && !searchField.getButtons()[index.getRow()][index.getColumn() + 1].isStartField() && !searchField.getButtons()[index.getRow()][index.getColumn() + 1].isVisited()) {
            Index rightIndex = Index.copy(index);
            rightIndex.setPreviousIndex(index);
            rightIndex.setColumn(rightIndex.getColumn() + 1);
            return checkEndPosition(rightIndex);
        }
        return false;
    }

    private boolean checkAbove(Index index) {
        if (index.getRow() - 1 >= 0 && !searchField.getButtons()[index.getRow() - 1][index.getColumn()].isVisited() && !searchField.getButtons()[index.getRow() - 1][index.getColumn()].isStartField()) {
            Index aboveIndex = Index.copy(index);
            aboveIndex.setPreviousIndex(index);
            aboveIndex.setRow(aboveIndex.getRow() - 1);
            return checkEndPosition(aboveIndex);
        }
        return false;
    }

    private boolean checkBelow(Index index) {
        if (index.getRow() + 1 < searchField.getButtons().length && !searchField.getButtons()[index.getRow() + 1][index.getColumn()].isVisited() && !searchField.getButtons()[index.getRow() + 1][index.getColumn()].isStartField()) {
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
        searchField.getButtons()[index.getRow()][index.getColumn()].setVisited(true);
        Button visitedButton = searchField.getButtons()[row][column];
        if (visitedButton.getText().equals("Z")) {
            return true;
        } else if (visitedButton.getText().equals("X")) {
            return false;
        } else {
            markFieldAsVisited(index, row, column, visitedButton);
            return false;
        }
    }

    private void markFieldAsVisited(Index index, int row, int column, Button visitedButton) {
        searchField.getGrid().getChildren().remove(visitedButton);
        visitedButton.setStyle("-fx-background-color: #89c1c7 ");
        searchField.getGrid().add(visitedButton, column, row);
        indexToContinueSearch.add(index);
    }

    public List<Index> getCurrentIndex() {
        return currentIndex;
    }


    public boolean doSearchForGreedyFirstSearch(SearchField searchField, List<Index> currentIndex) {
        this.currentIndex = new ArrayList<>(currentIndex);
        this.firstSearch=false;
        isSearchForGreedyFirstSearch = true;
        //currentIndex.get(0) because this is the field with ne lowest manhattan distance to the goal
        return doSearch(searchField);
    }

}
