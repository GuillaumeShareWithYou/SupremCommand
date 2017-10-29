package Engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Exec_command extends Command{

    public Exec_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;
        try{

            String filename = this.getArg(0);
            filename = deleteExtensionFile(filename);


            ProcessBuilder ps=new ProcessBuilder("cpp\\"+filename);
            for(int i=1; i<getArgs().size();i++){
                ps.command().add(getArg(i));
            };
            ps.redirectErrorStream(true);
            Process pr = ps.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;

            StringBuilder strb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                strb.append(line+"\n");
            }
            sendMessage(strb.toString());
            pr.waitFor();
            in.close();
        }catch (Exception e)
        {
            this.sendMessage("File not found");

        }
    }

}
