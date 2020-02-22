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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class MainController implements Initializable {
    
    /*VARIABLES*/
    private ObservableList<String> opciones;
    private ObservableList<String> elementOptions;
    @FXML ComboBox combo;
    @FXML ComboBox comboElemento;
    @FXML TextArea arearegular;
    @FXML ImageView imagenView;
    
    String fileName = "";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboElemento.setVisible(false);
        String[] array = {"Tree", "Table", "Transition", "Afd"};
        opciones = FXCollections.observableArrayList(array);
        combo.setItems(opciones);
        imagenView.setImage(null);
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
         fileName = archivo.getName();
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
                            NodeController.getInstancia().InsertElement(texto+"Tree", texto+"Table", texto+"Transition", texto+"Afd");
                            ExpresionTreeController.getInstancia().Insert(texto, NodeController.getInstancia().getRoot());
                            NodeController.getInstancia().clearList();
                            i = j;
                            break;
                        }
                    }
                }
            }
        }
        
    }
    
    @FXML
    private void on_change(ActionEvent event){
        comboElemento.setVisible(true);
        String element = combo.getValue().toString();
        
        ArrayList<String> array = new ArrayList();
        
        for (int i = 0; i < NodeController.getInstancia().getElements().size(); i++) {
            String str = (String)(NodeController.getInstancia().getElements()).get(i);
            if (str.contains(element)) {
                array.add(str);
            }
        }
        comboElemento.getItems().removeAll(comboElemento.getInsets());
        elementOptions = FXCollections.observableArrayList(array);
        comboElemento.setItems(elementOptions);
        
        
    }
    
    
    @FXML
    private void print_img(ActionEvent event) throws URISyntaxException{
        if (comboElemento.getValue().toString() != "") {
            File file = new File( comboElemento.getValue().toString() + ".jpg");
            if (file.exists()) {
                if (file != null) {
                    String path = "file:///" + file.getAbsoluteFile().getParent()+"\\"+comboElemento.getValue().toString() + ".jpg";
                    Image i = new Image(path);
                    imagenView.setImage(i);
                }
            } else {
                System.out.println("no encontrado");
            }
        }
    }
    
    @FXML
    private void save_file(ActionEvent event) throws IOException {
        
       /* BufferedWriter output = null;
        try {
            File file = new File(fileName);
            output = new BufferedWriter(new FileWriter(file));
            output.write(arearegular.getText());
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
          if ( output != null ) {
            output.close();
          }
        }*/
       Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
 
            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("er files (*.er)", "*.er");
            fileChooser.getExtensionFilters().add(extFilter);
 
            //Show save file dialog
            File file = fileChooser.showSaveDialog(stage);
 
            if (file != null) {
                saveTextToFile(arearegular.getText(), file);
        }
    }
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            //Logger.getLogger(SaveFileWithFileChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    @FXML
    private void print_tokens(ActionEvent event) throws IOException {
        
        TokenController.getInstancia().PrintToken(fileName);
        
    }
    @FXML
    private void print_errors(ActionEvent event) throws IOException {
        TokenController.getInstancia().PrintError(fileName);
    }
    
}
