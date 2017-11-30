package Engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Play_command extends Command {

   static Map<String,String> gameToUrl = new HashMap<>();
   static {
       gameToUrl.put("chess","bat/run_chess.bat");
   }
    public Play_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;
        try{
            command = command.substring(4);
            command = command.replaceAll("^ ","");
            Process p = Runtime.getRuntime().exec(gameToUrl.get(command));

            this.sendMessage("game started correctly");
        }catch(IOException |NullPointerException e)
        {
            this.sendMessage("This game doesn't exist");
            suggestHelp();
        }
    }

}
