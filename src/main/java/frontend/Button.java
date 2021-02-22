package frontend;

public class Button extends javafx.scene.control.Button {

    private boolean visited = false;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void switchField(String field){
        if (field.equals("0")){
            super.setText("X");
            super.setStyle("-fx-background-color: #99bfa3");
        } else if(field.equals("X")){
            super.setText("0");
            super.setStyle("");
        }
    }




    public Button(String text){
        super.setText(text);
    }

    public boolean isStartField() {
        return getText().equals("S");
    }
}
