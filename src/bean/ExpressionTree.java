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
public class ExpressionTree {
    private String name;
    private Node root;

    public ExpressionTree(String name, Node root) {
        this.name = name;
        this.root = root;
    }

    public ExpressionTree() {
    }
    
}
