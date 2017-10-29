package Engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    public static String getCommandDescription(String s) {
        Connection db = Database.getInstance();
        try {
            PreparedStatement st = db.prepareStatement("select command_name, description from command where command_name = ? and description IS NOT NULL");
            st.setString(1, s);
            ResultSet res = st.executeQuery();
            res.next();
            StringBuilder strb = new StringBuilder();

            strb.append(res.getString("command_name").toUpperCase() + ":  ");
            strb.append(res.getString("description"));
            return strb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "command "+s+" not found here.";
        }
    }


    public static String printAllCommands(Context context) {
        Connection db = Database.getInstance();
        try {
            PreparedStatement st = db.prepareStatement("select command_name, description from command natural join context_command natural join context where description IS NOT NULL" +
                    " and context_id <=(select context_id from context where context_name = ?) order by command_name");
            st.setString(1,context.getName());
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
    public static boolean isExistingCommand(String commandName) {
        Connection db = Database.getInstance();
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

    public static String getCommandOption(String commandName) {
        Connection db = Database.getInstance();
        try {
            PreparedStatement st = db.prepareStatement("select command_name, option_name, option_description from command natural join command_option where command_name = ? and option_description IS NOT NULL" +
                    "order by option_name");
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
            return strb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "options not found for command "+commandName;
        }
    }

    public static List<String> getForbiddenCommands(Context context) {
        try{

            Connection db = Database.getInstance();
            PreparedStatement st = db.prepareStatement("select command_name from command where command_id not in (select command_id from context_command NATURAL join context where context_name=?)");
            st.setString(1,context.getName());
            ResultSet res = st.executeQuery();
            List<String> forbiddenCommands = new ArrayList<>();
            while(res.next())
            {
                forbiddenCommands.add(res.getString("command_name"));
            }
            System.out.println(forbiddenCommands);
            return forbiddenCommands;
        }catch(SQLException e){

            return null;
        }
    }

    public static boolean isPermitted(String commandName, Context context) {
        try{

            Connection db = Database.getInstance();
            PreparedStatement st = db.prepareStatement(
                    "select command_id from command NATURAL join context_command natural join context" +
                            " where command_name=? " +
                    "and context_id <= (select context_id from context where context_name=?)");
            st.setString(1,commandName);
            st.setString(2,context.getName());
            ResultSet res = st.executeQuery();

            return res.next();
        }catch(SQLException e){
        e.printStackTrace();
            return false;
        }
    }

    public static String printCommandArguments(String commandName) {
        Connection db = Database.getInstance();
        try {
            PreparedStatement st = db.prepareStatement("select command_name, argument_name, argument_description from command natural join argument where command_name = ? and argument_description IS NOT NULL " +
                    "order by argument_name");
            st.setString(1, commandName);
            ResultSet res = st.executeQuery();
            res.next();
            StringBuilder strb = new StringBuilder();
            strb.append(res.getString("command_name").toUpperCase() + " [arg] : ");
            strb.append(res.getString("argument_name").toUpperCase() + " -> ");
            strb.append(res.getString("argument_description") + "\n");
            while (res.next()) {
                strb.append(res.getString("argument_name").toUpperCase() + " ->");
                strb.append(res.getString("argument_description") + "\n");
            }
            return strb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "arguments not found for command "+commandName;
        }
    }
}
