/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Juan José Ramos
 */
public class RowTable {
    private String lexema; // lexema
    private String noLeaf; // numero de hoja
    private String follow; // siguientes

    public RowTable() {
    }

    public RowTable(String noLeaf, String follow) {
        this.lexema = "";
        this.noLeaf = noLeaf;
        this.follow = follow;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getNoLeaf() {
        return noLeaf;
    }

    public void setNoLeaf(String noLeaf) {
        this.noLeaf = noLeaf;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }
    
    

    
    
    
    
}
