package sample;


import Engine.App;
import Engine.tools.Color;
import Engine.tools.Context;
import Engine.tools.Message;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import sample.tools.WriterService;

import java.util.*;

public class PromptManager implements Observer {

    private App app;
    private Color color;
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
        prompt.setFont(Font.font("monospace", 12));
        prompt.setStyle("-fx-text-fill: "+Color.LIGHTBLUE.getColor());
        welcome();
        startWriter();
    }

    private void startWriter() {
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
                            Thread.sleep(0);
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

        String msg = chooseMsg();
        prompt.appendText("\t\t"+msg+"\n\n");
    }

    private String chooseMsg() {
        List<String> messages = new ArrayList<>();
        messages.add("On marche capuché pour pas voir que le ciel nous tombe sur la gueule.");
        messages.add("Chez moi ça fait la passe à dix, mais c'est la cess qui a remplacé le ballon.");
        messages.add("La plus amère des vérités vaut mieux que le plus doux des mensonges.");
        int i = new Random().nextInt(messages.size());

        return messages.get(i);
    }


    public void update(Observable o, Object arg) {
    setColor(((App)o).getConfig().getColor());

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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {

        this.color = color;
        prompt.setStyle("-fx-text-fill: "+getColor().getColor());
    }

    public TextArea getPrompt() {
        return prompt;
    }

    public void setPrompt(TextArea prompt) {
        this.prompt = prompt;
    }
}