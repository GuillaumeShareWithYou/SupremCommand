package Engine.commands;

import Engine.App;

import java.io.IOException;

public class Dropbox_command extends Command {
    public Dropbox_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        try {
            ProcessBuilder ps=new ProcessBuilder("\\C:\\Windows\\explorer.exe");
            ps.command().add("C:\\Users\\HB1\\Dropbox\\CDI  Session 10 - Septembre 2017");
            Process pr = ps.start();
           pr.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
