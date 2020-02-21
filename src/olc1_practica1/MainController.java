/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc1_practica1;

import analyzer.LexicoAnalizer;
import bean.Token;
import controller.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class MainController implements Initializable {
    
    /*VARIABLES*/
    private ObservableList<String> opciones;
    @FXML ComboBox combo;
    @FXML TextArea arearegular;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String[] array = {"Arboles", "Siguiente", "Transiciones", "Automata"};
        opciones = FXCollections.observableArrayList(array);
        combo.setItems(opciones);
    }    
    
    /*CARGA DE ARCHIVO*/
    @FXML
    private void cargar_archivo(ActionEvent event) {
         Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OLC FILES", "*.er"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            FileReader fr = null;
            BufferedReader br = null;

        try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         File archivo = selectedFile;
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String line;
         while((line=br.readLine())!=null){
             arearegular.appendText(line+ "\n");
         }
            
            
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }            
        }
        
    }
    
    @FXML
    private void analizar_entrada(ActionEvent event) {
        TokenController.getInstancia().limpiarArrayList();
        LexicoAnalizer.getInstance().Analizador(arearegular.getText());
       
        ArrayList l = TokenController.getInstancia().getArrayListTokens();
        
        for (int i = 0; i < l.size(); i++) {
            Token t = (Token)l.get(i);
            if (t.getLexema().equals(">")) {
                String texto = "";
                
                //busca el nombre de la expresion
                for (int j = i; j > 0; j--) {
                    Token a = (Token)l.get(j);
                    if (a.getDescripcion().equals("Identificador")) {
                        texto = a.getLexema();
                        break;
                    }
                }

                Token t1 = (Token)l.get(i+1); // token de inicio de la expresion
                if (t1 != null && t1.getLexema().equals(".")) {
                    //itera en la expresion y guarda los elementos
                    for (int j = i+1; j < l.size(); j++) {
                        Token t2 = (Token)l.get(j);
                        if (!t2.getLexema().equals(";")) {
                            if (!t2.getLexema().equals("{") && !t2.getLexema().equals("}")) {
                                NodeController.getInstancia().Insert(t2.getLexema());
                            }
                        } else {
                            //Llama al metodo Reordering para armar el arbol de expresion regular
                            NodeController.getInstancia().Reordering(texto);
                            ExpresionTreeController.getInstancia().Insert(texto, NodeController.getInstancia().getRoot());
                            NodeController.getInstancia().clearList();
                            i = j;
                            break;
                        }
                    }
                }
            }
        }
        //System.out.println("cantidad de hojas = "+NodeController.getInstancia().leafNode());
        
       /* System.out.println("la cantidad de nodos es "+NodeController.getInstancia().cantidadNodos());
       
        System.out.println("cantidad de hojas = "+NodeController.getInstancia().leafNode());
        
        System.out.println("la altura del arbol es = "+NodeController.getInstancia().retornarAltura());
        System.out.println(" ");
        NodeController.getInstancia().imprimirNivel();*/
        
        /*for(Token token: TokenController.getInstancia().getArrayListTokens()) {
            if (token.getDescripcion().equals("TK_Simbolo")) {
                System.out.println(token);            
            }
        }*/
        //SetController.getInstancia().assemble_Sets();
    }
    
    /*set variables*/
    

    
    
    
}
