package Engine.autocomplete;

import Engine.App;
import Engine.tools.CycleList;
import Engine.commands.Command;
import Engine.db.DatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        Pattern pattern = Pattern.compile("[\\S]+$");
        Matcher matcher = pattern.matcher(text);
        if(!matcher.find()) return;
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
            List<String> res = DatabaseService.getAllCommandsBeginWith(command.getCommandName());
            cycleList = new CycleList(res);
            previousLastKeyword = cycleList.getCurrent();
            command.removeCommandName();
        } else if (lastKeyword.startsWith("-")) {
            type = TypeOfWord.OPT;
            lastKeyword = lastKeyword.replaceFirst("-+", "");
            cycleList = new CycleList(DatabaseService.getAllOptionsBeginWith(command.getCommandName(), lastKeyword));
            command.removeOption(lastKeyword);
            previousLastKeyword = cycleList.getCurrent();
        } else {
            type = TypeOfWord.ARG;
            cycleList = new CycleList(DatabaseService.getAllArgumentsBeginWith(command.getCommandName(), lastKeyword));
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
        if(type==TypeOfWord.OPT)
            str.append("-");
        str.append(cycleList.getPrevious());
        return str.toString();
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
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
