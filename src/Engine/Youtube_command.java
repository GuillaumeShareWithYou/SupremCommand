package Engine;

public class Youtube_command extends WebOriented_command {
    final String url_base = "https://www.youtube.com/results?search_query=";
    public Youtube_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;
        String search = null;
        try{
            search = this.getArg(0);
            this.setArg(0,url_base+search);
            new Net_command(getApp(),this.toString());

        }catch(Exception e){
            this.sendMessage("failed to open");
        }
    }
}
