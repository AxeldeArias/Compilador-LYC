#ifndef TABLAHEADER_H_INCLUDED
#define TABLAHEADER_H_INCLUDED

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define T_INTEGER 11
#define T_FLOAT 12
#define T_STRING 13
#define T_CTE_INT 14
#define T_CTE_FLOAT 15
#define T_CTE_STRING 16


void agregarNombre(tSimbolo *dato, char *nombre, char *valor); 
void agregarLongitud(tSimbolo *dato, int longitud); 
void agregarTipo(tSimbolo *dato, int tipo); 
void agregarValor(tSimbolo *dato, char *valor); 

void agregarVarATabla(tLista *lista, char *nombre, char *valor, int tipo, int longitud);
int yyerror(char* msg);

#endif // TABLAHEADER_H_INCLUDED