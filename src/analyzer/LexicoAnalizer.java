/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer;

import controller.TokenController;

public class LexicoAnalizer {
    String auxiliar = "";
    
    private LexicoAnalizer() {
    }
    
    public static LexicoAnalizer getInstance() {
        return LexicoAnalizerHolder.INSTANCE;
    }
    
    private static class LexicoAnalizerHolder {

        private static final LexicoAnalizer INSTANCE = new LexicoAnalizer();
    }
    
    public void Analizador(String entrada) {
        int estado = 0;
        int columna = 0;
        int fila = 1;;
        
        for (int i = 0; i < entrada.length(); i++){
            char letra = entrada.charAt(i);
            columna++;
            
            //System.err.println(letra);
            switch(estado) {
                case 0:
                    //SI VIENE LETRA
                    //System.out.println("ESTADO 0");
                    if(Character.isLetter(letra))
                    {
                        estado = 1;
                        auxiliar += letra;
                    //SI VIENE SALTO DE LINEA
                    } else if (letra == '\n')
                    {
                        estado = 0;
                        columna = 0;//COLUMNA 0
                        fila++; //FILA INCREMENTA
                    //VERIFICA ESPACIOS EN BLANCO
                    } else if (Character.isSpaceChar(letra))
                    {
                        //columna++;
                        estado = 0;
                    //VERIFICA SI VIENE NUMERO
                    } else if (Character.isDigit(letra))
                    {
                        estado = 2;
                        auxiliar += letra;
                    }
                    
                    //VERIFICA SI ES SIMBOLO
                    else if (Character.isDefined(letra))
                    {
                       switch(letra) {
                            case '>':
                                //System.out.println("ENTRA A >");
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Mayor");
                            break;
                            case '~':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Virgulilla");
                            break;
                            case '+':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Suma");
                            break;
                            case '*':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Multiplicacion");
                            break;
                            case '.':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Punto");
                            break;
                            case ',':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Coma");
                            break;
                            case ':':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_DosPuntos");
                            break;
                            case ';':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_PuntoComa");
                            break;
                            case '|':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Pleca");
                            break;
                            case '{':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_LlaveIzquierda");
                            break;
                            case '}':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_LlaveDerecha");
                            break;
                            case '-':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Resta");
                            break;
                            case '%':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Porcentaje");
                            break;
                            case '/':
                                estado = 3;
                                auxiliar += letra;
                            break;
                            case '<':
                                estado = 5;
                                auxiliar += letra;
                                System.err.println("ESTADO 5");
                            break;
                            case '"':
                                estado = 8;
                                auxiliar += letra;
                            break;
                            
                            /*SIMBOLOS ASCII*/
                            case '!':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '#':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '$':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '&':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '(':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case ')':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '=':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '?':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '@':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '[':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case ']':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '^':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            case '_':
                                TokenController.getInstancia().agregarToken(fila, columna-1, String.valueOf(letra), "TK_Simbolo");
                            break;
                            default:
                                TokenController.getInstancia().agregarError(fila, columna, String.valueOf(letra), "TD_Desconocido");
                            break;
                        }            
                    } else {
                        System.out.println("entro 2");
                        TokenController.getInstancia().agregarError(fila, columna, String.valueOf(letra), "TD_Desconocido");
                        
                    }
                break;
                case 1:
                    if (Character.isLetterOrDigit(letra) || letra == '_')
                    {
                        auxiliar += letra;
                        estado = 1;
                    } else {
                        if (auxiliar.equals("CONJ"))
                        {
                            TokenController.getInstancia().agregarToken(fila, (columna - auxiliar.length()-1), auxiliar, "PR_" + auxiliar);
                        } else {
                            TokenController.getInstancia().agregarToken(fila, (columna - auxiliar.length()-1), auxiliar, "Identificador");
                        }
                        auxiliar = "";
                        i--;
                        columna--;
                        estado = 0;
                    }
                break;
                case 2:
                    if (Character.isDigit(letra))
                    {
                        auxiliar += letra;
                        estado = 2;
                    }
                    else
                    {
                        TokenController.getInstancia().agregarToken(fila, columna, auxiliar, "Digito");
                        auxiliar = "";
                        i--;
                        columna--;
                        estado = 0;
                    }
                break;
                case 3:
                    if(letra=='/') {
                        estado = 4;
                        auxiliar += letra;
                    }
                break;
                case 4:
                    if (letra != '\n')
                    {
                        auxiliar += letra;
                        estado = 4;
                    }
                    else
                    {
                        TokenController.getInstancia().agregarToken(fila, 0, auxiliar, "ComentarioLinea");
                        fila++; columna = 0;
                        estado = 0;
                        auxiliar = "";
                    }
                break;
                case 5:
//                    System.err.println(letra);
//                    System.err.println(letra=='!');

                    if(letra=='!') {
                        estado = 6;
                        auxiliar += letra;
                        /*System.err.println("ESTADO 6");
                        System.err.println(auxiliar);*/
                    }
                break;
                case 6:
                    if (letra != '!')
                    {
                        if (letra == '\n') { fila++; columna = 0; }
                        auxiliar += letra;
                        estado = 6;
                        /*System.err.println("ESTADO 5");
                        System.err.println(auxiliar);*/
                    } else {
                        auxiliar += letra;
                        estado = 7;
                        /*System.err.println("ESTADO 7");
                        System.err.println(auxiliar);*/
                    }
                break;
                case 7: 
                    if (letra == '>')
                    {
                        auxiliar += letra;
//                        System.err.println("ESTADO 7");
//                        System.err.println(auxiliar);
                        TokenController.getInstancia().agregarToken(fila, columna, auxiliar, "ComentarioMultilinea");
                        //fila++; columna = 0;
                        estado = 0;
                        auxiliar = "";
                    }                    
                break;
                case 8:
                    if (letra != '"')
                    {
                        if (letra == '\n') { fila++; columna = 0; }
                        auxiliar += letra;
                        estado = 8;
                    }
                    else
                    {
                        estado = 9;
                        auxiliar += letra;
                        i--; columna--;
                    }
                break;
                case 9:
                    if (letra == '"')
                    {
                        TokenController.getInstancia().agregarToken(fila, (columna - auxiliar.length()), auxiliar, "Cadena");
                        estado = 0;
                        auxiliar = "";
                    }
                break;
                default:
                    System.out.println("entro 2");
                    TokenController.getInstancia().agregarError(fila, columna, String.valueOf(letra), "TD_Desconocido");    
                    break;
                
            }
        }
        
    }
    
    
    
    
}
