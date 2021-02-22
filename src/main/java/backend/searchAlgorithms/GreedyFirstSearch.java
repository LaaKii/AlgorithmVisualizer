package backend.searchAlgorithms;

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
    private FieldChecker fieldChecker = new FieldChecker();
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

        boolean breadthFirstSearchNeeded = false;


        for (Index index : currentIndex) {
            Direction directionToGo = directionToGoNext(index, endField);
            if (fieldChecker.canNextFieldByDirectionBeReached(index, directionToGo, buttons)) {
                breadthFirstSearchNeeded=false;
                flushOfAllVisitedNeeded=true;
                List<Index> nextIndices = fieldChecker.getNextIndices(index, directionToGo, buttons);
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
            //Todo store buttons before flush and then restore them...
//            if(flushOfAllVisitedNeeded){
//                flushAllVisited(buttons);
//                flushOfAllVisitedNeeded=false;
//            }
            bfs.doSearchForGreedyFirstSearch(searchField, buttons, currentIndex, indexWithShortestDistance);
            currentIndex = bfs.getCurrentIndex();
            //Current index is in a "tunnel"
            while (currentIndex.size()==0){
                indexWithShortestDistance = indexWithShortestDistance.getPreviousIndex();
                currentIndex = new ArrayList<>(Arrays.asList(indexWithShortestDistance));
                bfs.doSearchForGreedyFirstSearch(searchField, buttons, currentIndex, indexWithShortestDistance);
                currentIndex = bfs.getCurrentIndex();
            }
        } else {
            currentIndex = List.copyOf(indexToContinueSearch);
            indexToContinueSearch.clear();
        }

        return false;
    }

    private void flushAllVisited(Button[][] buttons) {
        for(int i = 0; i<buttons.length; i++){
            for (int j = 0; j<buttons[i].length; j++){
                buttons[i][j].setVisited(false);
            }
        }
    }

    private boolean checkEndPosition(Index index) {
        int row = index.getRow();
        int column = index.getColumn();
        buttons[row][column].setVisited(true);
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
