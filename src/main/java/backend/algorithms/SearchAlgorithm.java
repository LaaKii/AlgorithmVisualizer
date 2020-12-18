package backend.algorithms;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public interface SearchAlgorithm{

    void doSearch(GridPane searchField, Button[][] buttons, Index startField);
}
