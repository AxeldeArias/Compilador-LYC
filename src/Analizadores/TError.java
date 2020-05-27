/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizadores;

/**
 *
 * @author xdear
 */
public class TError {
    String lexema,tipo,descripcion;
    int line,column;
    
    private void exit(String mensaje) {
        System.exit(0);
    }
    public TError(String lexema,int line, int column, String tipo, String descripcion){
            this.lexema = lexema;
            this.line = line;
            this.column= column;
            this.tipo = tipo;
            this.descripcion= descripcion;
    }    
}
