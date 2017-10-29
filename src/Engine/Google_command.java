package Engine;

public class Google_command extends WebOriented_command {
    final String url_base = "https://www.google.fr/search?q=";

    public Google_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        String search = null;
        try{
            search = this.getArg(0);
           this.setArg(0,url_base+search);
            new Net_command(getApp(),this.toString());

        }catch(Exception e){
            this.sendMessage(" failed to open");
        }
    }
}
