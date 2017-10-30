package Engine;


import javax.xml.crypto.Data;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App extends Observable {
    private Message message;
    private Context context;
    private Config config;
    public App() {
        initApp();
    }

    public void initApp() {
        config = new Config();
        context = Context.SLEEP;
    }

    public void work(String command) {
        mapToContext(command);
    }

    public void mapToContext(String command) {


        if (command.equals("go")) { //for now
           this.setContext(Context.STANDARD);
           Ecp_command.setStarted(true);
        }else if (this.getContext() == Context.CONFIRM) {
            if (command.equals("y")) {
                setContext(Context.STANDARD);

            } else {
                setMessage("y or n ?");
            }
        } else if (this.getContext() == Context.INIT) {
           connectUser(command);
        }else{

            workInContext(command);
        }

    }


    private void connectUser(String command) {

        if (command.equals("dev")) {
            setContext(Context.CONFIRM);

        } else {
            setMessage("wrong password");
        }
    }


    public void workInContext(String command) {
        String commandName = null;
        try {

            commandName = Command.indicateCommandName(command);

            boolean exists = DatabaseService.isExistingCommand(commandName);



            if (exists && !DatabaseService.isPermitted(commandName,getContext())) {
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
            case CONFIRM:
                setMessage("Welcome to the main system\n######Initialisation######" + "\n" +
                        "G++ -g -Wall App.o context.o command.o option.o -o executable_live.exe" + "\n" +
                        "Do you really want to run executable_live.exe and access to the system ?[y/n]");
                break;
            case STANDARD:
                printWelcomeMsg();
                setMessage("App is now ready");
                break;
            case MANAGER:
                setMessage("Welcome back Guillaume, we missed you !");
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

}
