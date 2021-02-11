package backend.searchAlgorithms.interfaces;

import backend.searchAlgorithms.Index;
import frontend.Button;
import javafx.scene.layout.GridPane;

public interface SearchAlgorithm {

    boolean doSearch(GridPane searchField, Button[][] buttons, Index startField, Index endField);

}
