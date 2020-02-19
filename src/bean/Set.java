/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;

/**
 *
 * @author Juan José Ramos
 */
public class Set {
    String name;
    ArrayList<String> elements;
    
    
    public Set() {
        
    }
    
    public Set(String n, ArrayList e) {
        this.name = n;
        this.elements = e;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public void setElements(ArrayList<String> elements) {
        this.elements = elements;
    }
    
    
    
    
    
}
