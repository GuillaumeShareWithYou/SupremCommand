package Engine.autocomplete;

import Engine.App;
import Engine.tools.CycleList;
import Engine.commands.Command;
import Engine.db.DatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Autocompletion {
    private App app;
    private String commandName;
    private List<String> options;
    private List<String> args;
    private CycleList cycleList;
    private String lastKeyword;
    private String previousLastKeyword;
    private TypeOfWord type;

    public Autocompletion(App app) {
        this.app = app;
        lastKeyword = new String();
        previousLastKeyword = new String();
        options = new ArrayList<>();
        args = new ArrayList<>();
    }

    public void seek(String text)  {

        Pattern pattern = Pattern.compile("[\\S]+$");
        Matcher matcher = pattern.matcher(text);
        if(!matcher.find()) return;
        lastKeyword = matcher.group();
        if (lastKeyword.equals(previousLastKeyword))
        {
            previousLastKeyword = cycleList.getCurrent();
            return;
        }
        commandName = Command.getCommandName(text);
        options = Command.getOptions(text);
        args = Command.getArguments(text);

        if (lastKeyword.length() == 0) {
            cycleList = new CycleList();
            previousLastKeyword = lastKeyword;
        } else if (options.size() == 0 && args.size() == 0) {
            //need to complete name of command
            type = TypeOfWord.NAME;
            List<String> res = DatabaseService.getAllCommandsBeginWith(commandName);
            cycleList = new CycleList(res);
            previousLastKeyword = cycleList.getCurrent();
            //Remove command name
            commandName = "";
        } else if (lastKeyword.startsWith("-")) {
            type = TypeOfWord.OPT;
            lastKeyword = lastKeyword.replaceFirst("-+", "");
            cycleList = new CycleList(DatabaseService.getAllOptionsBeginWith(commandName, lastKeyword));
            //Remove option
            options.remove(lastKeyword);
            previousLastKeyword = cycleList.getCurrent();
        } else {
            type = TypeOfWord.ARG;
            cycleList = new CycleList(DatabaseService.getAllArgumentsBeginWith(commandName, lastKeyword));
            //Remove argument
            args.remove(lastKeyword);
            previousLastKeyword = cycleList.getCurrent();
        }
    }

    public String getNext()
    {
        StringBuilder str =  new StringBuilder(getCommandString());
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
        StringBuilder str =  new StringBuilder(getCommandString());
        if(type==TypeOfWord.OPT)
            str.append("-");
        str.append(cycleList.getPrevious());
        return str.toString();
    }

    public String getCommandString(){

         return Stream.of(commandName, optionsToString(), argsToString())
                .filter(e->e.length()>0)
                .collect(Collectors.joining(" "));
    }
    private String optionsToString() {
        return options.stream().map(s->"-"+s).collect(Collectors.joining(" "));
    }
    private String argsToString() {
        return args.stream().collect(Collectors.joining(" "));
    }
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public CycleList getCycleList() {
        return cycleList;
    }

    public void setCycleList(CycleList cycleList) {
        this.cycleList = cycleList;
    }

    public String getLastKeyword() {
        return lastKeyword;
    }

    public void setLastKeyword(String lastKeyword) {
        this.lastKeyword = lastKeyword;
    }

    public String getPreviousLastKeyword() {
        return previousLastKeyword;
    }

    public void setPreviousLastKeyword(String previousLastKeyword) {
        this.previousLastKeyword = previousLastKeyword;
    }

    public TypeOfWord getType() {
        return type;
    }

    public void setType(TypeOfWord type) {
        this.type = type;
    }
}

enum TypeOfWord{
    NAME, ARG, OPT
}
