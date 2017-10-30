package Engine;

public abstract class WebOriented_command extends Command{
    protected String url_base;
    public WebOriented_command(App app, String command) {
        super(app, command);
    }
    @Override
    protected String argsToString()
    {
        StringBuilder s = new StringBuilder();
        s.append(getUrl());
        for(String st : getArgs())
        {
            s.append(st+"+");
        }
        String search = s.toString();
        search = search.substring(0,search.length()-1);
        return search;

    }

    public String getUrl() {
        return url_base;
    }

    public void setUrl(String url_base) {
        this.url_base = url_base;
    }
}
