package sample;


import Engine.Context;
import Engine.Message;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class Writer extends Thread {
    private static List<Writer> threads;

    private Message message;
    private static int pauseMillisec = 3;
    private TextArea prompt;
    private Context context;
    private WriterService service;

    public Writer(WriterService semaphore, Message message, TextArea prompt) {
        threads = new ArrayList<>();
        this.message = message;
        this.prompt = prompt;
        this.service = semaphore;
        setDaemon(true);
    }

    public Writer(WriterService semaphore, Message message, TextArea prompt, Context context) {
        this(semaphore,message, prompt);
        this.context = context;
    }

    public synchronized static void write (TextArea prompt, Message message, Context context) {


            Platform.runLater(() -> {
                if (!message.isFromSystem()) {
                    prompt.appendText(context.getSymbole() + ":~$> ");
                }
            });

            for (int i = 0; i < message.getContent().length(); i++) {
                try {
                    Thread.sleep(pauseMillisec);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int x = i;
                Platform.runLater(() -> {
                    prompt.appendText(String.valueOf(message.getContent().charAt(x)));
                });

            }
            Platform.runLater(() -> {
                prompt.appendText("\n");
            });

    }

    public void run() {

       System.out.println(this.getName()+" entre dans run");
        service.add(this); //6
       System.out.println(this.getName()+" va ecrire");
        write(prompt, message, context);
        System.out.println(this.getName()+" a ecrit");
        service.remove(this); //5
        System.out.println(this.getName()+" sort");
    }
}

