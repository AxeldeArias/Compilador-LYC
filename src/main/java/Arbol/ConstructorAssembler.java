package Arbol;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static java.util.Arrays.asList;

public class ConstructorAssembler {

    private static Integer CANT_AUXILIARES = 0;
    private String assembler = "";
    private String etiquetas = "";
    private Integer NRO_ETIQUETA = 0;
    private Boolean segundaCondicion = true;
    Stack<String> pilaEtiquetas = new Stack<String>();

    public String escribirAssemblerAntesDeEscribirHijos(Nodo nodo) {
        String accion = nodo.getDato();
        switch (accion) {
           case "WHILE":
                    return generarSubarbolWhile();
            default:
                return null;
        }
    }

    public String escribirAssemblerAntesDeEscribirHijoDer(Nodo nodo) {
        String accion = nodo.getDato();
        switch (accion) {
           case "CUERPO":
                    return generaEtiquetaInicioCuerpo();
            case "OR":
                    return incrementarNroEtiqueta();
            case "AND":
                    return desapilarUltimoParaEvitarDuplicidad();
            default:
                return null;
        }
     }

    public String escribirAssembler(Nodo nodo) {
         String dato = nodo.getDato();
         if(dato.matches(">|<|==|!=|<=|>=")){
             return generarSubarbolCondicionSimple(nodo);
         }else {
             switch (dato) {
                 case "+":
                     return generarSubarbolOperacion(nodo, Collections.singletonList("FADD"));
                 case "-":
                     return generarSubarbolOperacion(nodo, asList("FXCH", "FSUB"));
                 case "*":
                     return generarSubarbolOperacion(nodo, Collections.singletonList("FMUL"));
                 case "/":
                     return generarSubarbolOperacion(nodo, asList("FXCH", "FDIV"));
                 case ":=":
                     return generarSubarbolAsignacion(nodo);
                 case "<":
                     return generarSubarbolCondicionSimple(nodo);
                 case "IF":
                     return generarSubarbolIF(nodo);
                 case "OR":
                     return generarSubarbolOR();
                 case "AND":
                     return incrementarNroEtiqueta();
                 case "WHILE":
                     return generarSubarbolWhile();

             }
         }
         return null;
    }

    private String generarSubarbolWhile(){
        return  assembler += formatAssembler("JMP", crearEtiqueta());

    }
    private String generarSubarbolOR() {
        String etiqueta = obtenerAnteUltimaEtiquetaCreada();
        return assembler += formatAssembler(etiqueta);
    }

    private String generarSubarbolCondicionSimple(Nodo nodo){
         Nodo izq = nodo.getIzq();
         Nodo der = nodo.getDer();
         String etiqueta = crearEtiqueta();
         assembler += formatAssembler("FLD", izq.getDato()) + formatAssembler("FLD", der.getDato());
         assembler += formatAssembler("FCOM");
         assembler += formatAssembler("FSTSW AX");
         assembler += formatAssembler("SAHF");
         assembler += formatAssembler(getSalto(nodo.getDato()) + " " + etiqueta);
         return assembler;
    }

    private String generaEtiquetaInicioCuerpo(){
        return assembler += formatAssembler(obtenerUltimaEtiquetaCreada());
    }

    private String generarSubarbolIF(Nodo nodo){
       Nodo izq = nodo.getIzq();
       Nodo der = nodo.getDer();
       if ( der.getDato() != "CUERPO" ) {
          String aux = formatAssembler(obtenerUltimaEtiquetaCreada());
          return assembler += aux;
       }
       return null;
    }

    private String generarSubarbolOperacion(Nodo nodo, List<String> commands) {

        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();
        String aux = incrementarCantAuxiliares();

        String asmFields = formatAssembler("FLD", izq.getDato()) + formatAssembler("FLD", der.getDato());
        String asmCommands = commands.stream().reduce("", (subtotal, command) -> subtotal + formatAssembler(command));
        String asmStore = formatAssembler("FSTP", aux);
        String asmFree = formatAssembler("FFREE") + "\n";
        String subarbolActual = asmFields + asmCommands + asmStore + asmFree;
        assembler += subarbolActual;

        return aux;
    }

    private String generarSubarbolAsignacion(Nodo nodo) {
        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();

        String asmFields = formatAssembler("FLD", der.getDato());
        String asmStore = formatAssembler("FSTP", izq.getDato());
        String subarbolActual = asmFields + asmStore;

        return assembler += subarbolActual;
    }


    private String incrementarCantAuxiliares() {
        CANT_AUXILIARES++;
        return "@aux" + CANT_AUXILIARES;
    }

    private String formatAssembler(String command, String value) {
        return String.format("\n\t%s %s", command, value);
    }

    private String formatAssembler(String command) {
        return String.format("\n\t%s", command);
    }

    public String getAssembler() {
        return assembler;
    }


    private String getSalto(String operador){
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

    private String incrementarNroEtiqueta(){
         return String.valueOf(NRO_ETIQUETA++);
    }

    private String crearEtiqueta(){
         String etiqueta = "etiqueta" + NRO_ETIQUETA;
         pilaEtiquetas.push(etiqueta);
         return etiqueta;
    }

    private String obtenerUltimaEtiquetaCreada(){
         incrementarNroEtiqueta();
         return pilaEtiquetas.pop();
    }
    private String desapilarUltimoParaEvitarDuplicidad(){
        return pilaEtiquetas.pop();
    }

    private String obtenerAnteUltimaEtiquetaCreada(){
         incrementarNroEtiqueta();
         String aux = pilaEtiquetas.pop();
         String etiqueta = pilaEtiquetas.pop();
         pilaEtiquetas.push(aux);
         return etiqueta;
    }

}
