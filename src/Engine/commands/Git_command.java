package Engine.commands;

import Engine.App;

public class Git_command extends Command {
    public Git_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        if(this.getOptions().contains("commit") && this.getArgs().size()>0)
        {
            String gitCommand = "!git add * && git commit -m \""+this.argsToString()+"\" && git push origin master";
            new Windows_command(getApp(), gitCommand);
        }
    }
}
