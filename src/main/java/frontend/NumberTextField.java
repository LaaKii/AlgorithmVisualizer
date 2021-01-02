package frontend;

import javafx.scene.control.Alert;

import java.awt.*;

public class NumberTextField extends TextField {

    @Override
    public void setText(String inputText) {
        if (isTextNumericOnly(inputText)) {
            Color background = getBackground();
            super.setText(inputText);
        } else {
            setBackground(Color.RED);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Wrong value in Field");
            String s = "Only numeric values are allowed in this field";
            alert.setContentText(s);
            alert.show();
        }
    }

    private boolean isTextNumericOnly(String inputText) {
        return inputText.matches("\\d+");
    }


}
