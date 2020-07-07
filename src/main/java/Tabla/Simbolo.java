package Tabla;

public class Simbolo {
    private String nombre;
    private String valor;
    private TipoDato tipo;
    private Integer longitud;

    public Simbolo(String nombre, TipoDato tipo, String valor, Integer longitud) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public void setTipo(TipoDato tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    private String orDash(Object o) {
        return o != null ? "" + o : "-";
    }

    @Override
    public String toString() {
        return String.format(
                "%-50s|%-30s|%-30s|%-30s", orDash(nombre), orDash(tipo), orDash(valor), orDash(longitud)
        );
    }
}
