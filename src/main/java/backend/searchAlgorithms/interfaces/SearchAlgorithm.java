package backend.searchAlgorithms.interfaces;

import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.SearchField;
import frontend.Button;
import javafx.scene.layout.GridPane;

public interface SearchAlgorithm {

    boolean doSearch(SearchField searchField);

}
