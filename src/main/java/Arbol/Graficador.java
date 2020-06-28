package Arbol;

import java.io.*;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.logging.Logger;

public class Graficador {

    public static Logger LOGGER = Logger.getLogger(Graficador.class.getName());
    private String codigo;
    private String intermediaArbol;

    public void escribirEnArchivo(BufferedWriter br, String s) throws IOException {
        br.write(s);
    }

    public void graficarArbol(Nodo arbol) {
        escribirIntermedia(arbol);
        graficarArbolGraphviz(arbol);
    }

    private void escribirIntermedia(Nodo arbol) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter("intermedia.txt"))) {
            intermediaArbol = "";
            //magia de asembler
            //nodo.recorrerIntermedia();
            escribirEnArchivo(br, intermediaArbol);
        } catch (IOException e) {
            LOGGER.severe("Ocurrio un error al guardar el archivo de intermedia");
            e.printStackTrace();
        }
    }



    public void graficarArbolGraphviz(Nodo arbol) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter("intermedia-graphviz.txt"))) {

            codigo = "digraph \"arbol\" {\n" +
                    "\tlabel=\"arbol\"\n";
            escribirLabels(arbol);
            escribirRelaciones(arbol);
            codigo += "}";
            escribirUrl(br);
            escribirEnArchivo(br, codigo);

        } catch (Exception e) {
            LOGGER.severe("Ocurrio un error al guardar el archivo  de graphviz");
            e.printStackTrace();
        }

    }

    public void escribirLabels(Nodo nodo) {
        if (nodo == null)
            return;
        String labelActual = String.format("\tn%s [label=\"%s\"] ;\n", nodo.getId(), escapear(nodo.getDato()));
        codigo += labelActual;
        escribirLabels(nodo.getIzq());
        escribirLabels(nodo.getDer());
    }

    public void escribirRelaciones(Nodo nodo) {
        if (nodo != null) {
            if (nodo.getIzq() != null) {
                String relacionActual = String.format("\tn%s -> n%s\n", nodo.getId(), nodo.getIzq().getId());
                codigo += relacionActual;
                escribirRelaciones(nodo.getIzq());
            }
            if (nodo.getDer() != null) {
                String relacionActual = String.format("\tn%s -> n%s\n", nodo.getId(), nodo.getDer().getId());
                codigo += relacionActual;
                escribirRelaciones(nodo.getDer());
            }
        }
    }

    public void escribirUrl(BufferedWriter br) throws IOException {
        try {
            String s = "#Probar en https://dreampuf.github.io/GraphvizOnline/#" + URLEncoder.encode(
                    codigo.replaceAll(" ", "@space@"), "UTF-8")
                    .replaceAll("%40space%40", "%20"
                    ) + "\n\n";
            escribirEnArchivo(br, s);
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("Ocurrio un error al generar el link");
            e.printStackTrace();
        }
    }

    public String escapear(String s) {
        return s.replace("\"", "\\\"");
    }
}
