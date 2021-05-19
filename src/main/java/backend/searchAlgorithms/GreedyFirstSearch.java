package backend.searchAlgorithms;

import backend.common.TwoDimensionalFieldChecker;
import backend.common.TwoDimensionalIndiceProvider;
import backend.searchAlgorithms.interfaces.FieldChecker;
import backend.searchAlgorithms.interfaces.FieldinfoProvider;
import backend.searchAlgorithms.interfaces.HeuristicSearchAlgorithm;
import frontend.Button;
import frontend.ResultDisplayer;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Heuristic algorithm.
//Distance to goal is calculated by Manhattan Distance.
public class GreedyFirstSearch implements HeuristicSearchAlgorithm {

    private List<Index> currentIndex = new ArrayList<>();
    private boolean firstRun = true;
    private Button[][] buttons;
    private List<Index> indexToContinueSearch = new ArrayList<>();
    private GridPane searchField = new GridPane();
    private FieldChecker fieldChecker = new TwoDimensionalFieldChecker();
    private FieldinfoProvider fieldinfoProvider = new TwoDimensionalIndiceProvider();
    private BreadthFirstSearch bfs = new BreadthFirstSearch();
    private Index indexWithShortestDistance;
    boolean flushOfAllVisitedNeeded = false;

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField) {
        this.buttons = buttons;
        this.searchField = searchField;
        if (firstRun) {
            currentIndex.add(startField);
            firstRun = false;
        }

        for(Index curr : currentIndex){
            curr.setManhattanDistance(heuristic(curr, endField));
        }

        List<Index> modifiableList = new ArrayList<>(currentIndex);
        Collections.sort(modifiableList);
        currentIndex = modifiableList;
        indexWithShortestDistance = currentIndex.get(0);

        return canAnyIndexReachTheGoal(searchField, buttons, endField);
    }

    private boolean canAnyIndexReachTheGoal(GridPane searchField, Button[][] buttons, Index endField) {
        boolean breadthFirstSearchNeeded = false;
        for (Index index : currentIndex) {
            Direction directionToGo = directionToGoNext(index, endField);
            Field field = new Field();
            field.setTwoDimensionalField(buttons);
            if (fieldChecker.canNextFieldByDirectionBeReached(index, directionToGo, field)) {
                breadthFirstSearchNeeded =false;
                flushOfAllVisitedNeeded=true;
                if (checkIfTargetCanBeReached(searchField, buttons, index, directionToGo)){
                    return true;
                }
                break;
            } else{
                breadthFirstSearchNeeded = true;
            }
        }

        if(breadthFirstSearchNeeded) {
            bfs.doSearchForGreedyFirstSearch(searchField, buttons, currentIndex, indexWithShortestDistance);
            currentIndex = bfs.getCurrentIndex();
            //Current index is in a "tunnel"
            while (currentIndex.size()==0){
                restartSearchWithRemainingIndex(searchField, buttons);
            }
        } else {
            currentIndex = List.copyOf(indexToContinueSearch);
            indexToContinueSearch.clear();
        }
        return false;
    }

    private void restartSearchWithRemainingIndex(GridPane searchField, Button[][] buttons) {
        indexWithShortestDistance = indexWithShortestDistance.getPreviousIndex();
        currentIndex = new ArrayList<>(Arrays.asList(indexWithShortestDistance));
        bfs.doSearchForGreedyFirstSearch(searchField, buttons, currentIndex, indexWithShortestDistance);
        currentIndex = bfs.getCurrentIndex();
    }

    private boolean checkIfTargetCanBeReached(GridPane searchField, Button[][] buttons, Index index, Direction directionToGo) {
        Field field = new Field();
        field.setTwoDimensionalField(buttons);
        List<Index> nextIndices = fieldinfoProvider.getNextIndices(index, directionToGo, field);
        for (Index nextIndex : nextIndices) {
            if (checkEndPosition(nextIndex)) {
                System.out.println("Target found at: [" + nextIndex.getRow() + "][" + nextIndex.getColumn() + "]");
                ResultDisplayer.displayResult(buttons, nextIndex, searchField);
                return true;
            }
        }
        return false;
    }

    private boolean checkEndPosition(Index index) {
        int row = index.getRow();
        int column = index.getColumn();
        buttons[row][column].setVisited(true);
        Button visitedButton = buttons[row][column];
        if (visitedButton.getText().equals("Z")) {
            return true;
        } else {
            markFieldAsVisited(index, row, column, visitedButton);
            return false;
        }
    }

    private void markFieldAsVisited(Index index, int row, int column, Button visitedButton) {
        searchField.getChildren().remove(visitedButton);
        visitedButton.setStyle("-fx-background-color: #89c1c7 ");
        searchField.add(visitedButton, column, row);
        indexToContinueSearch.add(index);
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
