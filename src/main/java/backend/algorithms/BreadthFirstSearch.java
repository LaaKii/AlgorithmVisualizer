package backend.algorithms;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class BreadthFirstSearch implements SearchAlgorithm{

    private List<Index> currentIndex = new ArrayList<>();
    private GridPane searchField = new GridPane();
    private Button[][] buttons;

    @Override
    public void doSearch(GridPane searchField, Button[][] buttons, Index startField) {
        this.searchField = searchField;
        this.buttons = buttons;
        boolean[][] visited = new boolean[searchField.getRowCount()][searchField.getColumnCount()];
        boolean foundTarget = false;
        currentIndex.add(startField);


        while(!foundTarget){
            for (Index index : currentIndex){
                foundTarget = searchIndexInEveryDirection(index);
                if (foundTarget){
                    break;
                }
            }
        }
    }

    public boolean searchIndexInEveryDirection(Index index){
        if (buttons[index.getColumn()][index.getRow()].getText().equals("0")){

        }else if (buttons[index.getColumn()][index.getRow()].getText().equals("Z")){
            return true;
        }
        return false;
    }
}
