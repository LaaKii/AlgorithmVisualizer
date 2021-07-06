package backend.searchAlgorithms;

import frontend.Button;

import java.util.Arrays;

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
        if (twoDimensionalField==null){
            throw new RuntimeException("Wrong constructor invoked. Returning null object now");
        }
        return twoDimensionalField;
    }

    public Button[][][] getThreeDimensionalField() {
        if (threeDimensionalField==null){
            throw new RuntimeException("Wrong constructor invoked. Returning null object now");
        }
        return threeDimensionalField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Arrays.equals(twoDimensionalField, field.twoDimensionalField) && Arrays.equals(threeDimensionalField, field.threeDimensionalField);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(twoDimensionalField);
        result = 31 * result + Arrays.hashCode(threeDimensionalField);
        return result;
    }
}
