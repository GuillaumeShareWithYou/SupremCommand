import Engine.App;
import Engine.autocomplete.Autocompletion;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AutocompletionTest {
    private static Autocompletion autocompletion;

    @BeforeClass
    public static void init(){
        autocompletion = new Autocompletion(new App());
    }
    @Test
    public void seek() throws Exception {
     autocompletion.seek("ecp app -in");
        String s = autocompletion.getNext();

        System.out.println(autocompletion.getCommand());
        System.out.println(autocompletion.getLastKeyword());
        assertEquals("Ecp app", s);
    }

    @Test
    public void getNext() throws Exception {
    }

}