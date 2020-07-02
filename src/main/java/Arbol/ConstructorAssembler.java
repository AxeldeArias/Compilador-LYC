package Arbol;

import Tabla.Simbolo;
import Tabla.TablaDeSimbolos;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class ConstructorAssembler {

    private static Integer CANT_AUXILIARES = 0;
    private String assembler = "";
    private Integer NRO_ETIQUETA = 0;
    private Boolean segundaCondicion = true;
    Stack<String> pilaEtiquetas = new Stack<>();

    public String escribirAssemblerAntesDeEscribirHijos(Nodo nodo) {
        String accion = nodo.getDato();
        switch (accion) {
            case "WHILE":
                generarSubarbolWhile();
                break;
            default:
                return null;
        }
        return null;
    }

    public String escribirAssemblerAntesDeEscribirHijoDer(Nodo nodo) {
        String accion = nodo.getDato();
        switch (accion) {
            case "CUERPO":
                generaEtiquetaInicioCuerpo();
                break;
            case "OR":
                return incrementarNroEtiqueta();
            case "AND":
                return desapilarUltimoParaEvitarDuplicidad();
            default:
                return null;
        }
        return null;
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
        String dato = nodo.getDato();
        if (dato.matches(">|<|==|!=|<=|>=")) {
            generarSubarbolCondicionSimple(nodo);
        } else {
            switch (dato) {
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
                    break;
                case "GET":
                    generarSubarbolGet(nodo);
                    break;
                case "DISPLAY":
                    generarSubarbolDisplay(nodo);
                    break;
                case "IF":
                    generarSubarbolIF(nodo);
                    break;
                case "OR":
                    generarSubarbolOR();
                    break;
                case "AND":
                    incrementarNroEtiqueta();
                    break;
                case "WHILE":
                    generarSubarbolWhile();
                    break;
                case "CUERPO":
                    generarSubarbolCuerpo();
                    break;
                default:
                    return null;
            }
        }
        return null;
    }

    private void generarSubarbolWhile() {
        assembler += formatAssembler("JMP", crearEtiqueta());

    }

    private void generarSubarbolCuerpo(){
        String etiqueta = obtenerUltimaEtiquetaCreada();
        assembler += formatAssembler(etiqueta);
    }

    private void generarSubarbolOR() {
        String etiqueta = obtenerAnteUltimaEtiquetaCreada();
        assembler += formatAssembler(etiqueta);
    }

    private void generarSubarbolCondicionSimple(Nodo nodo) {
        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();
        String etiqueta = crearEtiqueta();
        assembler += formatAssembler("FLD", izq.getDato()) + formatAssembler("FLD", der.getDato());
        assembler += formatAssembler("FCOM");
        assembler += formatAssembler("FSTSW AX");
        assembler += formatAssembler("SAHF");
        assembler += formatAssembler(getSalto(nodo.getDato()) + " " + etiqueta);
    }

    private void generaEtiquetaInicioCuerpo() {
        assembler += formatAssembler("JMP",crearEtiqueta());
        incrementarNroEtiqueta();
    }

    private void generarSubarbolIF(Nodo nodo) {
        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();
        if (der.getDato() != "CUERPO") {
            assembler += formatAssembler(obtenerUltimaEtiquetaCreada());
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

    private void generarSubarbolGet(Nodo nodo) {
        Nodo izq = nodo.getIzq();

        String asmFields = formatAssembler("GetFloat", izq.getDato());

        String subarbolActual = asmFields + "\n";

        assembler += subarbolActual;
    }

    private void generarSubarbolDisplay(Nodo nodo) {
        Nodo izq = nodo.getIzq();

        String asmFields = formatAssembler("DisplayFloat", izq.getDato() + ",1");
        String asmNewLine = "\tnewline 1\n";

        String subarbolActual = asmFields + asmNewLine + "\n";

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


    private String getSalto(String operador) {
        switch (operador) {
            case "==":
                return "JNE";
            case "!=":
                return "JE";
            case "<":
                return "JAE";
            case "<=":
                return "JA";
            case ">":
                return "JBE";
            case ">=":
                return "JB";
            default:
                return null;
        }
    }

    private String incrementarNroEtiqueta() {
        return String.valueOf(NRO_ETIQUETA++);
    }

    private String crearEtiqueta() {
        String etiqueta = "etiqueta" + NRO_ETIQUETA;
        pilaEtiquetas.push(etiqueta);
        return etiqueta;
    }

    private String obtenerUltimaEtiquetaCreada() {
        incrementarNroEtiqueta();
        return pilaEtiquetas.pop()+":";
    }

    private String desapilarUltimoParaEvitarDuplicidad() {
        return pilaEtiquetas.pop()+":";
    }

    private String obtenerAnteUltimaEtiquetaCreada() {
        incrementarNroEtiqueta();
        String aux = pilaEtiquetas.pop();
        String etiqueta = pilaEtiquetas.pop();
        pilaEtiquetas.push(aux);
        return etiqueta+":";
    }

}
