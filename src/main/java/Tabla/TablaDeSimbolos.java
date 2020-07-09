package Tabla;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class TablaDeSimbolos {
    private TipoDato tipoDato;
    public static final String TABLA_SIMBOLOS_ARCHIVO = "ts.txt";
    private Integer nro_const_string = 0;
    private static Logger LOGGER = Logger.getLogger(TablaDeSimbolos.class.getName());

    private List<Simbolo> listaDeSimbolos;

    public TablaDeSimbolos() {
        this.listaDeSimbolos = new LinkedList<>();
    }

    public String agregarEnTabla(String nombre, TipoDato tipo, String valor, Integer longitud) {
        String nombrePorTipo = nombre;
        if(tipo.equals(TipoDato.T_STRING) && nombre.equals("")){
            nombrePorTipo = "@const_string_" + nro_const_string++;
        }

        if (!chequearEnTabla(nombre)) {
            listaDeSimbolos.add(new Simbolo(nombrePorTipo, tipo, valor, longitud));
        }
        return nombrePorTipo;
    }

    public String agregarEnTabla(Integer nombre, TipoDato tipo, String valor, Integer longitud) {
         String nombrePorTipo = "_" + nombre;
         return agregarEnTabla(nombrePorTipo, tipo, valor, longitud);
    }

    public Boolean chequearEnTabla(String nombre) {
        return listaDeSimbolos.stream().anyMatch(simbolo -> simbolo.getNombre().equals(nombre));
    }

    public void recorrerTabla() {
        listaDeSimbolos.forEach(System.out::println);
    }

    public void guardarTabla() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(TABLA_SIMBOLOS_ARCHIVO))) {

            br.write(String.format("%-50s|%-30s|%-30s|%-30s\n", "NOMBRE", "TIPODATO", "VALOR", "LONGITUD"));
            listaDeSimbolos.forEach(simbolo -> {
                try {
                    br.write(simbolo.toString() + "\n");
                } catch (IOException e) {
                    LOGGER.severe("Ocurrio un error al guardar los simbolos");
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            LOGGER.severe("Ocurrio un error al guardar el archivo");
            e.printStackTrace();
        }
    }

    public List<Simbolo> getListaDeSimbolos() {
        return listaDeSimbolos;
    }
}
