
package bean;

public class RowTable {
    private String lexema; // lexema
    private String noLeaf; // numero de hoja
    private String follow; // siguientes

    public RowTable() {
    }

    public RowTable(String l, String noLeaf, String follow) {
        this.lexema = l;
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
