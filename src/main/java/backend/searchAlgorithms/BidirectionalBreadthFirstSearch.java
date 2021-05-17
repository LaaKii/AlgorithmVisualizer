package backend.searchAlgorithms;

import backend.searchAlgorithms.interfaces.HeuristicSearchAlgorithm;
import frontend.Button;
import frontend.ResultDisplayer;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

public class BidirectionalBreadthFirstSearch implements HeuristicSearchAlgorithm {

    BreadthFirstSearch startBreadthFirstSearch = new BreadthFirstSearch();
    BreadthFirstSearch endBreadthFirstSearch = new BreadthFirstSearch();

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField) {
        boolean startSearchTerminated = startBreadthFirstSearch.doSearch(searchField, buttons, startField);
        if (startSearchTerminated){
            return true;
        }
        boolean endSearchTerminated = endBreadthFirstSearch.doSearch(searchField, buttons, endField);
        if(endSearchTerminated){
            return true;
        }

        List<Index> intersectionBetweenStartAndEndSearch = getIntersectedIndicesBetweenStartAndEndSearch();
        return checkIfSearchIsFinished(searchField, buttons, intersectionBetweenStartAndEndSearch);
    }

    private boolean checkIfSearchIsFinished(GridPane searchField, Button[][] buttons, List<Index> intersectionBetweenStartAndEndSearch) {
        //Target found.
        if (intersectionBetweenStartAndEndSearch.size() > 0) {
            Index startSearchIndex = intersectionBetweenStartAndEndSearch.get(0);
            Index endSearchIndex = new Index();
            for(Index endIndex : endBreadthFirstSearch.getCurrentIndex()){
                if (endIndex.getColumn() == startSearchIndex.getColumn() && endIndex.getRow() == startSearchIndex.getRow()
                    || endIndex.getColumn()+1 == startSearchIndex.getColumn() && endIndex.getRow() == startSearchIndex.getRow()
                    || endIndex.getColumn()-1 == startSearchIndex.getColumn() && endIndex.getRow() == startSearchIndex.getRow()
                    || endIndex.getColumn() == startSearchIndex.getColumn() && endIndex.getRow()+1 == startSearchIndex.getRow()
                    || endIndex.getColumn() == startSearchIndex.getColumn() && endIndex.getRow()-1 == startSearchIndex.getRow()){
                    endSearchIndex = endIndex;
                    break;
                }
            }
            ResultDisplayer.displayResult(buttons, startSearchIndex, searchField);
            ResultDisplayer.displayResult(buttons, endSearchIndex, searchField);
            return true;
        } else {
            return false;
        }
    }

    private List<Index> getIntersectedIndicesBetweenStartAndEndSearch() {
        List<Index> intersectionBetweenStartAndEndSearch = startBreadthFirstSearch.getCurrentIndex().stream().filter(currentField -> {
            List<Index> endBreadthIndex = endBreadthFirstSearch.getCurrentIndex();
            for (Index ind : endBreadthIndex) {
                if (currentField.getRow() == ind.getRow() && currentField.getColumn() == ind.getColumn()
                    || currentField.getRow()+1 == ind.getRow() && currentField.getColumn() == ind.getColumn()
                    || currentField.getRow()-1 == ind.getRow() && currentField.getColumn() == ind.getColumn()
                    || currentField.getRow() == ind.getRow() && currentField.getColumn()+1 == ind.getColumn()
                    || currentField.getRow() == ind.getRow() && currentField.getColumn()-1 == ind.getColumn()){
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        return intersectionBetweenStartAndEndSearch;
    }

}
