package Engine;

public class Ecp_command extends Command {
    private static boolean started = false;

    public Ecp_command(App app, String command) {
        super(app, command);
        if (stopCommand) return;

        if (!started) {
            started = init();
        }else{

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



    }

    private boolean init() {
     try {

         if (this.getArg(0).equals("app")) {

             if (this.getOptions().contains("init")) {
                 if (this.getOptions().contains("dev")) {
                     app.setContext(Context.STANDARD);
                     if(!this.getOptions().contains("discreet"))
                     {
                         printWelcomeMsg();
                     }
                 } else {
                     app.setContext(Context.INIT);

                 }
             } else {
                 sendMessage("Do you mean ecp app -init ?");
             }
         }else{
             sendMessage("You can only init the app for now.");
         }
         return (app.getContext().compareTo(Context.SLEEP) > 0);
     }catch (Exception e)
     {
         return false;
     }
    }
    public void printWelcomeMsg() {
        new Fread_command(getApp(), "fread files/welcome.txt");
    }
    public static boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        Ecp_command.started = started;
    }
}
