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
        config = new Config(this);
        autocompletion = new Autocompletion(this);
        context = Context.SLEEP;
        listeners = new ArrayList<>();
    }

    public void work(String command) {
        mapToContext(command);
    }

    public void mapToContext(String command) {

        if(command.startsWith("//")){
            makeComment(command);
            return;
        }
        setMessage(command,false); // renvoie le msg utilisateur avec le prompt

        if (this.getContext() == Context.INIT) {
           connectUser(command);
        }else{
            workInContext(command);
        }

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


    private void connectUser(String command) {
      if(command.equals(config.getPassword()))
      {
          new Ecp_command(this,"ecp app --init -dev");
      }else{
          setMessage("Please try again.");
      }
    }


    public void workInContext(String command) {
        String commandName = null;
        try {

            commandName = Command.indicateCommandName(command);

            boolean exists = DatabaseService.isExistingCommand(commandName);

            if(!exists) throw new ChangeTerminalException("command not found");


            if (!DatabaseService.isPermitted(commandName,getContext())) {
                throw new ForbiddenCommandException("You can't use this command now");
            }
            Class classe = Class.forName("Engine.commands." + commandName + "_command");
            Constructor constructor = classe.getConstructor(Class.forName("Engine.App"), Class.forName("java.lang.String"));
            constructor.newInstance(this, command);

        } catch (ForbiddenCommandException e) {
            this.setMessage("You can't use the command " + commandName + " at the moment.");

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
        if(next)
            return autocompletion.getNext();
        return autocompletion.getPrevious();
    }

   public void addListener(PropertyChangeListener listener){
        this.listeners.add(listener);
   }
}
