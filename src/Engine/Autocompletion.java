package Engine;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Autocompletion {
    private App app;
    private Command command;
    private CycleList cycleList;
    private String lastKeyword;
    private String previousLastKeyword;
    private TypeOfWord type;

    public Autocompletion(App app) {
        this.app = app;
        lastKeyword = new String();
        previousLastKeyword = new String();
    }

    public void seek(String text)  {

        Pattern pattern = Pattern.compile("[^ ]*$");
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        lastKeyword = matcher.group();
        if (lastKeyword.equals(previousLastKeyword))
        {
            previousLastKeyword = cycleList.getCurrent();
            return;
        }
        command = new Command(app, text);

        if (lastKeyword.length() == 0) {

            cycleList = new CycleList();
            previousLastKeyword = lastKeyword;
        } else if (command.getOptions().size() == 0 && command.getArgs().size() == 0) {
            //need to complete name of command
            type = TypeOfWord.NAME;
            List<String> res = DatabaseService.getAllCommandsBeginWith(app.getContext(),command.getCommandName());
            cycleList = new CycleList(res);
            previousLastKeyword = cycleList.getCurrent();
            command.removeCommandName();
        } else if (lastKeyword.startsWith("-")) {
            type = TypeOfWord.OPT;
            lastKeyword = lastKeyword.replaceFirst("-{1,2}", "");
            cycleList = new CycleList(DatabaseService.getAllOptionsBeginWith(app.getContext(),command.getCommandName(), lastKeyword));
            command.removeOption(lastKeyword);
            previousLastKeyword = cycleList.getCurrent();
        } else {
            type = TypeOfWord.ARG;
            cycleList = new CycleList(DatabaseService.getAllArgumentsBeginWith(app.getContext(),command.getCommandName(), lastKeyword));
            command.removeArg(lastKeyword);
            previousLastKeyword = cycleList.getCurrent();
        }
    }

    public String getNext()
    {
        StringBuilder str =  new StringBuilder(command.toString());
        if(type!=TypeOfWord.NAME)
            str.append(" ");
        if(type==TypeOfWord.OPT)
            str.append("-");
        String variable = cycleList.getNext();
        if(variable!=null)
        {
            str.append(variable);
        }
        return str.toString();
    }
    public String getPrevious()
    {
        StringBuilder str =  new StringBuilder(command.toString());
        if(type!=TypeOfWord.NAME)
            str.append(" ");
        if(type==TypeOfWord.OPT)
            str.append("-");
        str.append(cycleList.getPrevious());
        return str.toString();
    }
}

enum TypeOfWord{
    NAME, ARG, OPT;

}
