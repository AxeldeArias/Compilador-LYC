#ifndef LISTAHEADER_H_INCLUDED
#define LISTAHEADER_H_INCLUDED

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TODO_BIEN 1
#define SIN_MEM 0
#define DUPLICADO 2

#define EXISTE 1
#define NO_EXISTE 0

typedef struct
{
    char nombre[100];
    char tipo[100] ;
    char valor[100] ;
    int longitud;
} tSimbolo;

typedef struct sNodo
{
    tSimbolo info;
    struct sNodo *sig;
}tNodo;

typedef tNodo* tLista;

void mostrarDato(const tSimbolo *d);

void crearLista(tLista *pl);
int insertarAlPrincipio(tLista *pl, const tSimbolo *d);
int insertarAlFinal(tLista *pl, const tSimbolo *d);
int insertarEnOrden(tLista *pl, const tSimbolo *d);
int eliminarDuplicadosNoOrd(tLista *pl, int(*comparar)(const void*, const void*));
int eliminarDuplicadosOrd(tLista *pl, int(*comparar)(const void*, const void*));
int eliminarDuplicadosYacumularNoOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*, const void*));
int eliminarDuplicadosYacumularOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*, const void*));

int eliminarDuplicadosYacumularAlFinalNoOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*,const void*));

int listaLlena(const tLista *pl);
int listaVacia(const tLista *pl);

int existeEnLista(tLista *pl, const tSimbolo* dato);
void recorrerLista(tLista *pl);
int fComp(const void *d1, const void *d2);
void fAcum(void *d1, const void *d2);

#endif // LISTAHEADER_H_INCLUDED