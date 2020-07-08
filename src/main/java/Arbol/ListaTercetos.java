package Arbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ListaTercetos {
    ArrayList<Terceto> listaTercetos = new ArrayList<Terceto>();
    Integer cantTercetos = 0;
        
    public Integer add (Terceto terceto) {
        terceto.setNroTerceto(cantTercetos);
        listaTercetos.add(terceto);
        return cantTercetos++;
    }

    public void reemplazarSalto(Integer numero, Integer salto) {
        listaTercetos.get(numero).setCampo2(String.valueOf(salto));
    }


    public void show () {

        Integer i=0;
         Iterator iterador = listaTercetos.iterator();
         while(iterador.hasNext()){
             System.out.println(i+" "+iterador.next());
             i++;
         }
    }

    public List<Terceto> getListaTercetos(){
        return listaTercetos;
    }
}
