package sample;

import Engine.App;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class View extends Application {

    private TextArea prompt;
    private TextField input;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        root.getStylesheets().add("sample/css/style.css");
        primaryStage.setTitle("SupCommand.py");
        primaryStage.setScene(new Scene(root,1180,650));

        prompt = (TextArea) root.getScene().lookup("#prompt");
        input =  (TextField) root.getScene().lookup("#input");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view.fxml"));
        MenuController menuController = loader.getController();

        App app = new App();
        new Controller(this,app);
        primaryStage.setResizable(true);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.getIcons().setAll(new Image("file:../../ressources/command.png"));
        primaryStage.show();
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
    }


    public TextArea getPrompt() {
        return prompt;
    }



    public TextField getInput() {
        return input;
    }


}
