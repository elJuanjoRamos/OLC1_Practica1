
package controller;

import bean.Token;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class TokenController {
    public static TokenController instancia;
    private ArrayList<Token> arrayListTokens = new ArrayList();
    private ArrayList<Token> arrayListErrors = new ArrayList();
    private int idToken = 1;
    private int idTokenError = 1;
    
    public static TokenController getInstancia() {
        if(instancia == null) {
            instancia = new TokenController();
        }
        return instancia;
    }

    public TokenController() {
    }
    
    public void agregarToken(int fila, int columna, String lexema, String descripcion)
    {
        Token token = new Token(idToken, lexema, descripcion, columna, fila);
        arrayListTokens.add(token);
        idToken++;
    }

    public void agregarError(int fila, int columna, String lexema, String descripcion)
    {
        Token token = new Token(idTokenError, lexema, descripcion, columna, fila);
        arrayListErrors.add(token);
        idTokenError++;
    }

    public void limpiarArrayList()
    {
        arrayListErrors.clear();
        arrayListTokens.clear();
        idToken = 1;
        idTokenError = 1;
    }

    public ArrayList<Token> getArrayListErrors() {
        return arrayListErrors;
    }

    public ArrayList<Token> getArrayListTokens() {
        return arrayListTokens;
    }
    

    public void PrintToken(String name) throws IOException
        {
            String cadena = "";
            String contenido = "";

            for (int i = 0; i < arrayListTokens.size(); i++)
            {
                Token tok = (Token)arrayListTokens.get(i);

                contenido = "<tr>\n" +
                    "     <th scope=\"row\">" + i + "</th>\n" +
                    "     <td>" + tok.getLexema() + "</td>\n" +
                    "     <td>" + tok.getDescripcion() + "</td>\n" +
                    "     <td>" + tok.getFila() + "</td>\n" +
                    "     <td>" + tok.getColumna() + "</td>\n" +
                    "</tr>";
                cadena = cadena + contenido;

            }
            String cadena2 = "<th scope =\"col\">No</th>\n" +
            "          <th scope=\"col\">Lexema</th>\n" +
            "          <th scope=\"col\">Token</th>\n" +
            "          <th scope=\"col\">Fila</th>\n" +
            "          <th scope=\"col\">Columna</th>\n";
            armarHTML(cadena, cadena2, "Tokens " + name);

        }

        public void PrintVoid(String name) throws IOException
        {
            String cadena = "";

            String cadena2 = "<th scope =\"col\">No</th>\n" +
             "          <th scope=\"col\">Lexema</th>\n" +
             "          <th scope=\"col\">Token</th>\n" +
             "          <th scope=\"col\">Fila</th>\n" +
             "          <th scope=\"col\">Columna</th>\n";
            armarHTML(cadena, cadena2, "Tokens " + name);

        }

        public void PrintError(String name) throws IOException
        {
            String cadena = "";
            String contenido = "";

            for (int i = 0; i < arrayListErrors.size(); i++)
            {
                Token tok = (Token)arrayListErrors.get(i);

                contenido = "<tr>\n" +
                    "     <th scope=\"row\">" + i + "</th>\n" +
                    "     <td>" + tok.getLexema() + "</td>\n" +
                    "     <td>" + tok.getDescripcion() + "</td>\n" +
                    "     <td>" + tok.getFila() + "</td>\n" +
                    "     <td>" + tok.getColumna() + "</td>\n" +
                    "</tr>";
                cadena = cadena + contenido;

            }
            String cadena2 = "<th scope =\"col\">No</th>\n" +
            "          <th scope=\"col\">Lexema</th>\n" +
            "          <th scope=\"col\">Token</th>\n" +
            "          <th scope=\"col\">Fila</th>\n"+
            "          <th scope=\"col\">Columna</th>\n";
            armarHTML(cadena, cadena2, "Errores " + name);

        }



        public void armarHTML(String cadena, String cadena2, String titulo) throws IOException
        {

            String head = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset='utf-8'>\n" +
            "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>\n" +
            "    <title>"+titulo +"' Repot</title>\n" +
            "    <meta name='viewport' content='width=device-width, initial-scale=1'>\n" +
            "    <link rel='stylesheet' type='text/css' media='screen' href='main.css'>\n" +
            "    <script src='main.js'></script>\n" +
            "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
            "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">\n" +
            "</head>" +
            "<body>\n" +
            "  <nav class=\"navbar navbar-light bg-light\">\n" +
            "    <span class=\"navbar-brand mb-0 h1\">Compiladores 1</span>\n" +
            "  </nav>";
            String body1 = "<div class=\"container\">\n" +
          "    <div class=\"jumbotron jumbotron-fluid\">\n" +
          "      <div class=\"container\">\n" +
          "        <h1 class=\"display-4\">" + titulo + "</h1>\n" +
          "        <p class=\"lead\">List of tokens detected by the analyzer</p>\n" +
          "      </div>\n" +
          "    </div>\n" +
          "    <div class=\"row\">\n" +
          "    <table id=\"data\"  cellspacing=\"0\" style=\"width: 100 %\" class=\"table table-striped table-bordered table-sm\">\n" +
          "      <thead class=\"thead-dark\">\n" +
          "        <tr>\n" +
                    cadena2 +
          "        </tr>\n" +
          "      </thead>" +
          "<tbody>";


            String body2 = "</tbody>\n" +
           "    </table>\n" +
           "</div>\n" +
           "  </div>";

            String script =
                "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" ></script>\n" +
                "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
                "  <script src=\"https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js\"></script>\n" +
                "  <script src=\"https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap4.min.js\" ></script>\n" +
                "<script>" +
                "$(document).ready(function () { " +
                 "$('#data').DataTable(" +

                 "{ \"aLengthMenu\" " + ":" + " [[5, 10, 25, -1], [5, 10, 25, \"All\"]], \"iDisplayLength\" : 5" +
                 "}" +
                 ");" +
                 "}" +
                 "); " +
               "</script>";

            String html;

            html = head + body1 + cadena + body2 +
            script +
            "</body>" +
            "</html>";


            /*creando archivo html*/
            //File.WriteAllText("Reporte "+titulo+".html", html);
            BufferedWriter output = null;
            try {
                File file = new File("Reporte "+titulo+".html");
                output = new BufferedWriter(new FileWriter(file));
                output.write(html);
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
              if ( output != null ) {
                output.close();
              }
            }
            
            //System.Diagnostics.Process.Start("Reporte "+titulo+".html");

        }


    
}
