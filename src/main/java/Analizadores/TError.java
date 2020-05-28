package Analizadores;

public class TError {
    String lexema, tipo, descripcion;
    int line, column;

    private void exit(String mensaje) {
        System.exit(0);
    }

    public TError(String lexema, int line, int column, String tipo, String descripcion) {
        this.lexema = lexema;
        this.line = line;
        this.column = column;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }
}
