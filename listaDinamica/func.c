#include "listaHeader.h"


void mostrarDato(const tSimbolo *d)
{
    printf("-----------------------\n");
    printf("Nombre %s\n", d->nombre);
    printf("Tipo %s\n", d->tipo);
    printf("Valor %s\n", d->valor);
    printf("Longitud %d\n", d->longitud);
    printf("-----------------------\n");
}


void crearLista(tLista *pl)
{
    *pl = NULL;
}

int insertarEnOrden(tLista *pl, const tSimbolo *d)
{
    tNodo *nue;

    while(*pl && fCompNombre(&(*pl)->info, d) < 0)
        pl = &(*pl)->sig;
    if(*pl && fCompNombre(&(*pl)->info, d) == 0)
    {
        return DUPLICADO;
    }
    nue = (tNodo*) malloc(sizeof(tNodo));
    if(!nue)
        return SIN_MEM;
    nue->info = *d;
    nue->sig = *pl;
    *pl = nue;
    return TODO_BIEN;
}

int existeEnLista(tLista *pl, const tSimbolo* d)
{
    while(*pl){
        if(fCompNombre(&(*pl)->info, d) == 0)
            return EXISTE;
        pl = &(*pl)->sig;
    }
    return NO_EXISTE;
}

void recorrerLista(tLista *pl)
{
    puts("------------------Recorro lista------------------");
    while(*pl)
    {
        mostrarDato(&(*pl)->info);
        pl = &(*pl)->sig;
    }
    puts("------------------Recorro lista------------------");
}

int fCompNombre(const void *d1, const void *d2)
{
    tSimbolo *dato1 = (tSimbolo*)d1;
    tSimbolo *dato2 = (tSimbolo*)d2;
    return strcmp(dato1->nombre, dato2->nombre);
}

// int insertarAlPrincipio(tLista *pl, const t_lex *d)
// {
//     tNodo *nue;

//     nue=(tNodo*)malloc(sizeof(tNodo));
//     if(!nue)
//         return SIN_MEM;
//     nue->info=*d;
//     nue->sig=*pl;
//     *pl=nue;
//     return TODO_BIEN;
// }

// int insertarAlFinal(tLista *pl, const t_lex *d)
// {
//     tNodo *nue;

//     while(*pl)
//         pl=&(*pl)->sig;
//     nue=(tNodo*)malloc(sizeof(tNodo));              ///*pl=(tNodo)malloc(sizeof(tNodo));
//     if(!nue)                                        ///if(!*pl)
//         return SIN_MEM;
//     nue->info=*d;                                   ///(*pl)->info=*d;
//     nue->sig=NULL;                                  ///(*pl)->sig=NULL;
//     *pl=nue;
//     return TODO_BIEN;
// }

// int eliminarDuplicadosNoOrd(tLista *pl, int(*comparar)(const void*, const void*))
// {
//     tLista *q;
//     tNodo *aux;
//     int marca=0;
//     int cant=0;

//     while(*pl)
//     {
//         q=&(*pl)->sig;
//         while(*q)
//         {
//             if(comparar(&(*pl)->info,&(*q)->info)==0)
//             {
//                 aux=*q;
//                 *q=aux->sig;
//                 marca=1;
//                 cant++;
//                 free(aux);
//             }
//             else
//                 q=&(*q)->sig;
//         }
//         if(marca)
//         {
//             aux=*pl;
//             *pl=aux->sig;
//             cant++;
//             free(aux);
//             marca=0;
//         }
//         else
//             pl=&(*pl)->sig;
//     }
//     return cant;
// }

// int eliminarDuplicadosOrd(tLista *pl, int(*comparar)(const void*, const void*))
// {
//     tLista *q;
//     tNodo *aux;
//     int cant;
//     int totalEliminados;

//     while(*pl)
//     {
//         q=&(*pl)->sig;
//         cant=0;
//         while(*q && comparar(&(*pl)->info,&(*q)->info)==0)
//         {
//             aux=*q;
//             *q=aux->sig;
//             cant++;
//             free(aux);
//         }
//         if(cant!=0)
//         {
//             totalEliminados=cant+1;
//             aux=*pl;
//             *pl=aux->sig;
//             free(aux);
//         }
//         else
//             pl=&(*pl)->sig;
//     }
//     return totalEliminados;
// }

// int eliminarDuplicadosYacumularNoOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*, const void*))
// {
//     tLista *q;
//     tNodo *aux;
//     int cant=0;

//     while(*pl)
//     {
//         q=&(*pl)->sig;
//         while(*q)
//         {
//             if(comparar(&(*pl)->info,&(*q)->info)==0)
//             {
//                 aux=*q;
//                 *q=aux->sig;
//                 acum(&(*pl)->info,aux);
//                 cant++;
//                 free(aux);
//             }
//             else
//                 q=&(*q)->sig;
//         }
//         pl=&(*pl)->sig;
//     }
//     return cant;
// }

// int eliminarDuplicadosYacumularOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*, const void*))
// {
//     tLista *q;
//     tNodo *aux;
//     int cant=0;

//     while(*pl)
//     {
//         q=&(*pl)->sig;
//         while(*q && comparar(&(*pl)->info,&(*q)->info)==0)
//         {
//             aux=*q;
//             *q=aux->sig;
//             acum(&(*pl)->info,aux);
//             cant++;
//             free(aux);
//         }
//         pl=&(*pl)->sig;
//     }
//     return cant;
// }

// int eliminarDuplicadosYacumularAlFinalNoOrd(tLista *pl, int(*comparar)(const void*, const void*), void(*acum)(void*, const void*))
// {
//     tLista *q;
//     tNodo *aux;
//     int marca=0, cant=0;

//     while(*pl)
//     {
//         q=&(*pl)->sig;
//         while(*q && marca==0)
//         {
//             if(comparar(&(*pl)->info,&(*q)->info)==0)
//             {
//                 acum(&(*q)->info,&(*pl)->info);
//                 marca=1;
//             }
//             else
//                 q=&(*q)->sig;
//         }
//         if(marca)
//         {
//             aux=*pl;
//             *pl=aux->sig;
//             free(aux);
//             cant++;
//             marca=0;
//         }
//         else
//             pl=&(*pl)->sig;
//     }
//     return cant;
// }

// int listaLlena(const tLista *pl)
// {
//     void *aux=malloc(sizeof(tNodo));
//     free(aux);
//     return aux==NULL;
// }

// int listaVacia(const tLista *pl)
// {
//     return *pl==NULL;
// }

// void fAcum(void *d1, const void *d2)
// {
//     t_lex *dato1=(t_lex*)d1;
//     t_lex *dato2=(t_lex*)d2;
//     dato1->cant+=dato2->cant;
// }


