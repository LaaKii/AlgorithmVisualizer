package backend.searchAlgorithms;

import backend.searchAlgorithms.interfaces.HeuristicSearchAlgorithm;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

//Heuristic algorithm.
//Distance to goal is calculated by Manhattan Distance.
public class GreedyFirstSearch implements HeuristicSearchAlgorithm {

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField) {

        return false;
    }

    public Index heuristic(Index currentIndex, Index goalIndex){
        return null;
    }
}
