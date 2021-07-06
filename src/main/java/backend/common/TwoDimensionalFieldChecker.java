package backend.common;

import backend.searchAlgorithms.Direction;
import backend.searchAlgorithms.Field;
import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.interfaces.FieldChecker;
import frontend.Button;

import java.util.ArrayList;
import java.util.List;

public class TwoDimensionalFieldChecker implements FieldChecker {

    @Override
    public boolean canNextFieldByDirectionBeReached(Index indexToCheck, Direction direction, Field field) {
        Button[][] twoDimensionalField = field.getTwoDimensionalField();
        if (direction == Direction.SOUTH && indexToCheck.getRow()+1<twoDimensionalField.length){
            indexToCheck.setRow(indexToCheck.getRow()+1);
            return canDirectionBeReached(indexToCheck, twoDimensionalField);
        } else if(direction == Direction.NORTH && indexToCheck.getRow()-1 >= 0){
            indexToCheck.setRow(indexToCheck.getRow()-1);
            return canDirectionBeReached(indexToCheck, twoDimensionalField);
        } else if(direction == Direction.WEST && indexToCheck.getColumn()-1 >= 0){
            indexToCheck.setColumn(indexToCheck.getColumn()-1);
            return canDirectionBeReached(indexToCheck, twoDimensionalField);
        } else if(direction == Direction.EAST && indexToCheck.getColumn()+1 < twoDimensionalField[indexToCheck.getRow()].length){
            Index index = Index.copy(indexToCheck);
            index.setColumn(indexToCheck.getColumn()+1);
            return canDirectionBeReached(index, twoDimensionalField);
        } else if(direction == Direction.SOUTHEAST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()+1);
            boolean southCheck = canDirectionBeReached(index, twoDimensionalField);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean eastCheck = canDirectionBeReached(index, twoDimensionalField);
            return southCheck && eastCheck;
        } else if(direction == Direction.NORTHEAST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()-1);
            boolean northCheck = canDirectionBeReached(index, twoDimensionalField);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean northEastCheck = canDirectionBeReached(index, twoDimensionalField) && northCheck;
            index = Index.copy(indexToCheck);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean eastCheck = canDirectionBeReached(index, twoDimensionalField);
            index.setRow(indexToCheck.getRow()-1);
            boolean eastNorthCheck = eastCheck && canDirectionBeReached(index, twoDimensionalField);
            return northEastCheck || eastNorthCheck;
        } else if(direction == Direction.SOUTHWEST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()+1);
            boolean southCheck = canDirectionBeReached(index, twoDimensionalField);
            index.setColumn(indexToCheck.getColumn()-1);
            boolean westCheck = canDirectionBeReached(index, twoDimensionalField);
            return southCheck && westCheck;
        } else if(direction == Direction.NORTHWEST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()-1);
            boolean northCheck = canDirectionBeReached(index, twoDimensionalField);
            index.setColumn(indexToCheck.getColumn()-1);
            boolean westCheck = canDirectionBeReached(index, twoDimensionalField);
            return northCheck && westCheck;
        }
        return false;
    }

    private boolean canDirectionBeReached(Index nextIndex, Button[][] field){
        return !field[nextIndex.getRow()][nextIndex.getColumn()].getText().equals("X") && !field[nextIndex.getRow()][nextIndex.getColumn()].isVisited();
    }
}
