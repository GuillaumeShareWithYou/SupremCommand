package Engine;

public class Show_command extends Command{
    public Show_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        if(this.getArgs().contains("context"))
        {
            app.setMessage("Context : "+app.getContext().getName());
        }else{
            printHelp();
        }
    }


    private void printHelp() {
        if (this.getArgs().contains("cmd")) {
            app.setMessage(DatabaseService.printAllCommands(app.getContext()));
        } else if (this.getArg(0) != null && this.getOptions().size() == 0) {
            System.out.println("commande sans option");
            app.setMessage(DatabaseService.getCommandDescription(this.getArg(0)));
        } else if (this.getArg(0) != null && this.getOptions().contains("o")) {
            System.out.println("commande et option");
            app.setMessage(DatabaseService.getCommandOption(this.getArg(0)));
        }else{
            app.setMessage("Need help ? /show cmd or /show command_name [-o]");
        }
    }

}
