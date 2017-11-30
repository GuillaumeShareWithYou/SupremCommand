package test;


import Engine.App;
import Engine.Color;
import Engine.Command;
import Engine.CycleList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args) throws Exception {

       /* Pattern pattern = Pattern.compile("^[^\\s *0-9].*$");
        Matcher matcher = pattern.matcher(" azer");
        System.out.println(" azer "+matcher.find());
        matcher = pattern.matcher("*azer");
        System.out.println("*azer "+matcher.find());
        matcher = pattern.matcher("\\ 4azer");
        System.out.println("\\ 4azer "+matcher.find());
        matcher = pattern.matcher("1azer");
        System.out.println("4azer "+matcher.find());*/
/*
       Pattern p = Pattern.compile("(\\w)\\1+");
       Matcher m = p.matcher("array");
       m.find();
        System.out.println(m.group()); */

        System.out.println(Color.valueOf("BLUE").getColor());
    }
}
