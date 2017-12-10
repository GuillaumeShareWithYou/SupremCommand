package Engine.autocomplete;

import java.util.HashMap;
import java.util.Map;

public class Emmet {
    static Map<String,String> shortcuts = new HashMap<>();
    static{

        shortcuts.put("eai","ecp app --init -dev");
        shortcuts.put("eaid","ecp app --init --discreet -dev ");
        shortcuts.put("ex","/exit");
        shortcuts.put("e++","ecp upgrade");
        shortcuts.put("sc","/show cmd");
    }

    public static String applyEmmet(String key){
        if(!shortcuts.keySet().contains(key)) return null;
        return shortcuts.get(key);
    }
}
