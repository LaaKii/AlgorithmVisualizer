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
    public boolean doSearch(SearchField searchField) {
        boolean startSearchTerminated = startBreadthFirstSearch.doSearch(searchField);
        if (startSearchTerminated){
            return true;
        }
        searchField.setStartField(searchField.getEndField());
        boolean endSearchTerminated = endBreadthFirstSearch.doSearch(searchField);
        if(endSearchTerminated){
            return true;
        }

        return checkIfSearchIsFinished(searchField, getIntersectedIndicesBetweenStartAndEndSearch());
    }

    private boolean checkIfSearchIsFinished(SearchField searchField, List<Index> intersectionBetweenStartAndEndSearch) {
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
            ResultDisplayer.displayResult(searchField, startSearchIndex);
            ResultDisplayer.displayResult(searchField, endSearchIndex);
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
