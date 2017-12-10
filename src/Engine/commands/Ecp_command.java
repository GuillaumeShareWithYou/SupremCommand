package Engine.commands;

import Engine.App;
import Engine.tools.Context;

import java.util.Random;

public class Ecp_command extends Command {
    private static boolean started = false;

    public Ecp_command(App app, String command) {
        super(app, command);
        if (stopCommand) return;

        if (!started) {
            started = init();
            return;
        }

            if(this.getArg(0)!=null && this.getArg(0).equals("upgrade"))
            {
                if(app.getContext().compareTo(Context.MANAGER)<0)
                {
                    app.setContext(Context.MANAGER);
                }else{
                    sendMessage("You are not a standard person for quite a while");
                }
            }else{
                    suggestHelp();
            }





    }

    private boolean init() {
     try {
         boolean init = false;

         if (this.getArg(0).equals("app")) {

             if (this.getOptions().contains("init")) {

                 if (this.getOptions().contains("dev")) {
                     app.setContext(Context.STANDARD);
                     if(!this.getOptions().contains("discreet"))
                     {
                         printWelcomeMsg();
                     }
                     init = true;
                 } else {
                     app.setContext(Context.INIT);
                 }
             } else {
                 sendMessage("Do you mean ecp app -init ?");
             }
         }else{
             sendMessage("You can only init the app for now.");
         }
         return init;
     }catch (Exception e)
     {
         return false;
     }
    }

    public  void printWelcomeMsg() {
        String suff = String.valueOf(new Random().nextInt(3)+1);

        new Fread_command(getApp(), "fread files/manon".concat(suff).concat(".txt"));
    }
    public static boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        Ecp_command.started = started;
    }
}
