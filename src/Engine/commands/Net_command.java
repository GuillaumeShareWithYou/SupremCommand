package Engine.commands;


import Engine.App;

public class Net_command extends Command{
    private String browser;
    private String url;
    public Net_command(App app, String command) {
        super(app, command);
        browser = app.getConfig().getBrowser();
        if(stopCommand) return;

        browser = app.getConfig().getBrowser();
        System.out.println(this);
        try{
            url = this.argsToString();
            if(this.getOptions().contains("firefox"))
            {
                browser = "firefox";
            }else if(this.getOptions().contains("chrome"))
            {
                browser = "chrome";
            }
            Process p = Runtime.getRuntime().exec("cmd /c start "+ browser+" "+url);
        }catch (Exception e){
            app.setMessage("please verify your url");

        }

    }
}
