package Engine;

public class Set_command extends Command{
    public Set_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        if(this.getArg(0)!=null)
        {
            if(this.getArg(0).equals("symbole"))
            {
                app.getContext().setSymbole(this.getArg(1));
                sendMessage("As you like.");
            }
        }
    }
}
