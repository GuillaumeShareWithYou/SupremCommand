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
    public void getNext() throws Exception {
    }

}