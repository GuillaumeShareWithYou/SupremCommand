package Engine;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Config {


    private String browser;

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

            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("select config_value from config where config_var='browser'");
            rs.next();
            this.browser = rs.getString("config_value");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("defaut config failed");
        }
    }
}
