package Engine.commands;

import Engine.App;

public class Youtube_command extends WebOriented_command {

    public Youtube_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;
        setUrl("https://www.youtube.com/results?search_query=");
        String search = null;
        try{
            search = this.getArg(0);
            new Net_command(getApp(),this.toString());

        }catch(Exception e){
            this.sendMessage("failed to open");
        }
    }
}
