#ifndef LISTADINAMICA_H_INCLUDED
#define LISTADINAMICA_H_INCLUDED
#define TODO_BIEN 1
#define SIN_MEM 0
#define DUPLICADO 2


typedef struct
{
    char nombre[100];
    char tipo[100] ;
    char valor[100] ;
    char longitud[100] ;
}t_lex;

typedef struct sNodo
{
    t_lex info;
    struct sNodo *sig;
}tNodo;

typedef tNodo *tLista;

char * reemplazarPuntoPorGuion(char *val);
void mostrarDato(const t_lex *d);
char * reemplazarPuntoPorGuion(char *);
void crearLista(tLista *pl);
int insertarAlPrincipio(tLista *pl, const t_lex *d);
int insertarAlFinal(tLista *pl, const t_lex *d);
int insertarEnOrden(tLista *pl, const t_lex *d);
int eliminarDuplicadosNoOrd(tLista *pl, int(*comparar)(const void*, const void*));
int eliminarDuplicadosOrd(tLista *pl, int(*comparar)(const void*, const void*));
int eliminarDuplicadosYacumularNoOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*, const void*));
int eliminarDuplicadosYacumularOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*, const void*));

int eliminarDuplicadosYacumularAlFinalNoOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*,const void*));

int listaLlena(const tLista *pl);
int listaVacia(const tLista *pl);

void recorrerLista(tLista *pl);
int fComp(const void *d1, const void *d2);
void fAcum(void *d1, const void *d2);

#endif // LISTADINAMICA_H_INCLUDED
