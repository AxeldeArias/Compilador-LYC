
%{
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include "y.tab.h"

#include "listaDinamica\header.h"
#include "tablaSimbolos\header.h"



int yystopparser=0;
FILE  *yyin;

/* Variables TABLA DE SIMBOLOS */
char tipoID[100];
tLista listaIDs;

%}

//Estructura yylval
%union {
int intval;
double val;
char *str_val;
}

%token <str_val>ID
%token <int>ENTERO
%token <double>FLOAT
%token <str_val>STRING
%token OP_SUMA OP_RESTA OP_MUL OP_DIV ASIG P_A P_C PYC 
%token IF LLAVE_A LLAVE_C ELSE
%token OP_MENOR OP_MENOROIGUAL OP_MAYOR OP_MAYOROIGUAL OP_IGUAL 
%token DEF_VAR DOS_PUNTOS END_VAR 
%token Integer Float

%%
start      : programa { recorrerLista(&listaIDs);printf("Regla Compilacion OK\n");}
programa   : bloqueDeclaracion bloqueMain { printf("Regla PROGRAMA \n");}

bloqueDeclaracion : DEF_VAR listaDeclaraciones END_VAR { printf("Regla BLOQUE DECLARACION \n");}

listaDeclaraciones : declaracion | listaDeclaraciones declaracion  { printf("Regla LISTA DECLARACION \n");}

declaracion : tipoDeclaracion DOS_PUNTOS listaIDs  { printf("Regla DECLARACION \n");}

tipoDeclaracion : Float { sprintf(tipoID, "%s","FLOAT");}  { printf("Regla TIPO DECLARACION \n");}
				| Integer { sprintf(tipoID, "%s","INTEGER");}  { printf("Regla TIPO DECLARACION \n");}

listaIDs: ID { insertarEnTablaDeSimbolos(&listaIDs, yylval.str_val, NULL, tipoID, NULL);} { printf("Regla LISTAIDS \n");}
| listaIDs PYC ID { insertarEnTablaDeSimbolos(&listaIDs, yylval.str_val, NULL, tipoID, NULL);} { printf("Regla LISTAIDS \n");}



bloqueMain  : sentencia | bloqueMain sentencia { printf("Regla BLOQUEMAIN \n");}
sentencia   : asignacion {printf("Regla SENTENCIA \n");}

sentencia : condicional 
condicional : IF P_A condicion P_C LLAVE_A bloqueMain LLAVE_C {printf("Regla SENTENCIA \n");}
condicional : IF P_A condicion P_C LLAVE_A bloqueMain LLAVE_C 
				ELSE LLAVE_A bloqueMain LLAVE_C {printf("Regla SENTENCIA \n");} 

condicion : expresion op_condicion expresion {printf("Regla CONDICION \n");}

op_condicion : OP_MENOR | OP_MENOROIGUAL | OP_MAYOR | OP_MAYOROIGUAL | OP_IGUAL   {printf("Regla OP CONDICION \n");}
	
asignacion : ID ASIG asignacion { printf("Regla ASIGNACION \n"); }|
			 ID ASIG expresion PYC { printf("Regla ASIGNACION\n"); };
		
expresion  : 
			termino { printf("Regla EXPRESION \n");}
			|expresion OP_SUMA termino { printf("Regla EXPRESION \n");}
			| expresion OP_RESTA termino { printf("Regla EXPRESION \n");}
			
termino    : 
			termino OP_MUL factor { printf("Regla TERMINO \n");}
			| termino OP_DIV factor  { printf("Regla TERMINO \n");}
			| factor { printf("Regla TERMINO \n");}
			;
			
			
factor     : 
			ID {printf("Regla FACTOR \n");}
			|
			ENTERO {printf("Regla FACTOR \n");}
			|
			FLOAT {printf("Regla FACTOR \n");}
			
			|STRING {printf("Regla FACTOR \n");}

			|P_A expresion P_C   {printf("Regla FACTOR \n");}
	;
	
	interacion: condicion lista_sentencias ENDWHILE  {printf("Regla - INTERACION: WHILE CONDICION LISTA_SENTENCIAS ENDWHILE\n");}      
;  

lista_id:ID   { printf("Regla 21 - LISTA_ID:ID\n");}
         |ID  COMA lista_id  { printf("Regla 22- LISTA_ID:ID COMA LISTA_ID\n");}
;

entsal: DIPLAY CONST_STR   {printf("Regla 23 - ENTSAL CONST_STR\n");}
       |DIPLAY ID          {printf("Regla 24 - ENTSAL ID\n");}
       |GET ID           {printf("Regla 25 - ENTSAL ID\n");}


%%


int main(int argc,char *argv[])
{
  if ((yyin = fopen(argv[1], "rt")) == NULL)
  {
	printf("\nNo se puede abrir el archivo: %s\n", argv[1]);
  }
  else
  {
	yyparse();
  }
  fclose(yyin);
  return 0;
}
int yyerror(void) {
	printf("Syntax Error\n");
	system ("Pause");
	exit (1);
}




