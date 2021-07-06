package backend.common;

import backend.searchAlgorithms.Direction;
import backend.searchAlgorithms.Field;
import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.interfaces.FieldChecker;
import frontend.Button;

public class ThreeDimensionalFieldChecker implements FieldChecker {
    @Override
    public boolean canNextFieldByDirectionBeReached(Index indexToCheck, Direction direction, Field field) {
        Button[][][] buttons = field.getThreeDimensionalField();
        return false;
    }
}
