package backend.searchAlgorithms;

public class Index {

    private int row;
    private int column;
    private Index previousIndex;
    private int manhattanDistance;

    public Index(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Index() {
    }

    public Index(int row, int column, Index previousIndex) {
        this.row = row;
        this.column = column;
        this.previousIndex = previousIndex;
    }

    public Index(int row, int column, Index previousIndex, int manhattanDistance) {
        this.row = row;
        this.column = column;
        this.previousIndex = previousIndex;
        this.manhattanDistance = manhattanDistance;
    }

    public void setPreviousIndex(Index previous) {
        this.previousIndex = previous;
    }

    public Index getPreviousIndex() {
        return previousIndex;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }

    public static Index copy(Index index) {
        Index ind = new Index(index.getRow(), index.getColumn(), index.getPreviousIndex());
        return ind;
    }

    public void setManhattanDistance(int distance) {
        this.manhattanDistance = distance;
    }

    public boolean isSameField(Index indexToCheckForSameField) {
        return this.getColumn() == indexToCheckForSameField.getColumn() && this.getRow() == indexToCheckForSameField.getRow();
    }

    @Override
    public String toString() {
        return "Index{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
