package frontend;

public class Button extends javafx.scene.control.Button {

    private boolean isFieldWall = false;


    public void switchField(String field){
        if (field.equals("X")){
            super.setText("0");
        } else if(field.equals("0")){
            super.setText("X");
        }

    }

    public Button(String text){
        super.setText(text);
    }
}
