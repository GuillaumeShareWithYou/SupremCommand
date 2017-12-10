package Engine.commands;

import Engine.App;

public class Mysql_command extends Command {
    public Mysql_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        new Windows_command(getApp(),"!C:\\wamp64\\bin\\mysql\\mysql5.7.19\\bin\\mysql.exe -u root");
        sendMessage("C:\\wamp64\\bin\\mysql\\mysql5.7.19\\bin\\mysql.exe -u root");
    }
}
