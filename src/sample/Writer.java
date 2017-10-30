package sample;


import Engine.Context;
import Engine.Message;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;

public class Writer extends Thread{
    private static boolean isWorking = false;
    private static List<Writer> waitingThreads = new ArrayList<>();
    private static Writer currentThread;
    private Message message;
    private static int speed =20;
    TextArea prompt;
    Context context;
    public Writer(Message message, TextArea prompt) {
        this.message = message;
        this.prompt = prompt;
        setDaemon(true);
    }
    public Writer(Message message, TextArea prompt, Context context) {
        this.message = message;
        this.prompt = prompt;
        setDaemon(true);
        this.context = context;
    }

    public synchronized static void write(TextArea prompt, Message message, Context context)
    {
        if(!message.isFromSystem())
        {
            prompt.appendText(context.getSymbole()+":~$> ");
        }
        for(int i=0; i<message.getContent().length(); i++) {
            try {
                Thread.sleep(speed);
                prompt.appendText(String.valueOf(message.getContent().charAt(i)));

            } catch (InterruptedException e) {
            }
        }
    }
    public void run(){

       write(prompt,message,context);
    }
}
