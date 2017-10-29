package Engine;

public abstract class WebOriented_command extends Command{
    public WebOriented_command(App app, String command) {
        super(app, command);
    }
    @Override
    protected String argsToString()
    {
        StringBuilder s = new StringBuilder();

        for(String st : getArgs())
        {
            s.append(st+"+");
        }
        String search = s.toString();
        search = search.substring(0,search.length()-1);
        return search;

    }
}
