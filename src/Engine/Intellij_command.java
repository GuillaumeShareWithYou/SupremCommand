package Engine;

public class Intellij_command extends Command {
    public Intellij_command(App app, String command) {
        super(app, command);
        if(stopCommand)return;
    try {
        ProcessBuilder ps = new ProcessBuilder("C:\\Users\\HB1\\Intellij\\bin\\idea64.exe");
        Process pr = ps.start();
        pr.waitFor();
     }catch (Exception e){sendMessage("Impossible to open Intellij");}
    }
}
