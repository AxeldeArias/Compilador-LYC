package Arbol;

import java.util.ArrayList;
import java.util.Iterator;

public class ListaTercetos {
    ArrayList<Terceto> listaTercetos = new ArrayList<Terceto>();
    Integer cantTercetos = 0;
        
    public Integer add (Terceto terceto) {
        listaTercetos.add(cantTercetos,terceto);
        return cantTercetos++;
    }
    
    
    public void show () {

        Integer i=0;
         Iterator iterador = listaTercetos.iterator();
         while(iterador.hasNext()){
             System.out.println(i+" "+iterador.next());
             i++;
         }
    }
}
