package backend.searchAlgorithms.interfaces;

import backend.searchAlgorithms.Direction;
import backend.searchAlgorithms.Field;
import backend.searchAlgorithms.Index;

public interface FieldChecker {
    boolean canNextFieldByDirectionBeReached(Index indexToCheck, Direction direction, Field field);
}
