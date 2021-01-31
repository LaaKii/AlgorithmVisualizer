package backend.searchAlgorithms;


import javafx.scene.control.Button;

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
            index.setRow(indexToCheck.getRow()+1);
            return canDirectionBeReached(indexToCheck, field);
        } else if(direction == Direction.SOUTHEAST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()+1);
            boolean southCheck = canDirectionBeReached(index, field);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean eastCheck = canDirectionBeReached(index, field);
            return southCheck && eastCheck;
        }
        return false;
    }

    public List<Index> getNextIndices(Index index, Direction dir){
        List<Index> nextIndices = new ArrayList<>();
        if (dir == Direction.SOUTHEAST){
            Index southIndex = Index.copy(index);
            southIndex.setRow(index.getRow()+1);
            southIndex.setPreviousIndex(index);
            Index southEastIndex = Index.copy(index);
            southEastIndex.setRow(index.getRow()+1);
            southEastIndex.setColumn(index.getColumn()+1);
            southEastIndex.setPreviousIndex(southIndex);
            nextIndices.add(southIndex);
            nextIndices.add(southEastIndex);
        } else if (dir == Direction.SOUTHWEST) {
            Index southIndex = Index.copy(index);
            southIndex.setRow(index.getRow()+1);
            Index southWestIndex = Index.copy(index);
            southWestIndex.setRow(index.getRow()+1);
            southWestIndex.setColumn(index.getColumn()-1);
            southWestIndex.setPreviousIndex(southIndex);
            nextIndices.add(southIndex);
            nextIndices.add(southWestIndex);
        } else if (dir == Direction.SOUTH){
            Index southIndex = Index.copy(index);
            southIndex.setRow(index.getRow()+1);
            nextIndices.add(southIndex);
        } else if (dir == Direction.NORTHEAST){
            Index northIndex = Index.copy(index);
            northIndex.setRow(index.getRow()-1);
            Index northEastIndex = Index.copy(index);
            northEastIndex.setRow(index.getRow()+1);
            northEastIndex.setColumn(index.getColumn()+1);
            northEastIndex.setPreviousIndex(northIndex);
            nextIndices.add(northIndex);
            nextIndices.add(northEastIndex);
        } else if (dir == Direction.NORTHWEST){
            Index northIndex = Index.copy(index);
            northIndex.setRow(index.getRow()-1);
            Index northEastIndex = Index.copy(index);
            northEastIndex.setRow(index.getRow()+1);
            northEastIndex.setColumn(index.getColumn()-1);
            northEastIndex.setPreviousIndex(northIndex);
            nextIndices.add(northIndex);
            nextIndices.add(northEastIndex);
        } else if (dir == Direction.NORTH){
            Index northIndex = Index.copy(index);
            northIndex.setRow(index.getRow()-1);
            northIndex.setPreviousIndex(index);
            nextIndices.add(northIndex);
        } else if(dir == Direction.EAST){
            Index eastIndex= Index.copy(index);
            eastIndex.setColumn(index.getColumn()+1);
            eastIndex.setPreviousIndex(index);
            nextIndices.add(eastIndex);
        } else if (dir == Direction.WEST){
            Index westIndex = Index.copy(index);
            westIndex.setColumn(index.getColumn()-1);
            westIndex.setPreviousIndex(index);
            nextIndices.add(westIndex);
        }
        return nextIndices;
    }

    private boolean canDirectionBeReached(Index nextIndex, Button[][] field){
        return !field[nextIndex.getRow()][nextIndex.getColumn()].getText().equals("X");
    }
}
