
package bean;

import java.util.ArrayList;

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
