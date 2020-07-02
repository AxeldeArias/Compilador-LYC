package Arbol;

import Tabla.Simbolo;
import Tabla.TablaDeSimbolos;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class ConstructorAssembler {

    private static Logger LOGGER = Logger.getLogger(TablaDeSimbolos.class.getName());
    private static Integer CANT_AUXILIARES = 0;
    private String assembler = "";

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

    public String generarData(List<Simbolo> listaDeSimbolos) {
        String variablesDeclaradas = listaDeSimbolos.stream()
                .map(this::formatTs)
                .reduce("", (subtotal, linea) -> subtotal + linea);

        String variablesAuxiliares = IntStream.range(1, CANT_AUXILIARES + 1)
                .mapToObj(i -> String.format("\t@aux%d\tdd\t?\n", i))
                .reduce("", (subtotal, linea) -> subtotal + linea);

        return variablesDeclaradas + "\n" + variablesAuxiliares + "\n";
    }

    private String formatTs(Simbolo linea) {
        String valor = linea.getValor() != null ? Double.valueOf(linea.getValor()).toString() : "?";
        //String nombre = (linea.getNombre().matches("_[0-9]+") ? "_" : "") + linea.getNombre(); //TODO Ver caso de CONST_STR
        String nombre = linea.getNombre();
        return String.format("\t%s\tdd\t%s\n", nombre, valor);
    }

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

    public String generarAssembler(Nodo nodo) {

        switch (nodo.getDato()) {
            case "+":
                return generarSubarbolOperacion(nodo, Collections.singletonList("FADD"));
            case "-":
                return generarSubarbolOperacion(nodo, Collections.singletonList("FSUB"));
            case "*":
                return generarSubarbolOperacion(nodo, Collections.singletonList("FMUL"));
            case "/":
                return generarSubarbolOperacion(nodo, asList("FXCH", "FDIV"));
            case ":=":
                generarSubarbolAsignacion(nodo);
                return null;
            case "GET":
                generarSubarbolGet(nodo);
                return null;
            case "DISPLAY":
                generarSubarbolDisplay(nodo);
                return null;
            default:
                return null;
        }
    }

    private String generarSubarbolOperacion(Nodo nodo, List<String> commands) {

        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();

        String aux = incrementarCantAuxiliares();

        String asmFields = formatAssembler("FLD", izq.getDato()) + formatAssembler("FLD", der.getDato());
        String asmCommands = commands.stream().reduce("", (subtotal, command) -> subtotal + formatAssembler(command));
        String asmStore = formatAssembler("FSTP", aux);
        String asmFree = formatAssembler("FFREE");

        String subarbolActual = asmFields + asmCommands + asmStore + asmFree + "\n";

        assembler += subarbolActual;

        return aux;
    }

    private void generarSubarbolGet(Nodo nodo){
        Nodo izq = nodo.getIzq();

        String asmFields = formatAssembler("GetFloat", izq.getDato());

        String subarbolActual = asmFields + "\n";

        assembler += subarbolActual;
    }

    private void generarSubarbolDisplay(Nodo nodo){
        Nodo izq = nodo.getIzq();

        String asmFields = formatAssembler("DisplayFloat", izq.getDato()+",1");

        String subarbolActual = asmFields + "\n";

        assembler += subarbolActual;
    }

    private void generarSubarbolAsignacion(Nodo nodo) {
        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();

        String asmFields = formatAssembler("FLD", der.getDato());
        String asmStore = formatAssembler("FSTP", izq.getDato());

        String subarbolActual = asmFields + asmStore + "\n";

        assembler += subarbolActual;
    }

    private String incrementarCantAuxiliares() {
        CANT_AUXILIARES++;
        return "@aux" + CANT_AUXILIARES;
    }

    private String formatAssembler(String command, String value) {
        String _value = (value.matches("[0-9]+") ? "_" : "") + value; //TODO Ver caso de CONST_STR
        return String.format("\t%s %s\n", command, _value);
    }

    private String formatAssembler(String command) {
        return String.format("\t%s\n", command);
    }

    public String getAssembler() {
        return assembler;
    }
}
