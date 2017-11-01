package Engine;


import javax.xml.crypto.Data;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App extends Observable {
    private Message message;
    private Context context;
    private Config config;
    private Autocompletion autocompletion;
    public App() {
        initApp();
    }

    public void initApp() {
        config = new Config();
        autocompletion = new Autocompletion(this);
        context = Context.SLEEP;
    }

    public void work(String command) {
        mapToContext(command);
    }

    public void mapToContext(String command) {

        if(command.startsWith("//")){
            command = command.substring(2);
            int size = command.length();
            StringBuilder deco = new StringBuilder();
            deco.append("\t\t");
            for(int i=0; i<size; i++){
                deco.append("* ");
            }
            deco.append("\n");
            deco.append("\t\t"+command);
            deco.append("\n");
            deco.append("\t\t");
            for(int i=0; i<size; i++){
                deco.append("* ");
            }
           setMessage(deco.toString());
            return;
        }
        setMessage(command,false); // renvoie le msg utilisateur avec le prompt

        if (this.getContext() == Context.INIT) {
           connectUser(command);
        }else{
            workInContext(command);
        }

    }


    private void connectUser(String command) {

        if (command.equals("dev")) {
            setContext(Context.STANDARD);

        } else {
            setMessage("wrong password");
        }
    }


    public void workInContext(String command) {
        String commandName = null;
        try {

            commandName = Command.indicateCommandName(command);

            boolean exists = DatabaseService.isExistingCommand(commandName);

            if(!exists) throw new UnexistingCommandException("command not found");


            if (!DatabaseService.isPermitted(commandName,getContext())) {
                throw new ForbiddenCommandException("You can't use this command now");
            }
            Class classe = Class.forName("Engine." + commandName + "_command");
            Constructor constructor = classe.getConstructor(Class.forName("Engine.App"), Class.forName("java.lang.String"));
            constructor.newInstance(this, command);

        } catch (ForbiddenCommandException e) {
            this.setMessage("You can't use the command "+commandName+" at the moment.");

        }catch (UnexistingCommandException e)
        {
            this.setMessage("This cmd doesn't exists,type !["+commandName+"] to open in windows cmd.");
        }catch (OnlyCommentException e)
        {
            command = command.substring(2);
            this.setMessage("####----"+command+"----####");
        }
        catch (ChangeTerminalException e) {
            if(getContext().compareTo(Context.STANDARD)>=0)
            {
                new Windows_command(this, command);
            }else{
                setMessage("You need to init the app first!");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            setMessage("error in the command. We are searching why...");
        }

    }

    public void setMessage(String message) {
        this.message = new Message(message);
        this.setChanged();
        this.notifyObservers();
    }
    public void setMessage(String message, boolean isFromSystem) {
        this.message = new Message(message,isFromSystem);
        this.setChanged();
        this.notifyObservers();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        switch (context) {
            case INIT:
                setMessage("Personnal application, the password is 'dev' if you forgot\nPlease enter your password:");
                break;
            case STANDARD:
                printWelcomeMsg();
                setMessage("App is now ready");
                break;
            case MANAGER:
                setMessage("You now have all privileges !");
                break;
        }
    }

    public Message getMessage() {
        return message;
    }


    public void printWelcomeMsg() {
        new Fread_command(this, "fread files/welcome.txt");
    }

    public Config getConfig() {
        return config;
    }

    public String seekAutocompletion(String text, boolean next) {
        String emmet = applyEmmmet(text);
        if(emmet!=null) return emmet;

        try{
            autocompletion.seek(text);
        }catch (Exception e)
        {
            return null;
        }
        if(next)
            return autocompletion.getNext();
        return autocompletion.getPrevious();
    }


    private String applyEmmmet(String text) {
        switch (text)
        {
            case "eai":
                return "ecp app --init -dev";
            case "ex":
                return "/exit";
            default:
                return null;
        }
    }
}
