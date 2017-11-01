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
        this.command = analyseCommand();

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
        //get origin command
        this.command = command;
    }

    protected String analyseCommand() {


        command = deleteCommandName(command);
        try {

            // Pattern composé d'un ou deux tirets suivis de lettres ou d'underscore (pas de nombres pour faire des soustractions)
            Pattern p = Pattern.compile("[\\-]{1,2}[a-zA-Z_]+");
            Matcher m = p.matcher(command);
            while (m.find()) {

                String opt = m.group();
                opt = opt.replaceFirst("[\\-]{1,2}", "");
                if(opt!="")
                     options.add(opt);
                command = command.replaceFirst(m.group(), "");
            }

        } catch (Exception e) {

        }
        try {

            Pattern p = Pattern.compile("[^ ]+");
            Matcher m = p.matcher(command);
            while (m.find()) {
              //  command = command.replaceFirst(m.group(), "");
                args.add(m.group());
            }
        } catch (Exception e) {

        }
        System.out.println(this);
        return command;
    }

    private String deleteCommandName(String command) {
        try {

            this.commandName = getCommandName(command);

            command = command.replaceFirst("^/","");
            command = command.replaceFirst(commandName, "");
        } catch (IllegalStateException e) {

        }
        return command;
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
    public static String getCommandName(String command)
    {
        String commandName = new String();
        Pattern pattern = Pattern.compile("^[\\w/]+");
        Matcher matcher = pattern.matcher(command);
        matcher.find();
        commandName = matcher.group();
        commandName = commandName.replaceFirst("/","");
        return commandName;

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

    public static String indicateCommandName(String command) throws ChangeTerminalException, OnlyCommentException {
        command = command.toLowerCase();
        Pattern p = Pattern.compile("[^ ]+");
        Matcher m = p.matcher(command);
        m.find();
        String commandName = m.group();
        if(commandName.charAt(0) == '!') throw new ChangeTerminalException("go to windows cmd");
        if(commandName.startsWith("//")) throw new OnlyCommentException("it's a comment, nothing to execute");
        if (commandName.charAt(0) == '/') {
            commandName = commandName.substring(1);
        }
        commandName = commandName.replaceFirst(String.valueOf(commandName.charAt(0)), String.valueOf(commandName.charAt(0)).toUpperCase());
        return commandName;
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
        if (index >= 0 && index < this.args.size()) {
            return this.args.get(index);
        }
        return null;
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

    @Override
    public String toString() {
        return commandName
                + optionsToString() + argsToString();
    }

    protected String optionsToString() {
        StringBuilder s = new StringBuilder();

        for (String st : getOptions()) {
            s.append(" -" + st);
        }
        return s.toString();
    }

    protected String argsToString() {
        StringBuilder s = new StringBuilder();

        for (String st : getArgs()) {
            s.append(" "+st);
        }
        return s.toString();
    }
}
