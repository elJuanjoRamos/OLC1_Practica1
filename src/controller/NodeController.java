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
    private ArrayList<String> listAux = new ArrayList();//Lista que contendra los elementos de la expresion regular
    private ArrayList<String> list = new ArrayList();  //Lista que servira para reordenar los elementos de la expresion regular
    private Stack stk = new Stack();
    int index = 0;
    Node raiz = null;
       
    /*Variables nuevas de prueba*/
    int cant;
    int altura;
    
    
    
    public static NodeController instancia;
    public static NodeController getInstancia() {
        if(instancia == null) {
            instancia = new NodeController();
        }
        return instancia;
    }

    public NodeController() {
    }
    
    //Iserta los elementos de la expresion regular a una lista;
    public void Insert(String element){
        listAux.add(element);
        
    }
    
    //Limpa las listas de elementos
    public void clearList(){
        //raiz = null;
        this.listAux.clear();
        this.list.clear();
    }
    
    //Reordena los elementos de la expresion regular para 
    public void Reordering(){
        //Le da la vuelta a los elementos del arreglo
        for (int i = listAux.size()-1; i >= 0; i--) {
            list.add(listAux.get(i));
        }
        //Envia los nodos del arreglo al stack
        for (String n : list) {
            InsertStack(n);
        }
        
        Node left = (Node)stk.pop();
        Node right = new Node("#");
        raiz = new Node(".", right, left, "0", "0", false);
        
        leafNode(raiz);
        raiz.print("arbol_texto"+index+".jpg");
        
        index++;
        
    }
    public void InsertStack(String s){
        Node newNode = new Node();
        Node right = new Node();
        Node left = new Node();
        boolean anuable = false;
        
        
        if (s.equals("|") || s.equals(".")) {
            left = (Node)stk.pop();
            right = (Node)stk.pop();
                    
            if (s.equals("|")) {
                if (left.isAnulable() || right.isAnulable()) {
                    anuable = true;
                }
            } else if(s.equals(".")){
                if (left.isAnulable() && right.isAnulable()) {
                    anuable = true;
                }
            }
            newNode = new Node(s, right, left, "0", "0", anuable);
            anuable = false;
            stk.push(newNode);
            
        } else if(s.equals("*") || s.equals("+")|| s.equals("?")){
            if (s.equals("+")) {
                anuable = false;
            } else {
                anuable = true;
            }
            left = (Node)stk.pop();
            Node n = new Node(s, null, left, "0", "0", anuable);
            anuable = false;
            stk.push(n);  
        } 
        else{
            Node node = new Node(s);
            stk.push(node);
        }
        
       
        
        
        /*Node newNode = new Node();
        Node right = new Node();
        Node left = new Node();
        
        if (s.equals("|") || s.equals(".")) {
            left = (Node)stk.pop();
            right = (Node)stk.pop();
            
            newNode = new Node(s, right, left, "0", "0");
            stk.push(newNode);
            
        } else if(s.equals("*") || s.equals("+")|| s.equals("?")){
            left = (Node)stk.pop();
            Node n = new Node(s, null, left, "0", "0");
            stk.push(n);  
        } 
        else{
            Node node = new Node(s);
            stk.push(node);
        }*/
    }
    
    
    public void inOrder(Node n){
        if (n != null) {
            inOrder(n.getLeftChild());
            System.out.print(n.getElement() +",");
            inOrder(n.getRightChild());
        }
    }
    
    public void PreOrden(Node n) {
        if (n != null) {
            System.out.println(n.getElement());
            PreOrden(n.getLeftChild());
            PreOrden(n.getRightChild());
        }
    }

    public void PostOrden(Node n) {
        if (n != null) {
            PostOrden(n.getLeftChild());
            PostOrden(n.getRightChild());
            System.out.println(n.getElement());
        }
    }
    
    
    
    
    /*METODOS DE PRUEBA*/
    
    
    //cantidad nodos hoja
    private void leafNode(Node reco) {
        if (reco != null) {
            if (reco.getLeftChild() == null && reco.getRightChild() == null) {
                cant++;
                
                //Le ingresa el anuable o no
                if (!reco.getElement().equals("epsilon")) {
                    reco.setAnulable(false);
                } else {
                    reco.setAnulable(true);
                }
                //le da numeracion
                reco.setFirst(String.valueOf(cant));
                reco.setLast(String.valueOf(cant));    
            }
            leafNode(reco.getLeftChild());
            leafNode(reco.getRightChild());
        }
    }

    public int leafNode() {
        cant = 0;
        leafNode(raiz);
        return cant;
    }

    public int retornarAltura() {
        altura = 0;
        retornarAltura(raiz, 1);
        return altura;
    }

    private void retornarAltura(Node reco, int nivel) {
        if (reco != null) {
            retornarAltura(reco.getLeftChild(), nivel + 1);
            if (nivel > altura) {
                altura = nivel;
            }
            retornarAltura(reco.getRightChild(), nivel + 1);
        }
    }
    
    
    
    
    
    //altura arbol
    String[] niveles;


    public void imprimirNivel() {
        niveles = new String[altura + 1];

        imprimirNivel(raiz, 0);
        for (int i = 0; i < niveles.length; i++) {
            System.out.println(niveles[i] + " En nivel: " + i);
        }
    }

    private void imprimirNivel(Node pivote, int nivel2) {
        if (pivote != null) {
            niveles[nivel2] = pivote.getElement() + ", " + ((niveles[nivel2] != null) ? niveles[nivel2] : "");
            imprimirNivel(pivote.getRightChild(), nivel2 + 1);
            imprimirNivel(pivote.getLeftChild(), nivel2 + 1);
        }
    }

    public void imprimirAlturaDeCadaNodo() {
        imprimirAlturaDeCadaNodo(raiz, 1);

    }

    private void imprimirAlturaDeCadaNodo(Node reco, int nivel) {
        if (reco != null) {
            System.out.println("Nodo contiene: " + reco.getElement() + " y su altura es: " + nivel);
            imprimirAlturaDeCadaNodo(reco.getLeftChild(), nivel + 1);
            imprimirAlturaDeCadaNodo(reco.getRightChild(), nivel + 1);
        }
    }
    
     public int cantidadNodos() {
        cant = 0;
        cantidad(raiz);
        return cant;
    }

    private void cantidad(Node reco) {
        if (reco != null) {
            cant++;
            cantidad(reco.getLeftChild());
            cantidad(reco.getRightChild());
        }
    }
    
    
}
