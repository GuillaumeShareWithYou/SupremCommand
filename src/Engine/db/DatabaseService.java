package Engine.db;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private static Connection db;
    static {
        db = Database.getInstance();
    }

    public static String getCommandDescription(String s) {

        try {
            PreparedStatement st = db.prepareStatement("select command_name, description from command where command_name = ? and description IS NOT NULL");
            st.setString(1, s);
            ResultSet res = st.executeQuery();
            StringBuilder strb = new StringBuilder();
            if(res.next()){

                strb.append(res.getString("command_name").toUpperCase() + ":  ");
                strb.append(res.getString("description"));
            }
            return strb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "command "+s+" not found here.";
        }
    }


    public static String printAllCommands() {

        try {
            PreparedStatement st = db.prepareStatement("select command_name, description from command where description IS NOT NULL");
            ResultSet res = st.executeQuery();
            StringBuilder strb = new StringBuilder();
            strb.append("List of availables commands : \n");
            while (res.next()) {
                strb.append(res.getString("command_name").toUpperCase() + " : " + res.getString("description") + "\n");
            }
            return strb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "System error 404";
        }

    }
    public static List<String> getAllCommandsBeginWith(String begining)
    {
        List<String> commands = new ArrayList<>();

        try {
            PreparedStatement st = db.prepareStatement("select command_name from command where command_name LIKE ? ");
            st.setString(1,begining+"%");
            ResultSet res = st.executeQuery();
            StringBuilder strb = new StringBuilder();
            while (res.next()) {
               commands.add(res.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commands;
    }

    public static String getCommandBeginWith(String begining)
    {
        String commandName = new String();

        try {
            PreparedStatement st = db.prepareStatement("select command_name from command where command_name LIKE ? limit 1");
            st.setString(1,begining+"%");
            ResultSet res = st.executeQuery();
            StringBuilder strb = new StringBuilder();
            if(res.next())
                commandName = res.getString(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandName;
    }
    public static boolean isExistingCommand(String commandName) {

        try {
            PreparedStatement st = db.prepareStatement("select command_name from command where command_name = ?");
            st.setString(1,commandName);
            ResultSet res = st.executeQuery();

           return res.next();



        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String printCommandOption(String commandName) {

        try {
            PreparedStatement st = db.prepareStatement("select command_name, option_name, option_description from command natural join command_options natural join options where command_name = ? and option_description IS NOT NULL " +
                    " order by option_name");
            st.setString(1, commandName);
            ResultSet res = st.executeQuery();
            res.next();
            StringBuilder strb = new StringBuilder();
            strb.append(res.getString("command_name").toUpperCase() + " [option] : ");
            strb.append("-"+res.getString("option_name").toUpperCase() + " -> ");
            strb.append(res.getString("option_description") + "\n");
            while (res.next()) {
                strb.append("-"+res.getString("option_name").toUpperCase() + " ->");
                strb.append(res.getString("option_description") + "\n");
            }
             return strb.toString().substring(0,strb.length()-1);
        } catch (SQLException e) {

            return "no options for the command "+commandName;
        }
    }



    public static String printCommandArguments(String commandName) {

        try {
            PreparedStatement st = db.prepareStatement("select command_name, argument_name, argument_description from command natural join argument where command_name = ? and argument_description IS NOT NULL " +
                    " order by argument_name");
            st.setString(1, commandName);
            ResultSet res = st.executeQuery();
            StringBuilder strb = new StringBuilder();
            if(res.next()){
                strb.append(res.getString("command_name").toUpperCase() + " [arg] : ");
                strb.append(res.getString("argument_name").toUpperCase() + " -> ");
                strb.append(res.getString("argument_description") + "\n");
            }
            while (res.next()) {
                strb.append(res.getString("argument_name").toUpperCase() + " ->");
                strb.append(res.getString("argument_description") + "\n");
            }

            return strb.toString().length() > 0 ? strb.toString().substring(0,strb.length()-1) : strb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "no arguments for the command "+commandName;
        }
    }

    public static List<String> getArgumentsCommand(String commandName)
    {
        List<String> args = new ArrayList<>();

        try {
            PreparedStatement st = db.prepareStatement("select argument_name from command natural join argument where command_name = ? " );
            st.setString(1, commandName);
            ResultSet res = st.executeQuery();
            while(res.next())
            {
                args.add(res.getString(1));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return args;
    }

    public static String getOptionBeginWith(String commandName, String optionName) {

        String ret = "";
        try{

            PreparedStatement st = db.prepareStatement("select option_name from options natural join command_options natural join command where option_name LIKE ? and command_name = ?");
            st.setString(1,optionName+"%");
            st.setString(2,commandName);
            ResultSet res = st.executeQuery();
            if(res.next())
               ret = res.getString(1);
        }catch (Exception e){}
    return ret;
    }
    public static List<String> getAllOptionsBeginWith(String commandName, String optionName) {

        List<String> ret = new ArrayList<>();
        try{

            PreparedStatement st = db.prepareStatement("select option_name from options natural join command_options natural join command where option_name LIKE ? and command_name = ? ");
            st.setString(1,optionName+"%");
            st.setString(2,commandName);
            ResultSet res = st.executeQuery();
            while(res.next()){

                ret.add(res.getString(1));
            }
        }catch (Exception e){}
        return ret;
    }

    public static String getArgumentBeginWith(String commandName, String argument) {
        String ret = new String();
        try{
            PreparedStatement st = db.prepareStatement("select argument_name from argument natural join command where argument_name LIKE ? and command_name = ? ");
            st.setString(1,argument+"%");
            st.setString(2,commandName);
            ResultSet res = st.executeQuery();
            if(res.next())
              ret = res.getString(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    public static List<String> getAllArgumentsBeginWith( String commandName, String argument) {
        List<String> ret = new ArrayList<>();
        try{
            PreparedStatement st = db.prepareStatement("select argument_name from argument natural join command where argument_name LIKE ? and command_name = ? ");
            st.setString(1,argument+"%");
            st.setString(2,commandName);

            ResultSet res = st.executeQuery();
            while(res.next()){
                ret.add(res.getString(1));
            }

        }catch (Exception e){}
        return ret;
    }
}
