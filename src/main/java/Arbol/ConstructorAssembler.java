package Arbol;

import Tabla.Simbolo;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class ConstructorAssembler {

    private static Integer CANT_AUXILIARES = 0;
    private String assembler = "";
    private Integer NRO_ETIQUETA = 0;
    private Boolean segundaCondicion = true;
    Stack<String> pilaEtiquetas = new Stack<>();

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

        return variablesDeclaradas + "\n" + variablesAuxiliares + "\n"
    ;}

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

    public String escribirAssemblerAntesDeEscribirHijos(Nodo nodo) {
        String accion = nodo.getDato();
        switch (accion) {
            case "WHILE":
                generarAssemblerPreWhile();
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
                generarAssemblerMitadCuerpo();
                break;
            case "OR":
                return incrementarNroEtiqueta();
            case "AND":
                desapilarUltimaEtiquetaCreada();
                return null;
            default:
                return null;
        }
        return null;
    }

    public String generarAssembler(Nodo nodo) {
        String dato = nodo.getDato();
        if (dato.matches(">|<|==|!=|<=|>=")) {
            generarAssemblerPosCondicionSimple(nodo);
        } else {
            switch (dato) {
                case "+":
                    return generarAssemblerPosOperacion(nodo, Collections.singletonList("FADD"));
                case "-":
                    return generarAssemblerPosOperacion(nodo, Collections.singletonList("FSUB"));
                case "*":
                    return generarAssemblerPosOperacion(nodo, Collections.singletonList("FMUL"));
                case "/":
                    return generarAssemblerPosOperacion(nodo, Collections.singletonList("FDIV"));
                case ":=":
                    generarAssemblerPosAsignacion(nodo);
                    break;
                case "GET":
                    generarAssemblerPosGet(nodo);
                    break;
                case "DISPLAY":
                    generarAssemblerPosDisplay(nodo);
                    break;
                case "IF":
                    generarAssemblerPosIF(nodo);
                    break;
                case "OR":
                    generarAssemblerPosOR();
                    break;
                case "AND":
                    incrementarNroEtiqueta();
                    break;
                case "CUERPO":
                    generarAssemblerPosCuerpo();
                    break;
                case "WHILE":
                    generarAssemblerPosWhile();
                    break;
                default:
                    return null;
            }
        }
        return null;
    }


    private void generarAssemblerPosIF(Nodo nodo) {
        Nodo der = nodo.getDer();
        if (!der.getDato().equals("CUERPO")) {
            assembler += desapilarUltimaEtiquetaCreada() + "\n";
        }
    }

    private void generarAssemblerPreWhile(){
        assembler += crearEtiquetaConPuntos() + "\n";
        incrementarNroEtiqueta();
    }

    private void generarAssemblerPosWhile(){
        String etiquetaSalirDelWhile = desapilarUltimaEtiquetaCreada();
        String etiquetaVolverAlInicio = desapilarUltimaEtiquetaCreada();
        incrementarNroEtiqueta();
        assembler += formatAssembler("JMP", etiquetaVolverAlInicio);
        assembler += etiquetaSalirDelWhile + "\n";
    }


    private void generarAssemblerPosCondicionSimple(Nodo nodo) {
        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();

        String etiqueta = crearEtiqueta();

        String asmFields = formatAssembler("FLD", izq.getDato()) + formatAssembler("FLD", der.getDato());
        String asmComp = formatAssembler("FXCH") +
                        formatAssembler("FCOM") +
                        formatAssembler("FSTSW AX") +
                        formatAssembler("SAHF");
        String asmJmpEtiqueta = formatAssembler(getTipoDeSalto(nodo.getDato()) + " " + etiqueta);

        String subarbolActual = asmFields + asmComp + asmJmpEtiqueta + "\n";

        assembler += subarbolActual;
    }

    private void generarAssemblerPosOR() {
        String etiqueta = desapilarAnteUltimaEtiquetaCreada();
        incrementarNroEtiqueta();
        assembler += etiqueta + "\n";
    }


    private void generarAssemblerMitadCuerpo() {
        String etiqueta = desapilarUltimaEtiquetaCreada();
        incrementarNroEtiqueta();
        assembler += formatAssembler("JMP", crearEtiqueta());
        incrementarNroEtiqueta();
        assembler += etiqueta + '\n';
    }

    private void generarAssemblerPosCuerpo() {
        String etiqueta = desapilarUltimaEtiquetaCreada();
        incrementarNroEtiqueta();
        assembler += etiqueta + "\n";
    }


    private String generarAssemblerPosOperacion(Nodo nodo, List<String> commands) {

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

    private void generarAssemblerPosGet(Nodo nodo) {
        Nodo izq = nodo.getIzq();

        String asmFields = formatAssembler("GetFloat", izq.getDato());

        String subarbolActual = asmFields + "\n";

        assembler += subarbolActual;
    }

    private void generarAssemblerPosDisplay(Nodo nodo) {
        Nodo izq = nodo.getIzq();

        String asmFields = formatAssembler("DisplayFloat", izq.getDato() + ",1");
        String asmNewLine = "\tnewline 1\n";

        String subarbolActual = asmFields + asmNewLine + "\n";

        assembler += subarbolActual;
    }

    private void generarAssemblerPosAsignacion(Nodo nodo) {
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
        String _value = (value.matches("[0-9]+") ? "_" : "") + value;
        return String.format("\t%s %s\n", command, _value);
    }

    private String formatAssembler(String command) {
        return String.format("\t%s\n", command);
    }

    public String getAssembler() {
        return assembler;
    }


    private String getTipoDeSalto(String operador) {
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
        pilaEtiquetas.push(etiqueta + ":");
        return etiqueta;
    }

    private String crearEtiquetaConPuntos(){
        String etiqueta = "etiqueta" + NRO_ETIQUETA;
        pilaEtiquetas.push(etiqueta);
        return etiqueta + ":";
    }

    private String desapilarUltimaEtiquetaCreada() {
        String aux = pilaEtiquetas.pop() ;
        return aux;
    }

    private String desapilarAnteUltimaEtiquetaCreada() {
        String aux = pilaEtiquetas.pop();
        String etiqueta = pilaEtiquetas.pop();
        pilaEtiquetas.push(aux);
        return etiqueta;
    }

}
