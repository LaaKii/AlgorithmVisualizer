package backend.algorithms;

import javafx.scene.Node;
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

//        while(!foundTarget){
//            for (Index index : currentIndex){
                foundTarget = searchIndexInEveryDirection(currentIndex.get(0));
//                if (foundTarget){
//                    break;
//                }
//            }
//        }
    }


    public boolean searchIndexInEveryDirection(Index index){

        int row = index.getRow()+1;

        if (buttons.length>row){
            if (buttons[index.getRow()][index.getColumn()].getText().equals("Z")){
                return true;
            } else if(buttons[index.getRow()][index.getColumn()].getText().equals("X")){
                //TODO
            }else{
                Button visitedButton = buttons[row][index.getColumn()];
                searchField.getChildren().remove(visitedButton);
                System.out.println("Before change: " + buttons[row][index.getColumn()].getStyle());
                visitedButton.setStyle("-fx-background-color: #89c1c7 ");
                System.out.println("After change: "+ buttons[row][index.getColumn()].getStyle());
                searchField.add(visitedButton,index.getColumn(),row);
            }
        }

        return false;
    }

}
