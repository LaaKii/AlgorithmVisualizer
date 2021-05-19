package backend.common;

import backend.searchAlgorithms.Direction;
import backend.searchAlgorithms.Index;
import frontend.Button;

import java.util.ArrayList;
import java.util.List;

public class FieldChecker {

    public boolean canNextFieldByDirectionBeReached(Index indexToCheck, Direction direction, Button[][] field){
        if (direction == Direction.SOUTH && indexToCheck.getRow()+1<field.length){
            indexToCheck.setRow(indexToCheck.getRow()+1);
            return canDirectionBeReached(indexToCheck, field);
        } else if(direction == Direction.NORTH && indexToCheck.getRow()-1 >= 0){
            indexToCheck.setRow(indexToCheck.getRow()-1);
            return canDirectionBeReached(indexToCheck, field);
        } else if(direction == Direction.WEST && indexToCheck.getColumn()-1 >= 0){
            indexToCheck.setColumn(indexToCheck.getColumn()-1);
            return canDirectionBeReached(indexToCheck, field);
        } else if(direction == Direction.EAST && indexToCheck.getColumn()+1 < field[indexToCheck.getRow()].length){
            Index index = Index.copy(indexToCheck);
            index.setColumn(indexToCheck.getColumn()+1);
            return canDirectionBeReached(index, field);
        } else if(direction == Direction.SOUTHEAST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()+1);
            boolean southCheck = canDirectionBeReached(index, field);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean eastCheck = canDirectionBeReached(index, field);
            return southCheck && eastCheck;
        } else if(direction == Direction.NORTHEAST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()-1);
            boolean northCheck = canDirectionBeReached(index, field);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean northEastCheck = canDirectionBeReached(index, field) && northCheck;
            index = Index.copy(indexToCheck);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean eastCheck = canDirectionBeReached(index, field);
            index.setRow(indexToCheck.getRow()-1);
            boolean eastNorthCheck = eastCheck && canDirectionBeReached(index, field);
            return northEastCheck || eastNorthCheck;
        } else if(direction == Direction.SOUTHWEST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()+1);
            boolean southCheck = canDirectionBeReached(index, field);
            index.setColumn(indexToCheck.getColumn()-1);
            boolean westCheck = canDirectionBeReached(index, field);
            return southCheck && westCheck;
        } else if(direction == Direction.NORTHWEST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()-1);
            boolean northCheck = canDirectionBeReached(index, field);
            index.setColumn(indexToCheck.getColumn()-1);
            boolean westCheck = canDirectionBeReached(index, field);
            return northCheck && westCheck;
        }
        return false;
    }

    private boolean canDirectionBeReached(Index nextIndex, Button[][] field){
        return !field[nextIndex.getRow()][nextIndex.getColumn()].getText().equals("X") && !field[nextIndex.getRow()][nextIndex.getColumn()].isVisited();
    }
}
