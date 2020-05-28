import Analizadores.AnalizadorLexico;
import Analizadores.AnalizadorSintactico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public class Main {

    public static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        File archivo = new File("programa.txt");
        FileReader fr;

        try {
            fr = new FileReader(archivo);
            AnalizadorLexico lexico = new AnalizadorLexico(new BufferedReader(fr));
            AnalizadorSintactico sintactico = new AnalizadorSintactico(lexico);
            sintactico.parse();
        } catch (FileNotFoundException ex) {
            LOGGER.severe("El archivo no existe");
        } catch (Exception ex) {
            LOGGER.severe("Hubo un error");
        }
    }
}
