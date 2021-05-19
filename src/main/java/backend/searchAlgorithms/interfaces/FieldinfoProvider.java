package backend.searchAlgorithms.interfaces;

import backend.searchAlgorithms.Direction;
import backend.searchAlgorithms.Field;
import backend.searchAlgorithms.Index;

import java.util.List;

public interface FieldinfoProvider {
    List<Index> getNextIndices(Index index, Direction dir, Field field);
}
