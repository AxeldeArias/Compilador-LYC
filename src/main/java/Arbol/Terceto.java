package Arbol;

public class Terceto {
    String campo1;
    String campo2;
    String campo3;

    public Terceto(String campo1){
        this.campo1 = campo1;
    }

    public Terceto(String campo1, String campo2){
        this.campo1 = campo1;
        this.campo2 = campo2;
    }
    public Terceto(String campo1, String campo2,  String campo3){
        this.campo1 = campo1;
        this.campo2 = campo2;
        this.campo3 = campo3;
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
            this.campo3 = campo2;
            this.campo2 = "[ "+ campo3 +" ]";

    }


    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public String getCampo2() {
        return campo2;
    }

    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    public String getCampo3() {
        return campo3;
    }

    @Override
    public String toString() {
        return "["+campo1+", " +campo2+", "+campo3+"]";
    }

    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }
}
