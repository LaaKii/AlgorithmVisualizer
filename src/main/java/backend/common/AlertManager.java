package backend.common;

import javafx.scene.control.Alert;

public class AlertManager {
    private String headerText;
    private String contextText;
    private String title;
    private Alert.AlertType type;

    private AlertManager(Builder builder){
        this.headerText = builder.headerText;
        this.contextText = builder.contextText;
        this.title=builder.title;
        this.type=type;
    }

    public void showAlert(){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.show();
    }


    public static class Builder{
        private String headerText;
        private String contextText;
        private String title;
        private Alert.AlertType type;

        public Builder setAlertType(Alert.AlertType type){
            this.type = type;
            return this;
        }

        public Builder setHeaderText(String headerText){
            this.headerText = headerText;
            return this;
        }

        public Builder setContextText(String contextText){
            this.contextText = contextText;
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public AlertManager build(){
            return new AlertManager(this);
        }

    }
}
