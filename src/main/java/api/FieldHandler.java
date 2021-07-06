package api;

import api.internal.FieldType;
import api.internal.IndexIsNotInFieldException;
import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.SearchField;
import frontend.Button;
import frontend.VisualizerField;
import java.util.*;

public class FieldHandler {

    private SearchField searchField;

    public FieldHandler(SearchField searchField) {
        this.searchField = searchField;
    }

    private void commitChangesToField(Map<Index, FieldType> indicesToMakeToAWall){
        Button[][] buttons = searchField.getButtons();
        for(Map.Entry<Index, FieldType> cursor : indicesToMakeToAWall.entrySet()){
            Index key = cursor.getKey();
            FieldType field = cursor.getValue();
            if (isIndexInField(key)){
                buttons[key.getColumn()][key.getRow()].setText(field.getText());
            }else{
                throw new IndexIsNotInFieldException("index: " + key + " is not in field");
            }
        }
        searchField.setButtons(buttons);
    }

    public boolean isIndexInField(Index key){
        return (searchField.getButtons().length < key.getRow() && searchField.getButtons()[0].length < key.getColumn());
    }

    public int getTotalFieldCount(){
        int amount = 0;
        for(int i = 0; i< searchField.getButtons().length; i++){
            for(int j = 0; j< searchField.getButtons()[j].length; j++){
                amount++;
            }
        }
        return amount;
    }

    public void changeAllEmptyFieldsWithWalls(){
        Button[][] buttons = searchField.getButtons();
        for(int i = 0; i<buttons.length; i++){
            for(int j = 0; j<buttons[i].length; j++){
                if (buttons[i][j].getText().equalsIgnoreCase("0")){
                    buttons[i][j].setText("X");
                }
            }
        }
        searchField.setButtons(buttons);
    }

    public void changeAllWallsWithEmptyFields(){
        Button[][] buttons = searchField.getButtons();
        for(int i = 0; i<buttons.length; i++){
            for(int j = 0; j<buttons[i].length; j++){
                if (buttons[i][j].getText().equalsIgnoreCase("X")){
                    buttons[i][j].setText("0");
                }
            }
        }
        searchField.setButtons(buttons);
    }

    public SearchField getSearchField(){
        return searchField;
    }

    public void reInitFieldWithRandomWalls(){
        Button[][] buttons = searchField.getButtons();
        for(int i = 0; i<buttons.length; i++){
            for (int j = 0; j < buttons[i].length; j++){
                Button currentButton = buttons[i][j];
                if (currentButton.isStartField() || currentButton.getText().equalsIgnoreCase("Z")){
                    continue;
                }
                if (Math.random()<0.5){
                    buttons[i][j].setText("X");
                }else{
                    buttons[i][j].setText("0");
                }
            }
        }
        searchField.setButtons(buttons);
    }

    public int getAmountOfWalls(){
        int amount = 0;
        Button[][] buttons = searchField.getButtons();
        for(int i = 0; i<buttons.length; i++){
            for (int j = 0; j < buttons[i].length; j++){
                if (buttons[i][j].getText().equalsIgnoreCase("X")){
                    amount++;
                }
            }
        }
        return amount;
    }
    
    public Optional<Index> getLocationOfStartFieldOrNull(){
        return Optional.ofNullable(searchField.getStartField());
    }

    public Optional<Index> getLocationOfEndFieldOrNull(){
        return Optional.ofNullable(searchField.getEndField());
    }

    class FieldChanger {
        private Map<Index, FieldType> indicesToChange = new HashMap<>();

        public void commitChanges(){
            commitChangesToField(indicesToChange);
        }

        public FieldChanger changeFieldTo(Index indexToChange, FieldType newType){
            indicesToChange.put(indexToChange, newType);
            return this;
        }
    }

}
