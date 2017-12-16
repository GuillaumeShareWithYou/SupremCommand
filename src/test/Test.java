package test;


import Engine.tools.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        String dateHeure = new SimpleDateFormat("EEEE dd MMMM YYYY HH:mm:ss").format(new Date());
        System.out.println(dateHeure);
        System.out.println(Color.valueOf("BLUE").getColor());
    }
}
