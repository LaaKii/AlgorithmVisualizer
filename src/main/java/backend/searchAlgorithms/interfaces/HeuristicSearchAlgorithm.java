package backend.searchAlgorithms.interfaces;

import backend.searchAlgorithms.Index;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public interface HeuristicSearchAlgorithm extends SearchAlgorithm{

    boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField);
}
