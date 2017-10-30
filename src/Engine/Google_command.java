package Engine;

public class Google_command extends WebOriented_command {

    public Google_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        setUrl("https://www.google.fr/search?q=");
        String search = null;
        try{
            search = this.getArg(0);
            new Net_command(getApp(),this.toString());

        }catch(Exception e){
            this.sendMessage(" failed to open");
        }
    }
}
