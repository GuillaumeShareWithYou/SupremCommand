package Engine.commands;

import Engine.App;
import Engine.db.DatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Command {
    protected App app;
    protected String commandName;
    protected String command;
    protected List<String> options;
    protected List<String> args;
    protected boolean stopCommand = false;

    public Command(App app, String _command) {
        this.app = app;
        this.command = _command;
        options = new ArrayList<>();
        args = new ArrayList<>();

        analyseCommand(_command);
        //bloc witch take care of global options and stop the process (no inherits constructors possible)
        if (options.contains("h") || options.contains("help")) {
            String info = DatabaseService.getCommandDescription(getCommandName()) + "\n";
            info = info.concat(DatabaseService.printCommandArguments(getCommandName()) + "\n");
            info = info.concat(DatabaseService.printCommandOption(getCommandName()));
            sendMessage(info);
            stopCommand();
        }
        if (options.contains("info")) {
            sendMessage(DatabaseService.getCommandDescription(getCommandName()));
            stopCommand();
        }
        if (options.contains("options") || options.contains("opt") || options.contains("option")) {
            sendMessage(DatabaseService.printCommandOption(getCommandName()));
            stopCommand();
        }
        if (options.contains("arg") || options.contains("args")) {
            sendMessage(DatabaseService.printCommandArguments(getCommandName()));
            stopCommand();
        }
    }

    public static String getCommandName(String command){
        String commandName =  command.trim().split(" ")[0].replaceFirst("^/","");
       return String.valueOf(commandName.charAt(0)).toUpperCase()
                + commandName.substring(1).toLowerCase();
    }


    public static List<String> getOptions(String command){
        /*looking for options in the command
          An option begin with '-' and doesn't contains numbers so i apply a
        positive look backward for '-' before word */
        List<String> options = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=\\s-{1,2})\\b[a-zA-Z]+\\b");
        Matcher matcher = pattern.matcher(command);
        while(matcher.find()){
            options.add(matcher.group());
        }
        return options;
    }
    protected void analyseCommand(String command){

        this.commandName = getCommandName(command);
        this.options = getOptions(command);
        this.args = getArguments(command);
    }

    public static List<String> getArguments(String command) {
        List<String> args = new ArrayList<>();
          /*
                looking for args in the command
                negative look backward for '-' before every word with letters
                negative look ahead for '-' after symboles and digits,

                to be able to use calculations with calc_command
                with + - * / ( ) . %
                So options can't contain digit later but it's ok.
         */
        //
        Pattern pattern = Pattern.compile("(?<!-)\\b[\\w./*+:?=@%\\\\-]+|[\\d+*/()-.%]+(?![a-zA-Z-])");
        Matcher matcher = pattern.matcher(command);
        matcher.find(); // remove command name
        while(matcher.find()){
            args.add(matcher.group());
        }
        return args;
    }


    public void removeCommandName()
    {
        this.commandName = "";
    }
    public void removeOption(String option)
    {
        this.getOptions().remove(option);
    }
    public void removeArg(String arg)
    {
        this.getArgs().remove(arg);
    }

    protected String deleteExtensionFile(String filename) {
        return filename.split("\\.")[0]; // bien echapper le point
    }

    protected void sendMessage(String answer) {
        app.setMessage(answer);
    }

    protected void suggestHelp() {
        sendMessage("You can use " + this.getCommandName() + " -h(elp) to get more informations");
    }

    public boolean hasArgs()
    {
        return this.getArgs().size()>0;
    }
    public boolean hasOptions()
    {
        return this.getOptions().size()>0;
    }
    protected void stopCommand() {
        this.stopCommand = true;
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

    public String getArg(int index) {
        try{
            return this.args.get(index);
        }catch (IndexOutOfBoundsException e){return null;}
    }

    public void setArg(int index, String arg) {
        this.getArgs().set(index, arg);
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public boolean isStopCommand() {
        return stopCommand;
    }

    public void setStopCommand(boolean stopCommand) {
        this.stopCommand = stopCommand;
    }

    @Override
    public String toString() {
        return Stream.of(commandName, optionsToString(), argsToString())
                .filter(e->e.length()>0)
                .collect(Collectors.joining(" "));
    }

    protected String optionsToString() {
     return options.stream().map(s->"-"+s).collect(Collectors.joining(" "));
    }

    protected String argsToString() {
        return args.stream().collect(Collectors.joining(" "));
    }
}
