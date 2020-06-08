package Arbol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class Graficador {

    public static Logger LOGGER = Logger.getLogger(Graficador.class.getName());
    private String codigo;

    public void escribirEnArchivo(BufferedWriter br, String s) throws IOException {
        br.write(s);
    }

    public void graficarArbol(Nodo arbol) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter("intermedia.txt"))) {

            codigo = "digraph \"arbol\" {\n" +
                    "\tlabel=\"arbol\"\n";
            escribirLabels(arbol);
            escribirRelaciones(arbol);
            codigo += "}";
            escribirEnArchivo(br, codigo);
            escribirUrl(br);


        } catch (Exception e) {
            LOGGER.severe("Ocurrio un error al guardar el archivo");
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
            String s = "\n\n#Probar en https://dreampuf.github.io/GraphvizOnline/#" + URLEncoder.encode(
                    codigo.replaceAll(" ", "@space@"), "UTF-8")
                    .replaceAll("%40space%40", "%20"
                    );
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
