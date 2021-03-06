package Tabla;

public enum TipoDato {

    T_INTEGER("Integer"),
    T_FLOAT("Float"),
    T_STRING("String");

    private final String tipo;

    TipoDato(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "" + tipo;
    }
}
