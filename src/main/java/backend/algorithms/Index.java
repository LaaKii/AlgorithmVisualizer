package backend.algorithms;

public class Index {

    private int row;
    private int column;

    public Index(int row, int column) {
        this.row=row;
        this.column=column;
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

    public static Index copy(Index index){
        Index ind = new Index(index.getRow(), index.getColumn());
        return ind;
    }
}
