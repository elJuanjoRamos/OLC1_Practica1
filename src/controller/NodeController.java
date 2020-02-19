/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
public class NodeController {
    private ArrayList<String> listAux = new ArrayList();
    private ArrayList<String> list = new ArrayList();
    private Stack stk = new Stack();
    int index = 0;
    
    
    public static NodeController instancia;
    public static NodeController getInstancia() {
        if(instancia == null) {
            instancia = new NodeController();
        }
        return instancia;
    }

    public NodeController() {
    }
    
    public void Insert(String element){
        listAux.add(element);
        
    }
    
    public void clearList(){
        this.listAux.clear();
        this.list.clear();
    }
    
    public void Reordering(){
        //Le da la vuelta a los elementos del arreglo
        for (int i = listAux.size()-1; i >= 0; i--) {
            list.add(listAux.get(i));
        }
        
        //Envia los nodos del arreglo al stack
        for (String n : list) {
            InsertStack(n);
        }
        
        Node n2 = (Node)stk.pop();
        Node n1 = new Node("#");
        Node n = new Node(".", n1, n2);
        
        n.print("arbol_texto"+index+".jpg");
        index++;
        //iterate(n);
        
    /*    System.out.println("\t\t  " + n.getElement());
        System.out.println("\t\t / " + " \\ \t\t");
        System.out.println("\t\t"+n.getLeftChild().getElement() + "     "  + n.getRightChild().getElement());
        System.out.println("\t   / " + " \t\t \\   ");
        System.out.println("\t"+n.getLeftChild().getLeftChild().getElement() + "            "  + n.getRightChild().getLeftChild().getElement());
        System.out.println("\t\t\t / \\");
        System.out.println("\t\t\t" + n.getRightChild().getLeftChild().getLeftChild().getElement() + "   "+n.getRightChild().getLeftChild().getRightChild().getElement());
        System.out.println("\t\t       / \\      \\");
        System.out.println("\t\t     " +n.getRightChild().getLeftChild().getLeftChild().getLeftChild().getElement() +  " " + n.getRightChild().getLeftChild().getLeftChild().getRightChild().getElement() +        "      " + n.getRightChild().getLeftChild().getRightChild().getLeftChild().getElement());
        System.out.println("\t\t        / \\");
        System.out.println("\t\t     " +n.getRightChild().getLeftChild().getLeftChild().getRightChild().getLeftChild().getElement() + "     " + n.getRightChild().getLeftChild().getLeftChild().getRightChild().getRightChild().getElement());
        System.out.println("\t\t\t    / \\");
        System.out.println("\t\t\t  " + n.getRightChild().getLeftChild().getLeftChild().getRightChild().getRightChild().getLeftChild().getElement() + " " + n.getRightChild().getLeftChild().getLeftChild().getRightChild().getRightChild().getRightChild().getElement());
    */
    }
    public void InsertStack(String s){
        Node newNode = new Node();
        Node right = new Node();
        Node left = new Node();
        
        if (s.equals("|") || s.equals(".")) {
            left = (Node)stk.pop();
            right = (Node)stk.pop();
            
            newNode = new Node(s, right, left);
            stk.push(newNode);
            
        } else if(s.equals("*") || s.equals("+")|| s.equals("?")){
            left = (Node)stk.pop();
            Node n = new Node(s, null, left);
            stk.push(n);  
        } 
        else{
            Node node = new Node(s);
            stk.push(node);
        }
    }
    
    
    public void iterate(Node n){
        if (n != null) {
            iterate(n.getLeftChild());
            System.out.print(n.getElement() +",");
            iterate(n.getRightChild());
        }
    }
    
    
    
    
    
    
    
    
}
