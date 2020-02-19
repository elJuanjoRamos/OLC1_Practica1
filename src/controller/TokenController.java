/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Token;
import java.util.ArrayList;

/**
 *
 * @author josem
 */
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
        
}
