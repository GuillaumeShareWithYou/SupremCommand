package Engine;

import javafx.scene.shape.PathElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fread_command extends Command {

    private final static String editor = "\"C:\\Program Files (x86)\\Brackets\\Brackets.exe\"";

    private boolean openEditor;

    public Fread_command(App app, String command) {
        super(app,command);
        if(stopCommand) return;
        try{
            openEditor = this.getOptions().contains("e");
        }catch (IllegalStateException e){}

        if(openEditor)
        {
            File f =null;
            try {
                f = new File(this.getArg(0));
                ProcessBuilder ps = new ProcessBuilder(editor,f.getAbsolutePath());
                ps.redirectErrorStream(true);
                Process pr = ps.start();
                this.sendMessage(f.getName()+" is open in Brackets");
            } catch (IOException e) {
                this.sendMessage("System failed to open "+f.getAbsolutePath()+" with "+editor);
            };
        }else{
            String fileContent = readFile(this.getArg(0));
            this.sendMessage(fileContent);
        }
    }

    public String readFile(String path)
    {

        try {

            Scanner sc = new Scanner(new File(path));
            StringBuilder strb = new StringBuilder();
            strb.append("\n"); //saut de ligne systematique dans un fread pour avoir le fichier aligné sur la marge
            while(sc.hasNext())
            {
                strb.append(sc.nextLine()+"\n");
            }
            return strb.toString();
        }catch (FileNotFoundException e) {
            return "File not found";
        }
    }
}
