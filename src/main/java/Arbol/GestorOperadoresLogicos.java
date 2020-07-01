package Arbol;

public class GestorOperadoresLogicos {

    private Boolean negarOperadores = false;

    public GestorOperadoresLogicos(Boolean negarOperadores) {
        this.negarOperadores = negarOperadores;
    }

    public String getOperador(String operador) {

        System.out.println(operador);
        if (negarOperadores == true) {
           return negarOperador(operador);
        }else{
            return operador ;
        }
    }

    public void negarProximosOperadores(Boolean negarOperadores){
        this.negarOperadores = negarOperadores;
    }

    public String negarOperador(String operador){
         switch (operador) {
                case ">":
                    return "<";
                case "<":
                    return ">";
                case "<=":
                    return ">=";
                case ">=":
                    return "<=";
                case "==":
                    return "!=";
                case "!=":
                    return "==";
            }
            return "";
    }

}
