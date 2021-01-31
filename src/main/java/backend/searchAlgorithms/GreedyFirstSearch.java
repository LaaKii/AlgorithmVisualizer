package backend.searchAlgorithms;

import backend.searchAlgorithms.interfaces.HeuristicSearchAlgorithm;
import frontend.ResultDisplayer;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Heuristic algorithm.
//Distance to goal is calculated by Manhattan Distance.
public class GreedyFirstSearch implements HeuristicSearchAlgorithm {

    private List<Index> currentIndex = new ArrayList<>();
    private Index indexWithShortestManhattanDistance = new Index(10000, 10000, new Index(), Integer.MAX_VALUE);
    private boolean firstRun = true;
    private boolean[][] visited;
    private Button[][] buttons;
    private List<Index> indexToContinueSearch = new ArrayList<>();
    private GridPane searchField = new GridPane();
    private FieldChecker fieldChecker = new FieldChecker();
    private BreadthFirstSearch bfs = new BreadthFirstSearch();

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField) {
        this.buttons = buttons;
        this.searchField = searchField;
        if (firstRun) {
            currentIndex.add(startField);
            visited = new boolean[buttons.length][buttons[0].length];
            firstRun = false;
        }

        for(Index curr : currentIndex){
            curr.setManhattanDistance(heuristic(curr, endField));
        }

        List<Index> modifiableList = new ArrayList<>(currentIndex);
        Collections.sort(modifiableList);
        currentIndex = modifiableList;

        boolean breadthFirstSearchNeeded = false;

        for (Index index : currentIndex) {
            Direction directionToGo = directionToGoNext(index, endField);
            if (fieldChecker.canNextFieldByDirectionBeReached(index, directionToGo, buttons)) {
                List<Index> nextIndices = fieldChecker.getNextIndices(index, directionToGo);
                for (Index nextIndex : nextIndices) {
                    if (checkEndPosition(nextIndex)) {
                        System.out.println("Target found at: [" + nextIndex.getRow() + "][" + nextIndex.getColumn() + "]");
                        ResultDisplayer.displayResult(buttons, nextIndex, searchField);
                        return true;
                    }
                }
                break;
            } else{
                breadthFirstSearchNeeded = true;
            }
        }

        if(breadthFirstSearchNeeded) {
            bfs.doSearch(searchField, buttons, currentIndex.get(0), endField);
            currentIndex = bfs.getCurrentIndex();
        } else {
            currentIndex = List.copyOf(indexToContinueSearch);
            indexToContinueSearch.clear();
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
        } else {
            searchField.getChildren().remove(visitedButton);
            visitedButton.setStyle("-fx-background-color: #89c1c7 ");
            searchField.add(visitedButton, column, row);
            indexToContinueSearch.add(index);
            return false;
        }
    }

    private Direction directionToGoNext(Index indexWithShortestManhattanDistance, Index goalIndex) {
        if (goalIndex.getColumn() > indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() > indexWithShortestManhattanDistance.getRow()) {
            return Direction.SOUTHEAST;
        } else if (goalIndex.getColumn() > indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() == indexWithShortestManhattanDistance.getRow()) {
            return Direction.EAST;
        } else if (goalIndex.getColumn() > indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() < indexWithShortestManhattanDistance.getRow()) {
            return Direction.NORTHEAST;
        } else if (goalIndex.getColumn() == indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() < indexWithShortestManhattanDistance.getRow()) {
            return Direction.NORTH;
        } else if (goalIndex.getColumn() == indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() > indexWithShortestManhattanDistance.getRow()) {
            return Direction.SOUTH;
        } else if (goalIndex.getColumn() < indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() > indexWithShortestManhattanDistance.getRow()) {
            return Direction.SOUTHWEST;
        } else if (goalIndex.getColumn() < indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() == indexWithShortestManhattanDistance.getRow()) {
            return Direction.WEST;
        } else if (goalIndex.getColumn() < indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() < indexWithShortestManhattanDistance.getRow()) {
            return Direction.NORTHWEST;
        } else {
            return Direction.EVERYWHERE;
        }
    }


    public int heuristic(Index currentIndex, Index goalIndex) {
        return Math.abs(currentIndex.getColumn() - goalIndex.getColumn()) + Math.abs(currentIndex.getRow() - goalIndex.getRow());
    }
}
