package Engine.commands;

import Engine.App;

import java.util.stream.Collectors;

public abstract class WebOriented_command extends Command{
    protected String url_base = "";
    public WebOriented_command(App app, String command) {
        super(app, command);
    }
    @Override
    protected String argsToString()
    {
        StringBuilder s = new StringBuilder(this.getUrl());
        s.append(
                getArgs().stream().collect(Collectors.joining("+"))
        );
        return s.toString();
    }

    public String getUrl() {
        return url_base;
    }

    public void setUrl(String url_base) {
        this.url_base = url_base;
    }
}
