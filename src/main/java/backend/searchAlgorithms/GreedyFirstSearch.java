package backend.searchAlgorithms;

import backend.searchAlgorithms.interfaces.HeuristicSearchAlgorithm;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.*;

//Heuristic algorithm.
//Distance to goal is calculated by Manhattan Distance.
public class GreedyFirstSearch implements HeuristicSearchAlgorithm {

    private List<Index> currentIndex = new ArrayList<>();
    private Index indexWithShortestManhattanDistance = new Index(10000, 10000, new Index(), Integer.MAX_VALUE);
    private boolean firstRun = true;

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField) {
        if (firstRun){
            currentIndex.add(startField);
        }

        for(Index index : currentIndex) {
            int distance = heuristic(startField, endField);
            if (distance < indexWithShortestManhattanDistance.getManhattanDistance()){
                index.setManhattanDistance(distance);
                indexWithShortestManhattanDistance  = index;
            }
        }

        //TODO check for walls, goals, etc, ...
       DirectionToGo d = checkBoundaries(indexWithShortestManhattanDistance, endField);

        return false;
    }

    private DirectionToGo checkBoundaries(Index indexWithShortestManhattanDistance, Index goalIndex) {
        if (goalIndex.getColumn()>indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() > indexWithShortestManhattanDistance.getRow()){
            return DirectionToGo.SOUTHEAST;
        } else if(goalIndex.getColumn() > indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() == indexWithShortestManhattanDistance.getRow()){
            return DirectionToGo.EAST;
        } else if(goalIndex.getColumn() > indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() < indexWithShortestManhattanDistance.getRow()){
            return DirectionToGo.NORTHEAST;
        } else if(goalIndex.getColumn() == indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() < indexWithShortestManhattanDistance.getRow()){
            return DirectionToGo.NORTH;
        } else if(goalIndex.getColumn() == indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() > indexWithShortestManhattanDistance.getRow()){
            return DirectionToGo.SOUTH;
        } else if(goalIndex.getColumn() < indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() > indexWithShortestManhattanDistance.getRow()){
            return DirectionToGo.SOUTHWEST;
        } else if(goalIndex.getColumn() < indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() == indexWithShortestManhattanDistance.getRow()){
            return DirectionToGo.WEST;
        } else if(goalIndex.getColumn() < indexWithShortestManhattanDistance.getColumn() && goalIndex.getRow() < indexWithShortestManhattanDistance.getRow()){
            return DirectionToGo.NORTHWEST;
        } else{
            return DirectionToGo.EVERYWHERE;
        }
    }


    public int heuristic(Index currentIndex, Index goalIndex){
        return Math.abs(currentIndex.getColumn() - goalIndex.getColumn()) + Math.abs(currentIndex.getRow() - goalIndex.getRow());
    }
}
