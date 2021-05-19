package backend.common;

import backend.searchAlgorithms.Direction;
import backend.searchAlgorithms.Field;
import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.interfaces.FieldinfoProvider;
import frontend.Button;

import java.util.List;

public class ThreeDimensionalIndiceProvider implements FieldinfoProvider {
    @Override
    public List<Index> getNextIndices(Index index, Direction dir, Field field) {
        Button[][][] threeDimensionalField = field.getThreeDimensionalField();
        return null;
    }
}
