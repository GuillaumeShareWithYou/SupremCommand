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

    List<String> list = new ArrayList<>();
    list.add("je suis");
    list.add("une liste");
    list.add("cyclique");
    CycleList clist = new CycleList(list);
    for(int i=0; i<25; i++){
        System.out.println(clist.getPrevious());
    }
    }
}
