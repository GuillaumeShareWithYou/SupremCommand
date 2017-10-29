package sample;


import Engine.App;
import Engine.Context;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

import java.util.Observer;

public class PromptManager implements Observer {

    private App app;
    private TextArea prompt;
    private static boolean optionThreadActice = false;

    public PromptManager(TextArea prompt, App app) {
        this.prompt = prompt;
        this.app = app;
        app.addObserver(this);
        prompt.setEditable(false);
        prompt.setFont(Font.font("Monospaced",16));
    }


    @Override
    public void update(java.util.Observable o, Object arg) {
        String response = app.getMessage() + "\n";
        Platform.runLater(()->{
            if(optionThreadActice)
            {
                new Writer(response, prompt, app.getContext()).start();

            }else{
                    write(response, app.getContext());
            }
        });

    }
    void write(String message, Context context){
        prompt.appendText(context.getSymbole()+":~$> ");
        prompt.appendText(message);
    }
}
