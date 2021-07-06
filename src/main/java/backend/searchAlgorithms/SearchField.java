package backend.searchAlgorithms;

import frontend.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class SearchField {
    private GridPane grid = new GridPane();
    private Button[][] buttons;
    private Index startField;
    private Index endField;

    public void initField(Button[][] field){
        this.buttons = field;
        grid.setAlignment(Pos.CENTER);
        boolean startFound = false;
        boolean endFound = false;
        for(int i = 0; i<field.length; i++){
            for(int j = 0; j<field[i].length; j++){
                Button tempButton = field[i][j];
                tempButton.setOnAction(actionEvent -> {
                    tempButton.switchField(tempButton.getText());
                });
                if (!startFound && tempButton.getText().equals("S")){
                    tempButton.setStyle("-fx-background-color: #f1f514; ");
                    startField = new Index(i,j);
                    startFound=true;
                }else if(!endFound && tempButton.getText().equals("Z")){
                    tempButton.setStyle("-fx-background-color: #f1f514; ");
                    endField = new Index(i,j);
                    endFound = true;
                }else if(tempButton.getText().equals("X")){
                    tempButton.setStyle("-fx-background-color: #aaadb3; ");
                }
                grid.add(tempButton, j,i);
            }
        }
    }



    public GridPane getGrid() {
        return grid;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public Button[][] getButtons() {
        return buttons;
    }

    public void setButtons(Button[][] buttons) {
        this.buttons = buttons;
    }


    public Index getStartField() {
        return startField;
    }

    public void setStartField(Index startField) {
        this.startField = startField;
    }

    public Index getEndField() {
        return endField;
    }

    public void setEndField(Index endField) {
        this.endField = endField;
    }

}
