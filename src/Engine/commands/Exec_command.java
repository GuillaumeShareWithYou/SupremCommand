package Engine.commands;



import Engine.App;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Exec_command extends Command {

    public Exec_command(App app, String command) {
        super(app, command);
        if (stopCommand) return;

        new Thread(()->{
            execute(this,command);
        }).start();
    }

    public static synchronized void execute(Exec_command exe, String command) {

        try {

            String filename = exe.getArg(0);
            filename = exe.deleteExtensionFile(filename);

            String f_filename = filename;
                try {

                    ProcessBuilder ps = new ProcessBuilder("cpp\\" + f_filename);
                    for (int i = 1; i < exe.getArgs().size(); i++) {
                        ps.command().add(exe.getArg(i));
                    }

                    ps.redirectErrorStream(true);
                    Process pr = ps.start();
                    BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                    String line;

                    StringBuilder strb = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        exe.sendMessage(line);
                        strb.append(line + "\n");
                    }
                    // sendMessage(strb.toString());
                    pr.waitFor();
                    in.close();
                } catch (Exception e) {
                }

        } catch (Exception e) {
            exe.sendMessage("File not found");
        }
    }
}



