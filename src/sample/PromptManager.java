package sample;


import Engine.App;
import Engine.Context;
import Engine.Message;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Observer;

public class PromptManager implements Observer {

    private App app;
    private TextArea prompt;
    private static boolean optionThreadActive = false;

    public PromptManager(TextArea prompt, App app) {
        this.prompt = prompt;
        this.app = app;
        app.addObserver(this);
        prompt.setEditable(false);
        prompt.setFont(Font.font("Monospaced", 17));
        prompt.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
            prompt.setScrollTop(Double.MAX_VALUE);

        });

        welcome();
    }

    private void welcome() {

        prompt.appendText("One command to rule them all : \t\"/show cmd\"\t\t\t(c) Guillaume\n\n");
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        Message response = app.getMessage();
        Platform.runLater(() -> {
            if (optionThreadActive) {
                new Writer(response, prompt, app.getContext()).start();

            } else {
                write(response, app.getContext());
            }
        });

    }

    void write(Message message, Context context) {
     if(!message.isFromSystem())
     {
         prompt.appendText(context.getSymbole() + ":~$> ");
     }
        prompt.appendText(message.getContent()+"\n");
    }
}
