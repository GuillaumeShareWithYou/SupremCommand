/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package sample;

import java.util.ArrayList;
import java.util.List;

public class PileMessage {
    private int indiceLecture;
    private List<String> pile;
    public PileMessage() {
        pile = new ArrayList<>();
        indiceLecture = 0;
    }
    
    public void add(String msg){
       pile.add(msg);
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



