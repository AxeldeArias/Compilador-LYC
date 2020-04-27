#include "tablaHeader.h"


void agregarNombre(tSimbolo *dato, char *nombre, char *valor){
    if(nombre){
        strcpy(dato->nombre, nombre);
    } else {       
       //Si es una constante le agrego el guión bajo
        sprintf(dato->nombre, "_%s", valor);
        sprintf(dato->tipo, "%s", "-");
    }
}

void agregarLongitud(tSimbolo *dato, int longitud){
    if(longitud)
        dato->longitud = longitud;
    else
        dato->longitud = -1;
}

void agregarTipo(tSimbolo *dato, int tipo){
    if(tipo){
        switch(tipo){
            case T_INTEGER: sprintf(dato->tipo, "%s", "Integer"); break; 
            case T_FLOAT: sprintf(dato->tipo, "%s", "Float"); break; 
            case T_STRING: sprintf(dato->tipo, "%s", "String"); break; 
            case T_CTE_INT: sprintf(dato->tipo, "%s", "CteInteger"); break; 
            case T_CTE_FLOAT: sprintf(dato->tipo, "%s", "CteFloat"); break; 
            case T_CTE_STRING: sprintf(dato->tipo, "%s", "CteString"); break; 
            default: sprintf(dato->tipo, "%s", "-");
        }
    }else{
        sprintf(dato->tipo, "%s", "-");
    }
}

void agregarValor(tSimbolo *dato, char *valor){
    if(valor)
        sprintf(dato->valor, "%s", valor);
    else
        sprintf(dato->valor, "%s", "-");
}

void agregarVarATabla(tLista *lista, char *nombre, char *valor, int tipo, int longitud){
    //Esta función inserta constantes a Tabla de SIMBOLOS
    //Ejemplos de constantes:  "ASD" 12 1.2 .0

    tSimbolo dato;
    
    agregarNombre(&dato, nombre, valor);
    agregarLongitud(&dato, longitud);
    agregarTipo(&dato, tipo);
    agregarValor(&dato, valor);
    
    insertarEnOrden(lista, &dato);
}

void chequearVarEnTabla(tLista *lista, char * nombre){
    tSimbolo dato;
    
    strcpy(dato.nombre, nombre);
    
    if(existeEnLista(lista, &dato) == NO_EXISTE){
        char msg[100];
        sprintf(msg, "La variable '%s' debe ser declarada previamente en la seccion de declaracion de variables", nombre);
		yyerror(msg);
    }
}

void guardarTabla(tLista *pl){
    FILE* arch = fopen("ts.txt", "w+");
	tSimbolo info;
    char linea[120];
    char longitud[30];

    if(!arch){
		printf("No se pudo crear el archivo ts.txt\n");
		return;
	}

	fprintf(arch, "%-30s|%-30s|%-30s|%-30s\n","NOMBRE","TIPODATO","VALOR","LONGITUD");
	fprintf(arch, ".........................................................................................................\n");

    while(*pl){
        info = (*pl)->info;
        if(info.longitud != -1){
            itoa(info.longitud, longitud, 10);
        } else {
            strcpy(longitud, "-");
        }
        sprintf(linea, "%-30s|%-30s|%-30s|%-30s", info.nombre, info.tipo, info.valor, longitud);
        fprintf(arch, "%s\n", linea);
        pl = &(*pl)->sig;
    }
    fprintf(arch, "\n");
	fclose(arch);
}

int yyerror(char *msg) {
    printf("Syntax Error %s\n", msg);
    system("Pause");
    exit(1);
}