package Arbol;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class ConstructorAssembler {

    private static Integer CANT_AUXILIARES = 0;
    private String assembler = "";

    public String escribirAssembler(Nodo nodo) {

        switch (nodo.getDato()) {
            case "+":
                return generarSubarbolOperacion(nodo, Collections.singletonList("FADD"));
            case "-":
                return generarSubarbolOperacion(nodo, asList("FXCH", "FSUB"));
            case "*":
                return generarSubarbolOperacion(nodo, Collections.singletonList("FMUL"));
            case "/":
                return generarSubarbolOperacion(nodo, asList("FXCH", "FDIV"));
            case ":=":
                generarSubarbolAsignacion(nodo);
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
        String asmFree = formatAssembler("FFREE") + "\n";

        String subarbolActual = asmFields + asmCommands + asmStore + asmFree;

        assembler += subarbolActual;

        return aux;
    }

    private void generarSubarbolAsignacion(Nodo nodo) {
        Nodo izq = nodo.getIzq();
        Nodo der = nodo.getDer();

        String asmFields = formatAssembler("FLD", der.getDato());
        String asmStore = formatAssembler("FSTP", izq.getDato());

        String subarbolActual = asmFields + asmStore;

        assembler += subarbolActual;
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
}
