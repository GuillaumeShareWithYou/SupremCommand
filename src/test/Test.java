package test;

import Engine.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public Test()
    {
        System.out.println("sout pas sysout");
    }
    public static void main(String[] args) throws Exception{

        Context.SLEEP.setSymbole("Mpo");

        System.out.println(Context.SLEEP.getSymbole());
    }
}
