package Engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Observable;
import java.util.Observer;

public class Config extends Observable {

    Observer prompt;
    private String browser;
    private String password = "manon";
    private Color color = Color.LIGHTBLUE;
    Config() {
        setDefault();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setDefault() {
        try {

            Connection db = Database.getInstance();
            //récupérer le navigateur par défault
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("select config_value from config where config_var='browser'");
            if(rs.next())
                 this.browser = rs.getString("config_value");
            //recuperer la couleur par défault
            rs = st.executeQuery("select config_value from config where config_var='color'");
            if(rs.next())
                this.color = Color.valueOf(rs.getString("config_value"));

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("defaut config failed");
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(String color){
        try{
            color = color.toUpperCase().trim();
            setColor(Color.valueOf(color));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setColor(Color color) {
        this.color = color;
        this.setChanged();
        this.notifyObservers();
    }

    public Observer getPrompt() {
        return prompt;
    }

    public void setPrompt(Observer prompt) {
        this.prompt = prompt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
