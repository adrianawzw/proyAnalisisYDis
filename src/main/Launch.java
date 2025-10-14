package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.MetaData;

public class Launch extends Application{

    public static Stage stage = null;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
        this.stage = stage;
        MetaData.parent = root;
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
