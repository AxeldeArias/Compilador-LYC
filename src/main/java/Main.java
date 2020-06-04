import Analizadores.AnalizadorLexico;
import Analizadores.AnalizadorSintactico;
import Arbol.Hoja;
import Arbol.Nodo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public class Main {

    public static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        File archivo = new File("programa.txt");
        String basepath = ".\\src\\resources";
//        File archivo = new File(basepath + "/test-ExpresionLogica1");

        FileReader fr;

        try {
            fr = new FileReader(archivo);
            AnalizadorLexico lexico = new AnalizadorLexico(new BufferedReader(fr));
            AnalizadorSintactico sintactico = new AnalizadorSintactico(lexico);
            sintactico.parse();
        } catch (FileNotFoundException e) {
            LOGGER.severe("El archivo no existe");
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.severe("Hubo un error");
            e.printStackTrace();
        }

 /*       Nodo izq = new Nodo("<", new Hoja("a"), new Hoja("d"));
        Nodo der = new Nodo(">", new Hoja("5"), new Hoja("c"));
        Nodo raiz = new Nodo("&&", izq, der);

        der = new Nodo("<", new Hoja("b"), new Hoja("c"));
        raiz = new Nodo("||", raiz, der);

        raiz.recorrer();*/
    }
}
