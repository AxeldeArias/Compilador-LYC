package Arbol;

public class Nodo {
    private String dato;
    private Nodo izq;
    private Nodo der;

    public Nodo(String dato, Nodo izq, Nodo der) {
        this.dato = dato;
        this.izq = izq;
        this.der = der;
    }

    public Nodo(String dato, Nodo izq) {
        this.dato = dato;
        this.izq = izq;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }

    public void recorrer() {
        recorrerPosorden(this);
    }

    public void recorrerPosorden(Nodo nodo) {
        if (nodo != null) {
            recorrerPosorden(nodo.getIzq());
            recorrerPosorden(nodo.getDer());
            System.out.print(nodo.getDato() + " ");
        }
    }

    @Override
    public String toString() {
        return "Nodo{" +
                "dato='" + dato + '\'' +
                '}';
    }
}
