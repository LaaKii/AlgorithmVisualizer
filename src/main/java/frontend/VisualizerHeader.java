package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class VisualizerHeader {

    public Node getHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(15,12,15,12));
        header.setAlignment(Pos.CENTER);
        Label headerLabel = new Label("Algorithm Visualizer");
        headerLabel.setAlignment(Pos.CENTER);
        header.getChildren().add(headerLabel);
        return header;
    }
}
