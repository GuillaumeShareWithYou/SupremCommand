package test;


import Engine.App;
import Engine.Command;
import Engine.CycleList;
import Engine.Gtk_command;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args) throws Exception{

       /* Pattern pattern = Pattern.compile("^[^\\s *0-9].*$");
        Matcher matcher = pattern.matcher(" azer");
        System.out.println(" azer "+matcher.find());
        matcher = pattern.matcher("*azer");
        System.out.println("*azer "+matcher.find());
        matcher = pattern.matcher("\\ 4azer");
        System.out.println("\\ 4azer "+matcher.find());
        matcher = pattern.matcher("1azer");
        System.out.println("4azer "+matcher.find());*/

       for(int i=0; i<100;++i){
        int x = i;
          new Thread(()->{
              execute(x);
          }).start();
       }

    }

    public static synchronized void execute(int x){

        try {
            Thread.sleep(100);
            System.out.println("Thread "+x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
