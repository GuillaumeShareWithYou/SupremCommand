package sample;


import Engine.App;
import Engine.Context;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

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
        prompt.setFont(Font.font("Monospaced", 16));
        prompt.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
            prompt.setScrollTop(Double.MAX_VALUE);

        });

        welcome();
    }

    private void welcome() {
        prompt.appendText("\n\n\t\t\t\t\tE-corp Command Line\n\n".toUpperCase());
        prompt.appendText("\nStart the app by typing:\t\"ecp app --init\"");
        prompt.appendText("\nDisplay all the available commands with:\t\"show cmd\"\n");
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        String response = app.getMessage() + "\n";
        Platform.runLater(() -> {
            if (optionThreadActive) {
                new Writer(response, prompt, app.getContext()).start();

            } else {
                write(response, app.getContext());
            }
        });

    }

    void write(String message, Context context) {
        prompt.appendText(context.getSymbole() + ":~$> ");
        prompt.appendText(message);
    }
}
