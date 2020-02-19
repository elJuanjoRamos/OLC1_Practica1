/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Node {
    String element;
    int index;
    Node rightChild;
    Node leftChild;
    private static int correlative=1;
    
    public Node() {
    }

    public Node(String element, Node rightChild, Node leftChild) {
        this.element = element;
        this.index = correlative++;
        this.rightChild = rightChild;
        this.leftChild = leftChild;
    }
    public Node(String element) {
        this.element = element;
        this.index = correlative++;
        this.rightChild = null;
        this.leftChild = null;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    
    
    
    
    public void print(String path) {
        FileWriter file = null;
        PrintWriter writer;
        try
        {
            file = new FileWriter("aux_grafico.dot");
            writer = new PrintWriter(file);
            writer.print(getCodeGraphviz());
        } 
        catch (Exception e){
            System.err.println("Error al escribir el archivo aux_grafico.dot");
        }finally{
           try {
                if (null != file)
                    file.close();
           }catch (Exception e2){
               System.err.println("Error al cerrar el archivo aux_grafico.dot");
           } 
        }                        
        try{
          Runtime rt = Runtime.getRuntime();
          //String command = "dot -Tpng \"" + path + "\\" + "aux_grafico" + ".dot\"  -o \"" + path + "\\" + nombre + ".png\"   ";
          rt.exec( "dot -Tjpg -o "+path+" aux_grafico.dot");
          //rt.exec(command);
          //rt.exec( "dot -Tjpg -o "+path+" aux_grafico.dot");
          Thread.sleep(500);
        } catch (Exception ex) {
            System.out.println(ex);
            System.err.println("Error al generar la imagen para el archivo aux_grafico.dot");
        }            
    }
    /**
     * Método que retorna el código que grapviz usará para generar la imagen 
     * del árbol binario de búsqueda.
     * @return 
     */
    private String getCodeGraphviz() {
        return "digraph grafica{\n" +
               "rankdir=TB;\n" +
               "node [shape = record, style=filled, fillcolor=seashell2];\n"+
                getBody()+
                "}\n";
    }
    /**
     * Genera el código interior de graphviz, este método tiene la particularidad 
     * de ser recursivo, esto porque recorrer un árbol de forma recursiva es bastante 
     * sencillo y reduce el código considerablemente. 
     * @return 
     */
    private String getBody() {
        String etiqueta;
        String str = element.replace('"', ' ');
    
        if (str.equals("|")) {
            str = "or";
        } 
    
        if(leftChild==null && rightChild==null){
            etiqueta="nodo"+index+" [ label =\""+str+"\"];\n";
        }else{
            etiqueta="nodo"+index+" [ label =\"|"+str+"|<C1>\"];\n";
        }
        if(leftChild!=null){
            etiqueta=etiqueta + leftChild.getBody() +
               "nodo"+index+":C0->nodo"+leftChild.index +"\n";
        }
        if(rightChild!=null){
            etiqueta=etiqueta + rightChild.getBody() +
               "nodo"+index+":C1->nodo"+rightChild.index+"\n";                    
        }
        return etiqueta;
    } 
    
}
