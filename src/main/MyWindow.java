package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyWindow extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("New window creating now...");
        stage.setTitle("First window");
        stage.setWidth(400);
        stage.setHeight(500);

        //https://hiphop.de/sites/default/files/styles/hiphopde_800x450/public/lx_facebook_screen_800_2015.jpg?itok=VuaB9ZMt

        VBox parent = new VBox();
        Scene scene1 = new Scene(parent);

        Image image = new Image("https://hiphop.de/sites/default/files/styles/hiphopde_800x450/public/lx_facebook_screen_800_2015.jpg?itok=VuaB9ZMt");
        ImageView imageView = new ImageView(image);
        parent.getChildren().add(imageView);
        stage.setScene(scene1);




        stage.show();


    }
}
