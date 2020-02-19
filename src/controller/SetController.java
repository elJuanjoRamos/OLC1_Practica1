/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Set;
import bean.Token;
import java.util.ArrayList;


/**
 *
 * @author Juan José Ramos
 */
public class SetController {
    private ArrayList<Set> list = new ArrayList();
    public static SetController instancia;
    public static SetController getInstancia() {
        if(instancia == null) {
            instancia = new SetController();
        }
        return instancia;
    }

    public SetController() {
    }
    
    
    
    
    public void assemble_Sets(){
        boolean isInterval = false;
        ArrayList<Token> arrayListTokens =TokenController.getInstancia().getArrayListTokens();
        for (int i = 0; i < arrayListTokens.size(); i++) {
            Token tok = arrayListTokens.get(i);
            if(tok.getLexema().toLowerCase().equals("conj") ){
                Token name = arrayListTokens.get(i+2);
                String setName = name.getLexema();
                
                int pos = i+5;
                ArrayList<String> elementos = new ArrayList();
                for (int j = pos; j < arrayListTokens.size(); j++) {
                    Token t = arrayListTokens.get(j);
                    if(!t.getLexema().equals(";")){
                        if (!t.getLexema().equals(",")) {
                            if (t.getLexema().equals("~")) {
                                isInterval = true;
                            }
                            elementos.add(t.getLexema());                            
                        }

                    } else {
                        break;
                    }
                }
                Insert(setName, elementos, isInterval);
            }
        }
    }
    
    
    
    public void Insert(String name, ArrayList<String> elements, boolean isInterval ){
        ArrayList<String> newElements = new ArrayList();   
        Set s = null;
        
        if (isInterval) {
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).equals("~")) {
                    newElements = getNewElements(elements.get(i-1).charAt(0), elements.get(i+1).charAt(0));
                    break;
                }
            }
            s = new Set(name, newElements);
        } else{
            s = new Set(name, elements);
        }
        list.add(s);
       
    }
    
    public ArrayList getNewElements(char ant, char sig){
        ArrayList<String> ar = new ArrayList();
        
        //DIGITS
        if (Character.isDigit(ant)) {
       
            for (int i = Integer.parseInt(String.valueOf(ant)); i < Integer.parseInt(String.valueOf(sig))+1; i++) {
                ar.add(String.valueOf(i));
            }
            return ar;
        //LETTERS
        }else if(Character.isLetter(ant)){
            int initValue = (int)ant;
            int endValue = (int)sig;
            
            for (int i = initValue; i <= endValue; i++) {
                ar.add(String.valueOf((char)i));
            }
            return ar; 
        //ASCII CODES 32 TO 125
        } else if((int)ant >= 32 && (int)sig <= 125){
            for (int i = (int)ant; i <= (int)sig; i++) {
                
                if (!Character.isDigit(ant) && !Character.isDigit(sig) && !Character.isLetter(ant) && !Character.isLetter(sig)) {
                    ar.add(Character.toString((char)i));
                    
                }
            }
            return ar;
        }
        return null;
    }
    
}
