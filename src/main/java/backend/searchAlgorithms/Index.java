package backend.searchAlgorithms;

public class Index {

    private int row;
    private int column;
    private Index previousIndex;

    public Index(int row, int column) {
        this.row=row;
        this.column=column;
    }

    public void setPreviousIndex(Index previous){
        this.previousIndex=previous;
    }

    public Index getPreviousIndex(){
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

    public static Index copy(Index index){
        Index ind = new Index(index.getRow(), index.getColumn());
        return ind;
    }
}
