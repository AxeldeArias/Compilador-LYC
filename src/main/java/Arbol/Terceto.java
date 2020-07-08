package Arbol;

public class Terceto {
    String campo1;
    String campo2;
    String campo3;
    Integer nroTerceto = null;

    public Terceto(String campo1){
        this.campo1 = campo1;
    }

    public Terceto(String campo1, String campo2){
        this.campo1 = campo1;
        this.campo2 = campo2;
    }

    public Terceto(String campo1, Integer campo2){
        this.campo1 = campo1;
        this.campo2 = "[ "+ campo2 +" ]";
    }

    public Terceto(String campo1, String campo2,  String campo3){
        this.campo1 = campo1;
        this.campo2 = campo2;
        this.campo3 = campo3;
    }

    public String reducirTercetoAVariableAux(){
        this.campo1 = "@aux" + nroTerceto;
        this.campo2 = null;
        this.campo3 = null;
        return this.campo1;
    }

    public Terceto(String campo1, Integer campo2,  Integer campo3){
        this.campo1 = campo1;
        this.campo2 = "[ "+ campo2 +" ]";
        this.campo3 = "[ "+ campo3 +" ]";
    }

    public Terceto(String campo1, Integer campo2,  String campo3){
        this.campo1 = campo1;
        this.campo2 = "[ "+ campo2 +" ]";
        this.campo3 = campo3;
    }

    public Terceto(String campo1, String campo2,  Integer campo3){
            this.campo1 = campo1;
            this.campo2 = campo2;
            this.campo3 = "[ "+ campo3 +" ]";
    }

    public Integer getNroTerceto() {
        return nroTerceto;
    }

    public void setNroTerceto(Integer nroTerceto) {
        this.nroTerceto = nroTerceto;
    }

    public String getCampo1() {

        return campo1;
    }

    public String getCampo2(String tipo) {
        return getOperando(campo2, tipo);
    }

    public String getCampo3(String tipo) {
        return getOperando(campo3, tipo);
    }

        private String getOperando(String str,String tipo){
        if(str.contains("[")){
            if(tipo == "operando"){
                String aux = quitarCorchetes(str);
                return "@aux"+aux;
            }else if(tipo == "etiqueta"){
                String aux = quitarCorchetes(str);
                return "@etiq_n"+aux;
            }
        }
        return str;
    }

    private String quitarCorchetes(String str){
        return str.replace("[", "").replace("]", "").replace(" ", "");
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public void setCampo2(String campo2) {
        this.campo2 = "[ "+ campo2 +" ]";
    }

    @Override
    public String toString() {
        return "["+campo1+", " +campo2+", "+campo3+"]";
    }

    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }
}
