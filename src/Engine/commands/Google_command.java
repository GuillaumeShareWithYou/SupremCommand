package Engine.commands;

import Engine.App;

public class Google_command extends WebOriented_command {

    public Google_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        setUrl("https://www.google.fr/search?q=");
        try{
            new Net_command(getApp(),this.toString());

        }catch(Exception e){
            this.sendMessage(" failed to open");
        }
    }
}
