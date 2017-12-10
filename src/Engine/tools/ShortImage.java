package Engine.tools;

import java.io.*;

public class ShortImage {

    private File file;


    private final int NB_LINE_UP_TO_CUT = 7;

    public ShortImage(File file) {
        this.file = file;
    }



    public void copyAndCutImage(String newFileName) throws IOException {

        String line;
        StringBuilder contentFileIn = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for(int i=0; i < NB_LINE_UP_TO_CUT ; ++i)
                reader.readLine();
            while((line = reader.readLine())!= null){
                line = line.substring(105,260);
                contentFileIn.append(line+"\n");
            }
        }

        try{
            PrintWriter pw = new PrintWriter(new FileWriter(new File(newFileName)));
            pw.write(contentFileIn.toString());
            pw.flush();
        }catch (IOException e){e.printStackTrace();}

        System.out.println(contentFileIn.toString());

    }
    public static void main(String[] args) {

        ShortImage manager = new ShortImage(new File("files/manon3.txt"));
        try {
            manager.copyAndCutImage("files/manon4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
