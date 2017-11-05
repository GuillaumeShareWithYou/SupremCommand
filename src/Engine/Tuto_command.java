package Engine;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tuto_command extends Command {
    public Tuto_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        displayTutorial();
    }
    private void displayTutorial(){
        try{

            Scanner sc = new Scanner(new File("files/tutorial.txt"));

            while(sc.hasNext()){
                sendMessage(sc.nextLine());
            }

        }catch (IOException e){}


    }
}
