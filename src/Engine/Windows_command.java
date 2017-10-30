package Engine;


import java.io.*;

public class Windows_command extends Command {

    public Windows_command(App app, String command) {
        super(app, command);

        try {
            command = command.substring(1); //retire the ! witch is the condition to open a windows command
            String[]cmds = command.split("&&");
            File temp = new File("bat/temp.bat");
            PrintWriter pw = new PrintWriter(temp);
          //  pw.println("@echo off");
            for(String s : cmds)
                pw.println(s);
            pw.close();

            Process pr = Runtime.getRuntime().exec("cmd /c start "+temp.getAbsolutePath());

        } catch (IOException  e) {
           this.sendMessage("command windows failed");
        }
    }
}
