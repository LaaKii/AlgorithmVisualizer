package backend.searchAlgorithms;

import frontend.Button;

public class Field {
    private Button[][] twoDimensionalField;
    private Button[][][] threeDimensionalField;

    public Button[][] getTwoDimensionalField() {
        return twoDimensionalField;
    }

    public void setTwoDimensionalField(Button[][] twoDimensionalField) {
        this.twoDimensionalField = twoDimensionalField;
    }

    public Button[][][] getThreeDimensionalField() {
        return threeDimensionalField;
    }

    public void setThreeDimensionalField(Button[][][] threeDimensionalField) {
        this.threeDimensionalField = threeDimensionalField;
    }
}
