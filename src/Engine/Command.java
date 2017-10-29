package Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    protected App app;
    protected String commandName;
    protected String command;
    protected List<String> options;
    protected List<String> args;
    protected boolean stopCommand = false;

    public Command(App app, String command) {
        this.app = app;
        this.command = command;
        options = new ArrayList<>();
        args = new ArrayList<>();
        this.command = separateOptions();

        //bloc witch take care of global options and stop the process (no inherits constructors possible)
        if(options.contains("h") || options.contains("help"))
        {
            app.setMessage(DatabaseService.getCommandDescription(getCommandName()));
            stopCommand = true;
        } if(options.contains("options")||options.contains("opt"))
        {
            app.setMessage(DatabaseService.getCommandOption(getCommandName()));
            stopCommand = true;
        } if(options.contains("arg")||options.contains("args"))
        {
            app.setMessage(DatabaseService.printCommandArguments(getCommandName()));
            stopCommand = true;
        }
        //get origin command
        this.command = command;
    }

    protected String separateOptions() {


        command = deleteCommandName(command);
        try {

            Pattern p = Pattern.compile("[\\-][\\w]+");
            Matcher m = p.matcher(command);
            while (m.find()) {
                command = command.replaceFirst(m.group(), "");
                options.add(m.group().substring(1));
            }

        } catch (Exception e) {

        }
        try{

            Pattern p = Pattern.compile("[^ ]+");
            Matcher m = p.matcher(command);
            while(m.find())
            {
                command = command.replaceFirst(m.group(),"");
                args.add(m.group());
            }
        }catch(Exception e)
        {

        }
        return command;
    }

    private String deleteCommandName(String command) {
        try {
            Pattern p = Pattern.compile("[\\w\\/]+[ ]+");
            Matcher m = p.matcher(command);
            m.find();
            this.commandName = m.group();
            this.commandName = this.commandName.replaceAll(" ","");
            command = command.replaceFirst(m.group(), "");
        } catch (IllegalStateException e) {

        }
        return command;
    }


    protected String deleteExtensionFile(String filename) {
        return filename.split("\\.")[0]; // bien echapper le point
    }

    protected void sendMessage(String answer) {
        app.setMessage(answer);
    }
    protected void suggestHelp()
    {
        sendMessage("You can use "+this.getCommandName()+" -h(elp) to get more informations");
    }

    public static String indicateCommandName(String command)
    {
        command = command.toLowerCase();
        Pattern p = Pattern.compile("[^ ]+");
        Matcher m = p.matcher(command);
        m.find();
        String commandName = m.group();
        if(commandName.charAt(0)=='/')
        {
            commandName = commandName.substring(1);
            if(commandName.equalsIgnoreCase("h")|| commandName.equalsIgnoreCase("help"))
            {
                commandName = "Help";
            }
        }
        commandName = commandName.replaceFirst(String.valueOf(commandName.charAt(0)), String.valueOf(commandName.charAt(0)).toUpperCase());
    return commandName;
    }
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getArgs() {
        return args;
    }
    public String getArg(int index)
    {
        if(index >= 0 && index < this.args.size())
        {
            return this.args.get(index);
        }
        return null;
    }
    public void setArg(int index,String arg)
    {
        this.getArgs().set(index,arg);
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public String toString() {
        return  commandName + " "
              + optionsToString() +" " + argsToString();
    }
    protected String optionsToString()
    {
        StringBuilder s = new StringBuilder();
        for(String st : getOptions())
        {
            s.append("-"+st+" ");
        }
        return s.toString();
    }
    protected String argsToString()
    {
        StringBuilder s = new StringBuilder();
        for(String st : getArgs())
        {
            s.append(st+" ");
        }
        return s.toString();
    }
}
