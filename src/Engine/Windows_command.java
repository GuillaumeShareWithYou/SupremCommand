package Engine;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Windows_command extends Command {

    public Windows_command(App app, String command) {
        super(app, command);
        boolean here = command.startsWith("!!");
        try {
            command = command.substring(1); //retire the ! witch is the condition to open a windows command
          if(here) command = command.replaceFirst("!","");
            String[]cmds = command.split("&&");

            File temp = new File("bat/temp.bat");
            PrintWriter pw = new PrintWriter(temp);
          //  pw.println("@echo off");
            for(String s : cmds)
                pw.println(s);
            pw.close();
            System.out.println(this);
        if(here)
        {
            final List<String> commands = new ArrayList<String>();
            ProcessBuilder ps = new ProcessBuilder(commands);
            ps.command().add(temp.getAbsolutePath());
            ps.redirectErrorStream(true);
            Process pr = ps.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                this.sendMessage(line);
            }
            in.close();
        }else{
            Process pr = Runtime.getRuntime().exec("cmd /c start "+temp.getAbsolutePath());
        }

        } catch (IOException  e) {
           e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
