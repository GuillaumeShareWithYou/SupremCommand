package Engine.commands;

import Engine.App;
import Engine.db.DatabaseService;

public class Show_command extends Command{
    public Show_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        if(this.getArgs().contains("context"))
        {
            app.setMessage("Context : "+app.getContext().getName());
        }
        if(this.getArgs().contains("cmd"))
        {
            app.setMessage(DatabaseService.printAllCommands());
        }
    }


}
