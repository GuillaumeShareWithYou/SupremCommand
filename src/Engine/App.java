package Engine;




import Engine.autocomplete.Autocompletion;
import Engine.autocomplete.Emmet;
import Engine.commands.Command;
import Engine.commands.Ecp_command;
import Engine.commands.Windows_command;
import Engine.db.DatabaseService;
import Engine.exceptions.ChangeTerminalException;
import Engine.exceptions.ForbiddenCommandException;
import Engine.tools.Config;
import Engine.tools.Context;
import Engine.tools.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class App {
    private Message message;
    private Context context;
    private Config config;
    private Autocompletion autocompletion;
    private List<PropertyChangeListener> listeners;
    public App() {
        initApp();
    }

    public void initApp() {
        listeners = new ArrayList<>();
        autocompletion = new Autocompletion(this);
        config = new Config(this);
        context = Context.SLEEP;
    }

    public void work(String command) {
        mapToContext(command);
    }

    public void mapToContext(String command) {

        if(command.startsWith("//")){
            makeComment(command);
            return;
        }
            workInContext(command);
    }

    private void makeComment(String command) {
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
    }


    public void workInContext(String command) {
        String commandName = Command.getCommandName(command);
        try {
            //TODO NOT INSTANCE A COMMAND, JUST USE A FONCTION TO GET COMMAND NAME
            setMessage(command, false);

            if(!DatabaseService.isExistingCommand(commandName))
                throw new ChangeTerminalException("command not found");

            Class classe = Class.forName("Engine.commands." + commandName + "_command");
            Constructor constructor = classe.getConstructor(Class.forName("Engine.App"), Class.forName("java.lang.String"));
            constructor.newInstance(this, command);

        }
        catch (ChangeTerminalException e) {
                new Windows_command(this, command);
        }catch (Exception e)
        {
            e.printStackTrace();
            setMessage("error in the command. We are searching why...");
        }

    }

    public void setMessage(String message) {
        setMessage(message, true);
    }

    public void setMessage(String _message, boolean isFromSystem) {
    notifyObservers(this,
            "message",
            this.message,
            this.message =  new Message(_message,isFromSystem));
    }

    public void notifyObservers(Object object, String field, Object oldValue, Object newValue) {

        for(PropertyChangeListener listener : listeners){
            listener.propertyChange(new PropertyChangeEvent(object, field, oldValue, newValue));
        }
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        switch (context) {
            case INIT:
                setMessage("Please enter your password:");
                break;
            case STANDARD:
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




    public Config getConfig() {
        return config;
    }

    public String seekAutocompletion(String text, boolean next) {
        String emmet = Emmet.applyEmmet(text);
        if(emmet!=null) return emmet;

        try{
            autocompletion.seek(text);
        }catch (Exception e)
        {
            return null;
        }
            return next ?  autocompletion.getNext()
                         : autocompletion.getPrevious();
    }

   public void addListener(PropertyChangeListener listener){
        this.listeners.add(listener);
   }

    public List<PropertyChangeListener> getListeners() {
        return listeners;
    }
}
