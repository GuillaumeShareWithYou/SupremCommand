import Engine.App;
import Engine.commands.Calc_command;
import Engine.commands.Command;
import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void getArgs(){
        String command = "name foo bar lala -3 -oh:c -oh1";
        Command cmd = new Command(new App(), command);
        System.out.println(cmd);
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
}