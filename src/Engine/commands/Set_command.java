package Engine.commands;

import Engine.App;

public class Set_command extends Command{
    public Set_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        if(this.getOptions().contains("default") || this.getOptions().contains("reset"))
        {
            app.getConfig().setDefault();
            sendMessage("configs are now by default");
            return;
        }

        if(this.getArg(0)!=null && this.getArg(1)!=null)
        {
            String variableToSet = this.getArg(0);
            if(variableToSet.equals("symbole"))
            {
                app.getContext().setSymbole(this.getArg(1));
                sendMessage("Symbole changed.");
            }else if(variableToSet.equals("browser"))
            {
                app.getConfig().setBrowser(this.getArg(1));
                sendMessage("Browser changed.");
            }else if(variableToSet.equals("color"))
            {
                app.getConfig().setColor(this.getArg(1));
            }
            else{
                sendMessage("this variable can't be set. check it with set --arg ");
            }
        }else{
            sendMessage("Missing variable or argument.");
        }
    }
}
