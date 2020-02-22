
package controller;

import bean.*;
import java.util.ArrayList;


public class ExpresionTreeController {
    private ArrayList<ExpressionTree> list = new ArrayList();//Lista que contendra los elementos de la expresion regular

    public static ExpresionTreeController instancia;    
    public static ExpresionTreeController getInstancia() {
        if (instancia == null) {
            instancia = new ExpresionTreeController();
        }
        return instancia;
    }

    public ExpresionTreeController() {
    }

    //Iserta nombre y arbol de la expresion regular a una lista;
    public void Insert(String name, Node tree) {
        ExpressionTree e = new ExpressionTree(name, tree);
        list.add(e);
    }
}
