import Engine.App;
import Engine.commands.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CommandTest {
    @Test
    public void analyseCommand() throws Exception {
        String command = "calc 6%2";
        Command cmd = new Calc_command(new App(),command);
        String res = ((Calc_command)cmd).getRes();
        System.out.println(cmd.getArgs());
        System.out.println(res);
       // assertEquals("2",res);
        //assertEquals("ecp", cmd.getCommandName());
    }

    @Test
    public void testSplit(){

        String command = " ecp  app  -init -dev ";
        String splited[] = command.split(" +");
        assertEquals("-dev", splited[splited.length-1]);
        System.out.println(Arrays.toString(splited));
    }


    @Test
    public void testPositiveLookAhead(){
        Pattern p = Pattern.compile("\\w(?!foo)");
        Matcher m = p.matcher("unfoo");
        while(m.find())
        {
            System.out.println(m.group());
        }
    }

    @Test
    public void mysqlCommand(){
        System.out.println("tralalala");
        new Mysql_command(new App(),"mysql");
    }
    @Test
    public void hbmailCommand(){
        new Hbmail_command(new App(),"hbmail");
    }



    @Test
    public  void testMethodStaticGetCommandName(){
        String commandName = "show45 -all commands";
        assertEquals("show45",Command.getCommandName(commandName));
    }
}