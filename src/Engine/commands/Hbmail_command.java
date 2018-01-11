package Engine.commands;

import Engine.App;

public class Hbmail_command extends WebOriented_command {

    public Hbmail_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;
        setUrl("https://mail.ovh.net/roundcube/?_user=gmathieu@humanbooster.com");
        new Net_command(getApp(),this.toString());
    }

    @Override
    protected String argsToString(){
           return this.url_base;
    }
}
