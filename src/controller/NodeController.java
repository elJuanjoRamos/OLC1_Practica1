
package controller;

import bean.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class NodeController {

    private ArrayList<String> listAux = new ArrayList();//Lista que contendra los elementos de la expresion regular
    private ArrayList<String> list = new ArrayList();  //Lista que servira para reordenar los elementos de la expresion regular
    private ArrayList<String> elementsList = new ArrayList();  //Lista que contendra los nombres de los elementos
    private ArrayList<RowTable> followList = new ArrayList();  //Lista que servira para los siguientes
    private ArrayList<RowTable> newFollowList = new ArrayList();  //Lista que servira para la tabla de transiciones
    
    
    private Stack stk = new Stack();
    int index = 0;
    Node raiz = null;

    /*Variables nuevas de prueba*/
    int cant;
    int altura;
    String nodeName = "";
    
    /*SINGLETON*/
    public static NodeController instancia;

    public static NodeController getInstancia() {
        if (instancia == null) {
            instancia = new NodeController();
        }
        return instancia;
    }
    /********************/
    
    public NodeController() {
    }

    //Iserta los elementos de la expresion regular a una lista;
    public void Insert(String element) {
        listAux.add(element);
    }
    
    //Inserta los nombres de los elmentos, 
    public void InsertElement(String tree, String table, String tablet, String afd){
        elementsList.add(tree);
        elementsList.add(table);
        elementsList.add(tablet);
        elementsList.add(afd);
    }

    //Limpa las listas de elementos
    public void clearList() {
        raiz = null;
        cant = 0;
        this.followList.clear();
        //this.elementsList.clear();
        this.listAux.clear();
        this.list.clear();
    }

    //Reordena los elementos de la expresion regular para el arbol
    public void Reordering(String name) {
        
        //verifica que el nombre no sea vacio
        if (name.equals("")) {
            name = "null" + index;
        }
        
        
        //Le da la vuelta a los elementos del arreglo para ordenar la expresion
        for (int i = listAux.size() - 1; i >= 0; i--) {
            list.add(listAux.get(i));
        }
        //Envia los nodos del arreglo al stack que almacenara los elementos
        for (String n : list) {
            InsertStack(n);
        }
        //Crea un nodo raiz y le concatena el estado de aceptacion 
        Node left = (Node) stk.pop();
        Node right = new Node("#");
        raiz = new Node(".", right, left, false);

        //manda la raiz a numerar sus nodos
        leafNode(raiz);
        setDesition(raiz);
        setRootAntNext(raiz);
        
        //Envia la raiz para obtener los siguientes
        elementsOfTable(raiz);
        
        //imprime el arbol raiz
        raiz.print(name+ "Tree.jpg");
        printElement(name, 1);
        index++;

    }

    public void InsertStack(String s) {
        Node newNode = new Node();
        Node right = new Node();
        Node left = new Node();
        boolean anuable = false;

        if (s.equals("|") || s.equals(".")) {
            left = (Node) stk.pop();
            right = (Node) stk.pop();

            if (s.equals("|")) {
                if (left.isAnulable() || right.isAnulable()) {
                    anuable = true;
                }
            } else if (s.equals(".")) {
                if (left.isAnulable() && right.isAnulable()) {
                    anuable = true;
                }
            }
            newNode = new Node(s, right, left, anuable);
            anuable = false;
            stk.push(newNode);

        } else if (s.equals("*") || s.equals("+") || s.equals("?")) {
            left = (Node) stk.pop();

            if (s.equals("*") || s.equals("?")) {
                anuable = true;
            } else if (s.equals("+")) {
                if (left.isAnulable()) {
                    anuable = true;
                } else {
                    anuable = false;
                }
            } else {
                anuable = true;
            }
            Node n = new Node(s, null, left, anuable);
            anuable = false;
            stk.push(n);
        } else {
            Node node = new Node(s);
            stk.push(node);
        }
    }

    public void inOrder(Node n) {
        if (n != null) {
            inOrder(n.getLeftChild());
            System.out.print(n.getElement() + ",");
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
                reco.setIsLeaf(true);
                //le da numeracion
                reco.setFirst(String.valueOf(cant));
                reco.setLast(String.valueOf(cant));
            }
            leafNode(reco.getLeftChild());
            leafNode(reco.getRightChild());
        }
    }

    private void setDesition(Node n) {
        if (n != null) {
            if (n.getElement().equals("|")) {
                setOrAntNext(n);
            } else if ((n.getElement().equals("*") || n.getElement().equals("?") || n.getElement().equals("+")) && n.isIsLeaf() == false  ) {
                setOperatorAntNext(n);
            } else if (n.getElement().equals(".") && n.isIsLeaf() == false) {
                setDotAntNext(n);
            }
        }
    }

    //metodo que sirve para poner los primera pos y ultima pos al Or
    private void setOrAntNext(Node reco) {
        if (reco != null) {
            reco.setFirst(reco.getLeftChild().getFirst() + "," + reco.getRightChild().getFirst());
            reco.setLast(reco.getLeftChild().getLast() + "," + reco.getRightChild().getLast());
            setDesition(reco.getLeftChild());
            setDesition(reco.getRightChild());
        }
    }
    //Metodo para poner primiero y siguiente a los operadores * + ?
    private void setOperatorAntNext(Node reco) {
        if (reco != null) {
            reco.setFirst(reco.getLeftChild().getFirst());
            reco.setLast(reco.getLeftChild().getLast());
            setDesition(reco.getLeftChild());
            setDesition(reco.getRightChild());
        }
    }
    //Metodo para poner primero y siguiente al punto
    private void setDotAntNext(Node reco) {
        if (reco != null) {
            //Parte izquierda y derecha
            if (reco.getLeftChild().getElement().equals(".") 
                    || reco.getRightChild().getElement().equals(".")
                    || reco.getRightChild().getElement().contains("0") 
                    || reco.getLeftChild().getElement().contains("0")
                    || reco.getFirst().contains("0")
                    || reco.getLast().contains("0")) {
                setDesition(reco.getLeftChild());
                setDesition(reco.getRightChild());
            }  
            //set first
            if (reco.getLeftChild().isAnulable()) {
                reco.setFirst(reco.getLeftChild().getFirst() + "," + reco.getRightChild().getFirst());
            } else {
                reco.setFirst(reco.getLeftChild().getFirst());
            }

            //set last
            if (reco.getRightChild().isAnulable()) {
                reco.setLast(reco.getLeftChild().getLast() + "," + reco.getRightChild().getLast());
            } else {
                reco.setLast(reco.getRightChild().getLast());
            }

            setDesition(reco.getLeftChild());
            setDesition(reco.getRightChild());
        }
    }
    //Le asigna los primero y ultimo a la raiz
    public void setRootAntNext(Node reco) {
        if (reco.getLeftChild().isAnulable()) {
                reco.setFirst(reco.getLeftChild().getFirst() + "," + reco.getRightChild().getFirst());
            } else {
                reco.setFirst(reco.getLeftChild().getFirst());
            }
        reco.setLast(reco.getRightChild().getLast());
    }
    //retorna la raiz
    public Node getRoot(){
        return raiz;
    }



    // metodo para obtener los datos con los que se construiran las tablas
    public void elementsOfTable(Node n){
        
        if (n != null) {
            
            if (n.getLeftChild() != null) {
                String[] f = n.getLeftChild().getLast().split(",");
                for (int i = 0; i < f.length; i++) {
                    
                    if (n.getElement().equals("*") || n.getElement().equals("+")) {
                        getLeafElement(raiz, f[i]);
                        getTable(nodeName, f[i], n.getLeftChild().getFirst());                 
                    
                    } else if (n.getElement().equals(".")) {
                        getLeafElement(raiz, f[i]);
                        getTable(nodeName, f[i], n.getRightChild().getFirst());
                    }
                }
            }
            
            elementsOfTable(n.getLeftChild());
            elementsOfTable(n.getRightChild());
        }
 
    }
    // Reestructurar la tabla
    public void getTable(String element, String noleaf, String follow){

        int contador = 0;
        if (followList.isEmpty()) {
            followList.add(new RowTable(element, noleaf, follow));
        } else {
            for (RowTable o : followList) {
                if (o.getNoLeaf().equals(noleaf)) {
                    contador++;
                }
            }
            if (contador == 0) {
                followList.add(new RowTable(element, noleaf, follow));
            }else {
                for (RowTable o : followList) {
                    if (o.getNoLeaf().equals(noleaf)) {
                        o.setFollow(follow + "," +o.getFollow());
                    }
                }
            }
        }
        //METODO BURBUJA PARA ORDENAR LOS SIGUIENTES DE MENOR A MAYOR
        int n = followList.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (Integer.parseInt(((RowTable)followList.get(j)).getNoLeaf()) > Integer.parseInt(((RowTable)followList.get(j+1)).getNoLeaf()))
                {
                    RowTable swap = followList.get(j);
                    followList.set(j, followList.get(j + 1));
                    followList.set(j+1, swap);
                    
                }
            }  
        }
    }

    
    
    
    
    
    
    /***************METODOS PARA LA TABLA DE TRANSICIONES*******************/
    
    //metodo para armar la nueva tabla de siguientes, elimina los elementos repetidos
    public void NewFollow(){
        
        for (int i = 0; i < followList.size(); i++) {
            RowTable r = (RowTable)followList.get(i);
            for (int j = i+1; j < followList.size(); j++) {
                RowTable r2 = (RowTable)followList.get(j);
                if (r.getNoLeaf().equals(r2.getNoLeaf())) {
                    r.setFollow(r.getFollow() + "," + r2.getFollow());
                    newFollowList.add(r);
                    break;
                }
                
            }
        }
        
        for (int i = 0; i < newFollowList.size(); i++) {
            RowTable r = (RowTable)newFollowList.get(i);
            System.out.println(r.getLexema() + " " + r.getNoLeaf() + " " + r.getFollow());
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*Metodo para imprimir los elementos*/
    
    public void printElement(String path, int element) {
        FileWriter file = null;
        PrintWriter writer;
        String imgName = "";
        String fileName = "";
        try
        {
            //Se va a imprimir tabla siguiente
            if (element == 1) {
                file = new FileWriter("table.dot");
                imgName = path+"Table.jpg";
                fileName = "table";
            //Se va a imprimir tabla de transicion
            } else if(element == 2){
                file = new FileWriter("tabletransition.dot");
                imgName = path+"Transition.jpg";
                fileName = "transition";
            } else if(element == 3){
                file = new FileWriter("afd.dot");
                imgName = path+"Afd.jpg";
                fileName = "afd";
            }
            writer = new PrintWriter(file);
            writer.print(getCodeGraphviz(path, element));
        } 
        catch (Exception e){
            System.err.println("Error al escribir el archivo .dot");
        }finally{
           try {
                if (null != file)
                    file.close();
           }catch (Exception e2){
               System.err.println("Error al cerrar el archivo .dot");
           } 
        }                        
        try{
          Runtime rt = Runtime.getRuntime();
          //String command = "dot -Tpng \"" + path + "\\" + "aux_grafico" + ".dot\"  -o \"" + path + "\\" + nombre + ".png\"   ";
          rt.exec( "dot -Tjpg -o "+imgName+" " + fileName +".dot");
          //rt.exec(command);
          //rt.exec( "dot -Tjpg -o "+path+" aux_grafico.dot");
          Thread.sleep(500);
        } catch (Exception ex) {
            System.out.println(ex);
            System.err.println("Error al generar la imagen para el archivo aux_grafico.dot");
        }         
    }
    
    private String getCodeGraphviz(String path, int element) {
        
        String texto = "";
        
        //Se va a imprimir tabla
        if (element == 1) {
            for (RowTable rowTable : followList) {
                String str = rowTable.getLexema().replace('"', ' ');
                texto = texto + "{<here> "+rowTable.getNoLeaf() +"|"+rowTable.getFollow()+"}|";         
            }

            RowTable r = followList.get(followList.size()-1);
            int a = Integer.parseInt(r.getNoLeaf())+1 ;

            texto = texto + "{<here> "+ a +"|---}|";   
        }
         
        return "digraph grafica{\n" +
               "rankdir=TB;\n" +
               "node [shape = record, style=filled, fillcolor=white];\n"
                +
                
                "nodo"+index+" [ label =\""
                + "{ Follow Table "+ path+ "|"+texto+"}"
                + "\"];\n}\n";
    }
     

    public void getLeafElement(Node reco, String node){
        if (reco != null) {
            if (reco.isIsLeaf()) {
                if (reco.getFirst().equals(node) && reco.getLast().equals(node)) {
                    nodeName = reco.getElement();    
                }
               } else {
                getLeafElement(reco.getLeftChild(), node);
                getLeafElement(reco.getRightChild(), node);   
            }
            
        }
    }

    
    
    /*********METODOS PARA LA PARTE VISUAL**********/
    //Retorna la lista de elementos guardados, (arboles, afds, tablas)
    public ArrayList getElements(){
        return this.elementsList;
    }

}
