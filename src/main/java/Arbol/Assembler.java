package Arbol;
import Tabla.*;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Assembler {

    public static Logger LOGGER = Logger.getLogger(Assembler.class.getName());
    private static Integer CANT_AUXILIARES = 0;
    private BufferedWriter br;
    private static final String INTERMEDIA_GRAPHVIZ = "intermedia-graphviz.txt";
    private static final String INTERMEDIA = "Final.asm";
    public TipoDato tipoId;

    public void generarAssembler(List<Terceto> listaTercetos, TablaDeSimbolos tablaDeSimbolos) throws IOException {

        try {
            setupWriter(INTERMEDIA);
            String cuerpo = "";
            String codigo = null;
            String header = null;

            for(Terceto str : listaTercetos){
                cuerpo += traducirTercetoAAsm(str, tablaDeSimbolos);
            }

            codigo = generarCodigo(cuerpo);
            tablaDeSimbolos.guardarTabla();
            header = generarHeader(tablaDeSimbolos.getListaDeSimbolos());

            this.br.write(header);
            this.br.write(codigo);

        } catch (IOException e) {
            LOGGER.severe("Ocurrio un error al guardar el archivo de intermedia");
            e.printStackTrace();
        } finally {
            cleanWriter();
        }
    }

    private String traducirTercetoAAsm(Terceto terceto, TablaDeSimbolos tablaDeSimbolos){
        switch (terceto.getCampo1()) {
            case "=":
                return traducirAsignacionAAsm(terceto);
            case "+":
                return traducirOperacionAritmeticaAAsm(terceto, "FADD", tablaDeSimbolos);
            case "-":
                return traducirOperacionAritmeticaAAsm(terceto, "FSUB", tablaDeSimbolos);
            case "WRITE":
                return "WRITE\n";
            case "READ":
                return "READ\n";
            case "CMP":
                return "CMP\n";
            case "ETIQ":
                return "ETIQ\n";
            case "JB":
                return "JB\n";
            default:
                // cte y ids
                return traducirOperandosAAsm(terceto, tablaDeSimbolos);
        }
    }

    private String traducirOperandosAAsm(Terceto terceto,  TablaDeSimbolos tablaDeSimbolos){
        String asmFields = formatAssembler("FLD", terceto.getCampo1());
        String aux = terceto.reducirTercetoAVariableAux();
        tablaDeSimbolos.agregarEnTabla(aux, TipoDato.T_INTEGER, null, null);
        String asmStore = formatAssembler("FSTP", aux);
        return asmFields + asmStore + "\n";
    }

    private String traducirOperacionAritmeticaAAsm(Terceto terceto, String command, TablaDeSimbolos tablaDeSimbolos) {

        String asmFields = formatAssembler("FLD", terceto.getCampo2("operando")) +
                           formatAssembler("FLD", terceto.getCampo3("operando"));
        String asmCommands = formatAssembler(command);
        String aux = terceto.reducirTercetoAVariableAux();
        tablaDeSimbolos.agregarEnTabla(aux, TipoDato.T_INTEGER, null, null);
        String asmStore = formatAssembler("FSTP", aux);
        String asmFree = formatAssembler("FFREE");

        String subarbolActual = asmFields + asmCommands + asmStore + asmFree + "\n";

        return subarbolActual;

    }



    private String traducirAsignacionAAsm(Terceto terceto){
        String asmFields = formatAssembler("FLD", terceto.getCampo2("operando"));
        String asmStore = formatAssembler("FSTP", terceto.getCampo3("operando"));
        return asmFields + asmStore + "\n";
    }


     public String generarHeader(List<Simbolo> listaDeSimbolos) {
        String includes =
                "include number.asm\n" +
                        "include macros2.asm\n\n" +
                        ".MODEL LARGE ;Modelo de Memoria\n" +
                        ".386 ;Tipo de Procesador\n" +
                        ".STACK 200h ;Bytes en el Stack\n\n";
        String data =
                ".DATA\n" +
                        "%s\n";
        String dataGenerada = generarData(listaDeSimbolos);

        return includes + String.format(data, dataGenerada);
    }

    private String formatTs(Simbolo linea) {
        String valor = linea.getValor() != null ? Double.valueOf(linea.getValor()).toString() : "?";
        //String nombre = (linea.getNombre().matches("_[0-9]+") ? "_" : "") + linea.getNombre(); //TODO Ver caso de CONST_STR
        String nombre = linea.getNombre();
        return String.format("\t%s\tdd\t%s\n", nombre, valor);
    }


    public String generarData(List<Simbolo> listaDeSimbolos) {
        String variablesDeclaradas = listaDeSimbolos.stream()
                .map(this::formatTs)
                .reduce("", (subtotal, linea) -> subtotal + linea);

        String variablesAuxiliares = IntStream.range(1, CANT_AUXILIARES + 1)
                .mapToObj(i -> String.format("\t@aux%d\tdd\t?\n", i))
                .reduce("", (subtotal, linea) -> subtotal + linea);

        return variablesDeclaradas + "\n" + variablesAuxiliares + "\n"
    ;}

    public String generarCodigo(String codigoPrograma) {
        String codigo =
                ".CODE\n\n" +
                        "start:\n" +
                        "\tMOV EAX,@DATA\n" +
                        "\tMOV DS,EAX\n" +
                        "\tMOV ES,EAX\n\n" +
                        "%s\n" +
                        "\tMOV EAX, 4C00h\n" +
                        "\tINT 21h\n\n" +
                        "\tEND start";
        return String.format(codigo, codigoPrograma);
    }


    private String formatAssembler(String command) {
        return String.format("\t%s\n", command);
    }
    private String formatAssembler(String command, String value) {
        String _value = (value.matches("[0-9]+") ? "_" : "") + value; //TODO Ver caso de CONST_STR
        return String.format("\t%s %s\n", command, _value);
    }

    private String incrementarCantAuxiliares() {
        CANT_AUXILIARES++;
        return "@aux" + CANT_AUXILIARES;
    }





















    private void setupWriter(String filename) throws IOException {
        this.br = new BufferedWriter(new FileWriter(filename));
    }

    private void cleanWriter() throws IOException {
        this.br.close();
        this.br = null;
    }

}
