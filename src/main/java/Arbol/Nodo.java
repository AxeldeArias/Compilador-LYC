package Arbol;

public class Nodo {
    private String dato;
    private Nodo izq;
    private Nodo der;
    private Integer id;

    private static Integer ultimoNodo = 0;

    public Nodo(Nodo otroNodo) {
        this.dato = otroNodo.dato;
        this.izq = otroNodo.izq;
        this.der = otroNodo.der;
        this.id = otroNodo.id;
    }

    public Nodo(String dato, Nodo izq, Nodo der) {
        this.dato = dato;
        this.izq = izq;
        this.der = der;
        this.id = ultimoNodo;
        ultimoNodo++;
    }

    public Nodo(String dato, Nodo izq) {
        this.dato = dato;
        this.izq = izq;
        this.der = null;
        this.id = ultimoNodo;
        ultimoNodo++;
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

    public Integer getId() {
        return id;
    }

    public void recorrer() {
        recorrerPosorden(this);
        System.out.println("");
    }

    public void recorrerPosorden(Nodo nodo) {
        if (nodo == null)
            return;
        recorrerPosorden(nodo.getIzq());
        recorrerPosorden(nodo.getDer());
        System.out.print(nodo.getDato() + " ");
    }

    public void recorrerIntermedia() {
        Nodo aux = new Nodo(this);
        escribirIntermedia(aux);
    }

    public void escribirIntermedia(Nodo nodo) {
        if (nodo == null)
            return;
        if(!nodo.izq.esHoja())
            escribirIntermedia(nodo.izq);
        if (!nodo.der.esHoja())
            escribirIntermedia(nodo.der);
        nodo.dato = nodo.izq.dato + " " + nodo.der.dato + " " + nodo.dato;
        //Hacer magia de assembler
    }

    public Boolean esHoja() {
        return this.getIzq() == null && this.getDer() == null;
    }

    @Override
    public String toString() {
        return "Nodo {" +
                "dato='" + dato + '\'' +
                '}';
    }
}
