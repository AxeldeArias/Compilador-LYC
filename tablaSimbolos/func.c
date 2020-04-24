#include "string.h"
#include "header.h"

void insertarEnTablaDeSimbolos(tLista *lista,char* nombre, char * valor, char* tipo,char* longitud){
    //Esta función inserta constantes a Tabla de SIMBOLOS
    //Ejemplos de constantes:  "ASD" 12 1.2 .0
    
    static int primeraVez = 1;
    if(primeraVez == 1){
        primeraVez = 0;
        crearLista(lista);
    }
    
    
    char guionBajo[100] = "_";
    char const_string[] = "CONST_STRING";
    t_lex dato;
    
    if(nombre){
        strcpy(dato.nombre,nombre);
    }else{       
       //Si es una constante le agrego el guión bajo
        reemplazarPuntoPorGuion(valor);
        strcpy(dato.nombre,strcat(guionBajo,valor));
        sprintf(dato.tipo, "%s", "-");
    }
    
    if(longitud){
        sprintf(dato.longitud, "%d", longitud);
    }else{
        sprintf(dato.longitud, "%s", "-");
    }
    
    if(tipo){
        sprintf(dato.tipo, "%s", tipo);
    }else{
        sprintf(dato.tipo, "%s", "-");
    }
    
    if(valor){
        sprintf(dato.valor, "%s", valor);
    }else{
        sprintf(dato.valor, "%s", "-");
    }
    
    insertarEnOrden(lista,&dato);

}


char * reemplazarPuntoPorGuion(char *val){

	int i;
    for (i=0; val[i] != '\0';i++)
    {
        if (val[i] == '.' ){
          val[i] = '_';
        }
    }
    return val;
      
}