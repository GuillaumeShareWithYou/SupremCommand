package sample;

import Engine.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View extends Application {

    private TextArea prompt;
    private TextField input;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.getStylesheets().add("sample/css/style.css");
        primaryStage.setTitle("E-Corp");
        primaryStage.setScene(new Scene(root,950,490));

        prompt = (TextArea) root.getScene().lookup("#prompt");
        input =  (TextField) root.getScene().lookup("#input");


        prompt.appendText("\n\n\t\t\t\t\tE-corp Command Line\n\n".toUpperCase());

        App app = new App();
        new Controller(this,app);
        primaryStage.setResizable(true);

        primaryStage.show();
        primaryStage.setMinWidth(300);
        primaryStage.setMaxWidth(1000);
        prompt.setPadding(new Insets(0,0,5,20));

    }

    public TextArea getPrompt() {
        return prompt;
    }



    public TextField getInput() {
        return input;
    }


}
