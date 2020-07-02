package Arbol;

import java.io.*;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class Graficador {

    public static Logger LOGGER = Logger.getLogger(Graficador.class.getName());

    private BufferedWriter br;
    private ConstructorAssembler constructorAssembler = new ConstructorAssembler();

    private static final String INTERMEDIA_GRAPHVIZ = "intermedia-graphviz.txt";
    private static final String INTERMEDIA = "intermedia.txt";

    public void graficarArbol(Nodo arbol) {
        try {
            escribirArbolGraphviz(arbol);
            escribirArbolIntermedia(arbol);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void escribirArbolIntermedia(Nodo arbol) throws IOException {
        try {
            setupWriter(INTERMEDIA);

            Nodo nodo = new Nodo(arbol);
            escribirIntermedia(nodo);
            String assembler = constructorAssembler.getAssembler();
            this.br.write(assembler);

        } catch (IOException e) {
            LOGGER.severe("Ocurrio un error al guardar el archivo de intermedia");
            e.printStackTrace();
        } finally {
            cleanWriter();
        }
    }

    private void escribirIntermedia(Nodo nodo) {
        if (nodo == null)
            return;

        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();

        constructorAssembler.escribirAssemblerAntesDeEscribirHijos(nodo);

        if(izq != null && !izq.esHoja()){
            escribirIntermedia(izq);
        }

        constructorAssembler.escribirAssemblerAntesDeEscribirHijoDer(nodo);

        if (der != null && !der.esHoja())
            escribirIntermedia(der);
        String datoSubarbol = constructorAssembler.escribirAssembler(nodo);
    }


    private void escribirArbolGraphviz(Nodo arbol) throws IOException {
        try {
            setupWriter(INTERMEDIA_GRAPHVIZ);

            String labels = escribirLabels(arbol);
            String relaciones = escribirRelaciones(arbol);

            String codigo = "digraph \"arbol\" {\n" +
                    "\tlabel=\"arbol\"\n";
            codigo += labels;
            codigo += relaciones;
            codigo += "}";

            escribirUrl(codigo);

            this.br.write(codigo);

        } catch (Exception e) {
            LOGGER.severe("Ocurrio un error al guardar el archivo  de graphviz");
            e.printStackTrace();
        } finally {
            cleanWriter();
        }
    }

    private String escribirLabels(Nodo nodo) {
        if (nodo == null)
            return "";

        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();

        String labelActual = String.format("\tn%s [label=\"%s\"] ;\n", nodo.getId(), escapear(nodo.getDato()));
        String labelIzq = izq != null ? " " + escribirLabels(izq) : "";
        String labelDer = der != null ? " " + escribirLabels(der) : "";

        return labelActual + labelIzq + labelDer;
    }

    private String escribirRelaciones(Nodo nodo) {
        if (nodo == null)
            return "";

        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();

        if (izq == null && der == null)
            return "";

        String relacionIzq = izq != null ? String.format("\tn%s -> n%s\n", nodo.getId(), nodo.getIzq().getId()) + escribirRelaciones(izq) : "";
        String relacionDer = der != null ? String.format("\tn%s -> n%s\n", nodo.getId(), nodo.getDer().getId()) + escribirRelaciones(der) : "";

        return relacionIzq + relacionDer;
    }

    private void escribirUrl(String codigo) throws IOException {
        try {
            String s = "#Probar en https://dreampuf.github.io/GraphvizOnline/#" + URLEncoder.encode(
                    codigo.replaceAll(" ", "@space@"), "UTF-8")
                    .replaceAll("%40space%40", "%20"
                    ) + "\n\n";
            this.br.write(s);
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("Ocurrio un error al generar el link");
            e.printStackTrace();
        }
    }

    private String escapear(String s) {
        return s.replace("\"", "\\\"");
    }


    private void setupWriter(String filename) throws IOException {
        this.br = new BufferedWriter(new FileWriter(filename));
    }

    private void cleanWriter() throws IOException {
        this.br.close();
        this.br = null;
    }

}
