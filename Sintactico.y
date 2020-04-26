
%{
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include "y.tab.h"

#include "listaDinamica\header.h"
#include "tablaSimbolos\header.h"


int yystopparser = 0;
FILE  *yyin;

/* Variables TABLA DE SIMBOLOS */
char tipoID[100];
tLista listaIDs;

%}

//Estructura yylval
%union {
int int_val;
double double_val;
char *str_val;
}

%token <str_val>ID
%token <int_val>CONST_INT
%token <double_val>CONST_FLOAT
%token <str_val>CONST_STR

%token DEFVAR ENDDEF
%token TIPO_INTEGER TIPO_FLOAT TIPO_STRING

%token WHILE
%token IF ELSE
%token OP_AND OP_OR OP_NOT

%token OP_ASIG
%token OP_SUMA OP_RESTA 
%token OP_MUL OP_DIV
%token OP_IGUAL OP_DISTINTO
%token OP_MENOR OP_MENOR_IGUAL OP_MAYOR OP_MAYOR_IGUAL 

%token LL_A LL_C P_A P_C
%token PUNTO_COMA 
%token DOS_PUNTOS
%token COMA

%token GET
%token DISPLAY

%%
start   : programa { recorrerLista(&listaIDs);printf("Regla Compilacion OK\n");}
		;

programa	: seccion_declaraciones bloque { printf("Regla PROGRAMA \n");}
			;

seccion_declaraciones 	: DEFVAR bloque_declaraciones ENDDEF { printf("Regla SECCION DECLARACION \n");}
						;

bloque_declaraciones	: declaracion
						| bloque_declaraciones declaracion  { printf("Regla BLOQUE DECLARACION \n");}
						;

declaracion : tipo_dato DOS_PUNTOS lista_ids  { printf("Regla DECLARACION \n");}
			;

tipo_dato 	: TIPO_FLOAT { sprintf(tipoID, "%s","FLOAT");}  { printf("Regla TIPO DECLARACION \n");}
			| TIPO_INTEGER { sprintf(tipoID, "%s","INTEGER");}  { printf("Regla TIPO DECLARACION \n");}
			| TIPO_STRING { sprintf(tipoID, "%s","STRING");}  { printf("Regla TIPO DECLARACION \n");}
			;

lista_ids	: ID { agregarVarATabla(&listaIDs, yylval.str_val, NULL, tipoID, NULL);} { printf("Regla LISTAIDS \n");}
			| lista_ids PUNTO_COMA ID { agregarVarATabla(&listaIDs, yylval.str_val, NULL, tipoID, NULL);} { printf("Regla LISTAIDS \n");}
			;

bloque  : sentencia
		| bloque sentencia { printf("Regla BLOQUE \n");}

sentencia   : asignacion
			| bloque_if
			| bloque_while
			| expresion_aritmetica {printf("Regla SENTENCIA \n");}
			| escritura
			| lectura
			;

bloque_if	: IF P_A expresion_logica P_C LL_A bloque LL_C {printf("Regla BLOQUE_IF \n");}
			| IF P_A expresion_logica P_C LL_A bloque LL_C
				ELSE LL_A bloque LL_C {printf("Regla SENTENCIA \n");}
			;

bloque_if_unario	: IF P_A expresion_logica COMA expresion COMA expresion P_C {printf("Regla BLOQUE_IF \n");}

bloque_while	: WHILE P_A expresion_logica P_C LL_A bloque LL_C {printf("Regla BLOQUE_WHILE \n");}
				;

op_aritmetico 	: OP_SUMA
				| OP_RESTA
				| OP_MUL
				| OP_DIV {printf("Regla OPERADOR ARITMETICO \n");}
				;

op_booleano 	: OP_MENOR
				| OP_MENOR_IGUAL
				| OP_MAYOR
				| OP_MAYOR_IGUAL
				| OP_IGUAL   
				| OP_DISTINTO {printf("Regla OPERADOR BOOLEANO \n");}
				;

expresion_logica	: expresion_logica OP_AND termino_logico
					| expresion_logica OP_OR termino_logico
					| termino_logico {printf("Regla EXPRESION_LOGICA \n");}
					;

termino_logico	: OP_NOT termino_logico
				| expresion_aritmetica op_booleano expresion_aritmetica {printf("Regla TERMINO LOGICO \n");}
				;
	
//asignacion 	: ID OP_ASIG asignacion {printf("Regla ASIGNACION \n");}
//			| ID OP_ASIG expresion PUNTO_COMA {printf("Regla ASIGNACION \n");}
//			;

asignacion 	: ID OP_ASIG expresion {chequearVarEnTabla($1); printf("Regla ASIGNACION \n");}
			;



expresion	: expresion_aritmetica {printf("Regla EXPRESION_ARITMETICA \n");}
			| expresion_cadena {printf("Regla EXPRESION_CADENA \n");}
			;
		
expresion_aritmetica	: termino { printf("Regla EXPRESION \n");}
						| expresion_aritmetica OP_SUMA termino { printf("Regla EXPRESION \n");}
						| expresion_aritmetica OP_RESTA termino { printf("Regla EXPRESION \n");}
						;

expresion_cadena	: CONST_STR {agregarCteStringATabla(yylval.str_val); printf("Regla FACTOR \n");}
					;
			
termino	: termino OP_MUL factor { printf("Regla TERMINO \n");}
		| termino OP_DIV factor  { printf("Regla TERMINO \n");}
		| factor { printf("Regla TERMINO \n");}
		;
			
factor	: P_A expresion_aritmetica P_C {printf("Regla FACTOR \n");}
		;

factor	: ID {chequearVarEnTabla(yylval.str_val); printf("Regla FACTOR \n");}
		| CONST_INT {agregarCteIntATabla(yylval.int_val); printf("Regla FACTOR \n");}
		| CONST_FLOAT {agregarCteFloatATabla(yylval.double_val); printf("Regla FACTOR \n");}		
		;

escritura	: DISPLAY ID 
			| DISPLAY CONST_STR {printf("Regla ESCRITURA");}
			;

lectura	: GET ID {printf("Regla LECTURA");}

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

