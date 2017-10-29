package Engine;

public class Exit_command extends Command{

    public Exit_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;
        System.exit(0);
    }
}
