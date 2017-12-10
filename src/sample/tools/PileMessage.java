/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package sample.tools;

import java.util.ArrayList;
import java.util.List;

public class PileMessage {
    private int indiceLecture;
    private List<String> pile;
    public PileMessage() {
        pile = new ArrayList<>();
        indiceLecture = 0;
    }

    public String getLast()
    {
        if(pile.size()>0){
            return pile.get(pile.size()-1);
        }
        return "";
    }
    public void add(String msg){
        if(!getLast().equals(msg))
        {
            pile.add(msg);
        }
        indiceLecture = pile.size();
    }

    public String lireMessagePrec(){
        try {
            String ret =  pile.get(indiceLecture-1);
            indiceLecture--;
            return ret;
        }catch (Exception e)
        {
            return pile.get(indiceLecture);
        }
    }
     public String lireMessageSuiv(){
         try {
             String ret =  pile.get(indiceLecture+1);
             indiceLecture++;
             return ret;
         }catch (Exception e)
         {
             return pile.get(indiceLecture);
         }
    }
}



