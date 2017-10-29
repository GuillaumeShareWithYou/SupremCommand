package Engine;


public class Net_command extends Command{
    private String browser ="chrome";
    private String url;
    public Net_command(App app, String command) {
        super(app, command);
        if(stopCommand) return;

        try{
            url = this.getArg(0);
            if(this.getOptions().contains("firefox"))
            {
                browser = "firefox";
            }
            Process p = Runtime.getRuntime().exec("cmd /c start "+ browser+" "+url);
        }catch (Exception e){
            app.setMessage("please verify your url");
        }

    }
}
