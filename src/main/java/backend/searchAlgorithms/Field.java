package backend.searchAlgorithms;

import frontend.Button;

public final class Field {
    private final Button[][] twoDimensionalField;
    private final Button[][][] threeDimensionalField;

    public Field(Button[][] twoDimensionalField){
        this.twoDimensionalField = twoDimensionalField;
        this.threeDimensionalField = null;
    }

    public Field(Button[][][] threeDimensionalField){
        this.threeDimensionalField=threeDimensionalField;
        this.twoDimensionalField=null;
    }

    public Button[][] getTwoDimensionalField() {
        return twoDimensionalField;
    }

    public Button[][][] getThreeDimensionalField() {
        return threeDimensionalField;
    }

}
