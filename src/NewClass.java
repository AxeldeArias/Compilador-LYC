
import Analizadores.Analizador_Lexico;
import Analizadores.analisis_sintactico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xdear
 */
public class NewClass {
  public static void main(String[] args) {
        File archivo = new File ("programa.txt");
      
        FileReader fr = null;
        try {
            fr = new FileReader (archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Analizador_Lexico lexico = new Analizador_Lexico(new BufferedReader( fr));
        analisis_sintactico sintactico = new analisis_sintactico(lexico);
  
            
        try {
            sintactico.parse();
        } catch (Exception ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
}
