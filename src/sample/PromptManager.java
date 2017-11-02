package sample;


import Engine.App;
import Engine.Context;
import Engine.Message;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class PromptManager implements Observer {

    private App app;
    private TextArea prompt;
    private static boolean optionThreadActive = true;
    private WriterService writerService;
    private  List<Message> messages;
    private Thread writer;
    public PromptManager(TextArea prompt, App app) {
        writerService = new WriterService();
        this.prompt = prompt;
        this.app = app;
        messages = new ArrayList<>();
        app.addObserver(this);
        prompt.setEditable(false);
        prompt.setFont(Font.font("Monospaced", 17));
       /* prompt.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
           Platform.runLater(()->{
               prompt.setScrollTop(Double.MAX_VALUE);
           });

        });
*/
        welcome();

         writer = new Thread(()->{
            while(true){

                while(messages.size()>0)
                {
                    Message msg = messages.get(0);
                    Platform.runLater(() -> {
                        if (!msg.isFromSystem()) {
                            prompt.appendText(app.getContext().getSymbole() + ":~$> ");
                        }
                    });

                    for (int i = 0; i < msg.getContent().length(); i++) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        final int x = i;
                        Platform.runLater(() -> {
                            prompt.appendText(String.valueOf(msg.getContent().charAt(x)));
                        });

                    }
                    Platform.runLater(() -> {
                        prompt.appendText("\n");
                    });

                    messages.remove(msg);
                }

                try {
                    synchronized (writer){
                        writer.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        writer.setDaemon(true);
         writer.start();
    }

    private void welcome() {

        prompt.appendText("One command to rule them all : \t\"/show cmd\"\t\t\t(c) Guillaume\n\n");
    }

    @Override
    public void update(java.util.Observable o, Object arg) {

        Message response = app.getMessage();
        if (optionThreadActive) {

           // new Writer(writerService,response, prompt, app.getContext()).start();
            messages.add(response);
            synchronized (writer){
                writer.notify();
            }

        } else {
            write(response, app.getContext());
        }
    }

    void write(Message message, Context context) {
        Platform.runLater(() -> {

            if (!message.isFromSystem()) {
                prompt.appendText(context.getSymbole() + ":~$> ");
            }
            prompt.appendText(message.getContent() + "\n");
        });

    }
}