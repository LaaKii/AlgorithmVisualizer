package backend.searchAlgorithms;

import backend.searchAlgorithms.interfaces.HeuristicSearchAlgorithm;
import frontend.ResultDisplayer;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

public class BidirectionalBreadthFirstSearch implements HeuristicSearchAlgorithm {

    BreadthFirstBasicSearch startBreadthFirstSearch = new BreadthFirstBasicSearch();
    BreadthFirstBasicSearch endBreadthFirstSearch = new BreadthFirstBasicSearch();

    @Override
    public boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField) {
        startBreadthFirstSearch.doSearch(searchField, buttons, startField);
        endBreadthFirstSearch.doSearch(searchField, buttons, endField);

        startBreadthFirstSearch.getCurrentIndex().stream().forEach(System.out::println);
        System.out.println("---------------------------");
        endBreadthFirstSearch.getCurrentIndex().stream().forEach(System.out::println);

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

}
