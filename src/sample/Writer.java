package sample;


import Engine.Context;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Writer extends Thread{
    private static boolean isWorking = false;
    private static List<Writer> waitingThreads = new ArrayList<>();
    private static Writer currentThread;
    private String message;
    private static int speed =20;
    TextArea prompt;
    Context context;
    public Writer(String message, TextArea prompt) {
        this.message = message;
        this.prompt = prompt;
        setDaemon(true);
    }
    public Writer(String message, TextArea prompt, Context context) {
        this.message = message;
        this.prompt = prompt;
        setDaemon(true);
        this.context = context;
    }

    public synchronized static void write(TextArea prompt, String message, Context context)
    {
        prompt.appendText(context.getSymbole()+":~$> ");
        for(int i=0; i<message.length(); i++) {
            try {
                Thread.sleep(speed);
                prompt.appendText(String.valueOf(message.charAt(i)));

            } catch (InterruptedException e) {
            }
        }
    }
    public void run(){

       write(prompt,message,context);
    }
}
