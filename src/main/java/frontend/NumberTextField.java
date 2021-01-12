package frontend;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

    public NumberTextField(String text){
        super(text);
    }

    public int getAmount(){
        return Integer.parseInt(getText());
    }

    @Override
    public void replaceText(int i, int i1, String s) {
        if (isTextNumericOnly(s)) {
            super.replaceText(i,i1,s);
            setStyle("");
        } else {
            setStyle("-fx-control-inner-background: #a81830");
        }
    }

    @Override
    public void replaceSelection(String s) {
        if (isTextNumericOnly(s)) {
            super.replaceSelection(s);
            setStyle("");
        } else {
            setStyle("-fx-control-inner-background: #a81830");
        }
    }

    private boolean isTextNumericOnly(String inputText) {
        return inputText.matches("\\d+");
    }


}
