package backend.searchAlgorithms;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class FieldChecker {

    public boolean canNextFieldByDirectionBeReached(Index indexToCheck, Direction direction, Button[][] field, boolean[][] visited){
        if (direction == Direction.SOUTH && indexToCheck.getRow()+1<field.length){
            indexToCheck.setRow(indexToCheck.getRow()+1);
            return canDirectionBeReached(indexToCheck, field, visited);
        } else if(direction == Direction.NORTH && indexToCheck.getRow()-1 >= 0){
            indexToCheck.setRow(indexToCheck.getRow()-1);
            return canDirectionBeReached(indexToCheck, field, visited);
        } else if(direction == Direction.WEST && indexToCheck.getColumn()-1 >= 0){
            indexToCheck.setColumn(indexToCheck.getColumn()-1);
            return canDirectionBeReached(indexToCheck, field, visited);
        } else if(direction == Direction.EAST && indexToCheck.getColumn()+1 < field[indexToCheck.getRow()].length){
            Index index = Index.copy(indexToCheck);
            index.setColumn(indexToCheck.getColumn()+1);
            return canDirectionBeReached(index, field, visited);
        } else if(direction == Direction.SOUTHEAST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()+1);
            boolean southCheck = canDirectionBeReached(index, field, visited);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean eastCheck = canDirectionBeReached(index, field, visited);
            return southCheck && eastCheck;
        } else if(direction == Direction.NORTHEAST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()-1);
            boolean northCheck = canDirectionBeReached(index, field, visited);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean northEastCheck = canDirectionBeReached(index, field, visited) && northCheck;
            index = Index.copy(indexToCheck);
            index.setColumn(indexToCheck.getColumn()+1);
            boolean eastCheck = canDirectionBeReached(index, field, visited);
            index.setRow(indexToCheck.getRow()-1);
            boolean eastNorthCheck = eastCheck && canDirectionBeReached(index, field, visited);
            return northEastCheck || eastNorthCheck;
        } else if(direction == Direction.SOUTHWEST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()+1);
            boolean southCheck = canDirectionBeReached(index, field, visited);
            index.setColumn(indexToCheck.getColumn()-1);
            boolean westCheck = canDirectionBeReached(index, field, visited);
            return southCheck && westCheck;
        } else if(direction == Direction.NORTHWEST){
            Index index = Index.copy(indexToCheck);
            index.setRow(indexToCheck.getRow()-1);
            boolean northCheck = canDirectionBeReached(index, field, visited);
            index.setColumn(indexToCheck.getColumn()-1);
            boolean westCheck = canDirectionBeReached(index, field, visited);
            return northCheck && westCheck;
        }
        return false;
    }

    public List<Index> getNextIndices(Index index, Direction dir, Button[][] field){
        List<Index> nextIndices = new ArrayList<>();
        if (dir == Direction.SOUTHEAST){
            if (isSouthIndexReachable(index, field)){
                Index southIndex = createSouthIndex(index);
                Index southEastIndex = createEastIndex(southIndex);
                nextIndices.add(southIndex);
                nextIndices.add(southEastIndex);
            }else{
                Index eastIndex = createEastIndex(index);
                Index eastSouthIndex = createSouthIndex(eastIndex);
                nextIndices.add(eastIndex);
                nextIndices.add(eastSouthIndex);
            }
        } else if (dir == Direction.SOUTHWEST) {
            if (isSouthIndexReachable(index, field)){
                Index southIndex = createSouthIndex(index);
                Index southWestIndex = createWestIndex(southIndex);
                nextIndices.add(southIndex);
                nextIndices.add(southWestIndex);
            } else{
                Index westIndex = createWestIndex(index);
                Index westSouthIndex = createSouthIndex(westIndex);
                nextIndices.add(westIndex);
                nextIndices.add(westSouthIndex);
            }
        } else if (dir == Direction.NORTHEAST){
            if (isNorthIndexReachable(index, field)){
                Index northIndex = createNorthIndex(index);
                Index northEastIndex = createEastIndex(northIndex);
                nextIndices.add(northIndex);
                nextIndices.add(northEastIndex);
            } else{
                Index eastIndex = createEastIndex(index);
                Index eastNorthIndex = createNorthIndex(eastIndex);
                nextIndices.add(eastIndex);
                nextIndices.add(eastNorthIndex);
            }

        } else if (dir == Direction.NORTHWEST){
            if (isNorthIndexReachable(index, field)){
                Index northIndex = createNorthIndex(index);
                Index northWestIndex = createWestIndex(northIndex);
                nextIndices.add(northIndex);
                nextIndices.add(northWestIndex);
            }else{
                Index westIndex = createWestIndex(index);
                Index northWestIndex = createNorthIndex(westIndex);
                nextIndices.add(westIndex);
                nextIndices.add(northWestIndex);
            }
        } else if (dir == Direction.NORTH){
            nextIndices.add(createNorthIndex(index));
        } else if (dir == Direction.SOUTH){
            nextIndices.add(createSouthIndex(index));
        } else if(dir == Direction.EAST){
            nextIndices.add(createEastIndex(index));
        } else if (dir == Direction.WEST){
            nextIndices.add(createWestIndex(index));
        }
        return nextIndices;
    }

    private Index createSouthIndex(Index index){
        Index southIndex = Index.copy(index);
        southIndex.setRow(index.getRow()+1);
        southIndex.setPreviousIndex(index);
        return southIndex;
    }

    private Index createNorthIndex(Index index){
        Index northIndex = Index.copy(index);
        northIndex.setRow(index.getRow()-1);
        northIndex.setPreviousIndex(index);
        return northIndex;
    }

    private Index createWestIndex(Index index){
        Index westIndex = Index.copy(index);
        westIndex.setColumn(index.getColumn()-1);
        westIndex.setPreviousIndex(index);
        return westIndex;
    }

    private Index createEastIndex(Index index){
        Index eastIndex= Index.copy(index);
        eastIndex.setColumn(index.getColumn()+1);
        eastIndex.setPreviousIndex(index);
        return eastIndex;
    }

    //Only needed when next Direction is Northeast or Northwest
    //Determines if we firstly have to move north or firstly move east/west
    private boolean isNorthIndexReachable(Index index, Button[][] field){
        return !field[index.getRow()-1][index.getColumn()].getText().equals("X");
    }

    //Only needed when Direction is Southeast or Southwest
    //Determines if we firstly have to move south or firstly move east/west
    private boolean isSouthIndexReachable(Index index, Button[][] field){
        return !field[index.getRow()+1][index.getColumn()].getText().equals("X");
    }

    private boolean canDirectionBeReached(Index nextIndex, Button[][] field, boolean[][] visited){
        return !field[nextIndex.getRow()][nextIndex.getColumn()].getText().equals("X") && !visited[nextIndex.getRow()][nextIndex.getColumn()];
    }
}
